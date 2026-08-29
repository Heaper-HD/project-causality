package com.heaper.causality.block.entity;

import com.heaper.causality.block.MultiblockControllerBlock;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.core.recipe.MachineRecipe;
import com.heaper.causality.core.recipe.ProcessType;
import com.heaper.causality.core.recipe.RecipeRegistry;
import com.heaper.causality.core.spec.SpecSheet;
import com.heaper.causality.multiblock.FormationResult;
import com.heaper.causality.multiblock.MultiblockDefinition;
import com.heaper.causality.multiblock.SpecCalculator;
import com.heaper.causality.multiblock.StructureIndex;
import com.heaper.causality.port.ItemPort;
import com.heaper.causality.port.MachinePort;
import com.heaper.causality.recipe.MachineProcessor;
import com.heaper.causality.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultiblockControllerBE extends BlockEntity {
    private static final int DATA_VERSION = 1;

    private final MultiblockDefinition definition;

    private boolean formed = false;
    private Map<Material, Integer> materials = Map.of();
    private SpecSheet spec = SpecSheet.EMPTY;

    private List<BlockPos> structurePositions = List.of();
    private List<BlockPos> inputPorts = List.of();
    private List<BlockPos> outputPorts = List.of();

    private String activeRecipeId = null;

    private int progress = 0;

    public MultiblockControllerBE(BlockPos pos, BlockState state, MultiblockDefinition definition) {
        super(ModBlockEntities.MULTIBLOCK_CONTROLLER.get(), pos, state);
        this.definition = definition;
    }

    public boolean isFormed() { return formed; }
    public Map<Material, Integer> materials() { return materials; }
    public SpecSheet spec() { return spec; }
    public @Nullable Material casingMaterial() {
        return materials.keySet().stream().findFirst().orElse(null);
    }

    private Direction facing() {
        return getBlockState().getValue(HorizontalDirectionalBlock.FACING);
    }

    private FormationResult runValidation() {
        return definition.defaultPattern()
                .validate(level, worldPosition, facing(), definition.portRequirements());
    }

    private void setActive(boolean active) {
        if (level == null) return;

        BlockState state = getBlockState();
        if (state.getValue(MultiblockControllerBlock.ACTIVE) == active) return;

        level.setBlock(worldPosition,
                state.setValue(MultiblockControllerBlock.ACTIVE, active),
                Block.UPDATE_ALL);
    }

    public void tryForm(Player player) {
        if (level == null) return;

        FormationResult result = definition.defaultPattern()
                .validate(level, worldPosition, facing(), definition.portRequirements());

        if (result instanceof FormationResult.Success success) {
            applyFormation(success);

            player.sendSystemMessage(Component.literal(success.message()));
            spec.ratings().forEach((axis, value) ->
                    player.sendSystemMessage(Component.literal(
                            " " + axis.id() + ": " + Math.round(value) + " " + axis.unit())));
        } else {
            clearFormation();
            player.sendSystemMessage(Component.literal(result.message()));
        }
    }

    private void applyFormation(FormationResult.Success success) {
        structurePositions = success.worldPositions();
        inputPorts = success.inputPorts();
        outputPorts = success.outputPorts();

        claimPorts(worldPosition);

        formed = true;
        materials = success.materialCounts();
        spec = SpecCalculator.fromCasings(materials);

        StructureIndex.register(level, worldPosition, structurePositions);
        setChanged();
    }

    private void claimPorts(BlockPos owner) {
        if (level == null) return;

        Material casing = materials.keySet().stream().findFirst().orElse(null);

        for (BlockPos pos : inputPorts) claim(pos, owner, casing);
        for (BlockPos pos : outputPorts) claim(pos, owner, casing);
    }

    private void claim(BlockPos pos, @Nullable BlockPos owner, @Nullable Material casing) {
        if (!(level.getBlockEntity(pos) instanceof MachinePort port)) return;
        port.setControllerPos(owner);
        if (port instanceof ItemPortBE itemPort)
            itemPort.setFormedMaterial(owner == null ? null : casing);
    }

    private void clearFormation() {
        claimPorts(null);

        formed = false;
        materials = Map.of();
        spec = SpecSheet.EMPTY;
        inputPorts = List.of();
        outputPorts = List.of();

        if (level != null) StructureIndex.unregister(level, worldPosition);
        structurePositions = List.of();
        setChanged();
    }

    public boolean revalidate() {
        if (level == null) return false;

        if (runValidation() instanceof FormationResult.Success success) {
            applyFormation(success);
            return true;
        }

        if (formed) clearFormation();
        return false;
    }

    public void invalidateStructure() {
        if (formed) clearFormation();
    }

    public List<ItemPort> itemInputs() { return resolvePorts(inputPorts); }
    public List<ItemPort> itemOutputs() { return resolvePorts(outputPorts); }

    private List<ItemPort> resolvePorts(List<BlockPos> positions) {
        if (level == null) return List.of();
        return positions.stream()
                .map(level::getBlockEntity)
                .filter(ItemPort.class::isInstance)
                .map(ItemPort.class::cast)
                .toList();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level == null || level.isClientSide() || !formed) return;

        if (runValidation() instanceof FormationResult.Success success) applyFormation(success);
        else clearFormation();
    }

    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide()) {
            claimPorts(null);
            StructureIndex.unregister(level, worldPosition);
        }
        super.setRemoved();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("DataVersion", DATA_VERSION);
        output.putBoolean("Formed", formed);

        ValueOutput mats = output.child("Materials");
        materials.forEach((material, count) -> mats.putInt(material.id(), count));

        if (activeRecipeId != null) output.putString("Recipe", activeRecipeId);
        output.putInt("Progress", progress);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        int version = input.getIntOr("DataVersion", 0);
        if (version > DATA_VERSION) {
            clearFormation();
            return;
        }

        formed = input.getBooleanOr("Formed", false);

        Map<Material, Integer> loaded = new LinkedHashMap<>();
        ValueInput mats = input.childOrEmpty("Materials");
        for (String id : mats.keySet()) {
            MaterialRegistry.find(id).ifPresent(m ->
                    loaded.put(m, mats.getIntOr(id, 0)));
        }

        materials = Map.copyOf(loaded);
        spec = SpecCalculator.fromCasings(materials);

        activeRecipeId = input.getStringOr("Recipe", "").isEmpty()
                ? null : input.getStringOr("Recipe", "");
        progress = input.getIntOr("Progress", 0);
    }

    public void serverTick() {
        if (!formed || level == null ) return;

        ProcessType process = definition.processType();
        if (process == null) return;

        List<ItemPort> inputs = itemInputs();
        List<ItemPort> outputs = itemOutputs();

        if (activeRecipeId == null) {
            if (level.getGameTime() % 20 != 0) return;

            MachineProcessor.findRunnable(process, spec, inputs, outputs)
                    .ifPresent(recipe -> {
                        activeRecipeId = recipe.id();
                        progress = 0;
                        setChanged();
                    });
            return;
        }

        MachineRecipe recipe = RecipeRegistry.find(activeRecipeId).orElse(null);
        if (recipe == null) {
            activeRecipeId = null;
            progress = 0;
            setChanged();
            return;
        }

        progress++;

        if (progress < recipe.durationTicks()) return;

        if (MachineProcessor.complete(recipe, inputs, outputs, level.getRandom())) {
            activeRecipeId = null;
            progress = 0;
        }
        setChanged();
    }
}
