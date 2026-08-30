package com.heaper.causality.datagen;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.block.FluidPortBlock;
import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.block.MultiblockControllerBlock;
import com.heaper.causality.client.appearance.Appearance;
import com.heaper.causality.client.appearance.AppearanceRegistry;
import com.heaper.causality.client.appearance.Layer;
import com.heaper.causality.client.appearance.LayerTint;
import com.heaper.causality.core.material.MaterialStack;
import com.heaper.causality.material.MaterialItem;
import com.heaper.causality.multiblock.MultiblockDefinition;
import com.heaper.causality.registry.ModBlocks;
import com.heaper.causality.registry.ModItems;
import com.heaper.causality.registry.ModMultiblocks;
import com.heaper.causality.registry.ModPorts;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.*;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, ProjectCausality.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        materialItems(itemModels);
        componentBlocks(blockModels);
        controllers(blockModels);
        ports(blockModels);
        fluidPorts(blockModels);
    }

    private void materialItems(ItemModelGenerators itemModels) {
        for (Map.Entry<MaterialStack, DeferredItem<MaterialItem>> entry : ModItems.all().entrySet()) {
            MaterialStack stack = entry.getKey();
            MaterialItem item = entry.getValue().get();

            List<Layer> layers = AppearanceRegistry.get(stack.material()).layers(stack.form());
            Identifier model = ModelLocationUtils.getModelLocation(item);

            TextureMapping mapping = new TextureMapping();
            for (int i = 0; i < layers.size(); i++) {
                mapping.put(ModModelTemplates.layerSlot(i),
                        material("item/" + layers.get(i).texture()));
            }

            ModModelTemplates.layeredItem(layers.size())
                    .create(model, mapping, itemModels.modelOutput);

            if (layers.size() == 1 && layers.getFirst().tint() instanceof LayerTint.None) {
                itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(model));
                continue;
            }

            ItemTintSource[] tints = new ItemTintSource[layers.size()];
            for (int i = 0; i < layers.size(); i++) {
                tints[i] = new Constant(layers.get(i).tint().resolve(stack.material()));
            }

            itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, tints));
        }
    }

    private void componentBlocks(BlockModelGenerators blockModels) {
        for (Map.Entry<ModBlocks.ComponentKey, DeferredBlock<Block>> entry : ModBlocks.all().entrySet()) {
            ModBlocks.ComponentKey key = entry.getKey();
            Appearance appearance = AppearanceRegistry.get(key.material());

            cube(blockModels, entry.getValue().get(),
                    "block/" + appearance.set().folder() + "/" + key.type().id(),
                    appearance.color());
        }
    }

    private Identifier machineFace(BlockModelGenerators blockModels,
                                   Block block, String frontPath, String suffix) {
        TextureMapping mapping = new TextureMapping()
                .put(ModModelTemplates.CASING, material("block/metallic/casing"))
                .put(ModModelTemplates.FRONT, material(frontPath));

        return ModModelTemplates.MACHINE_FACE.createWithSuffix(
                block, suffix, mapping, blockModels.modelOutput);
    }

    private void ports(BlockModelGenerators blockModels) {
        for (Map.Entry<ModPorts.ItemKey, DeferredBlock<ItemPortBlock>> entry : ModPorts.all().entrySet()) {
            ModPorts.ItemKey key = entry.getKey();
            Block block = entry.getValue().get();

            Identifier model = machineFace(blockModels, block,
                    "block/port/" + key.size().prefix() + "_"
                            + key.direction().name().toLowerCase(Locale.ROOT),
                    "");

            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(model))
                            .with(BlockModelGenerators.ROTATION_FACING));

            blockModels.registerSimpleItemModel(block, model);
        }
    }

    private void fluidPorts(BlockModelGenerators blockModels) {
        for (Map.Entry<ModPorts.FluidKey, DeferredBlock<FluidPortBlock>> entry : ModPorts.allFluid().entrySet()) {
            ModPorts.FluidKey key = entry.getKey();
            Block block = entry.getValue().get();

            Identifier model = machineFace(blockModels, block,
                    "block/port/fluid_" + key.spec().tier().prefix() + "_"
                            + key.direction().name().toLowerCase(Locale.ROOT),
                    "");

            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(model))
                            .with(BlockModelGenerators.ROTATION_FACING));

            blockModels.registerSimpleItemModel(block, model);
        }
    }

    private void controllers(BlockModelGenerators blockModels) {
        for (Map.Entry<MultiblockDefinition, DeferredBlock<MultiblockControllerBlock>> entry : ModMultiblocks.all().entrySet()) {
            MultiblockDefinition definition = entry.getKey();
            Block block = entry.getValue().get();

            Identifier off = machineFace(blockModels, block,
                    "block/" + definition.frontTexture(), "");
            Identifier on = machineFace(blockModels, block,
                    "block/" + definition.frontTexture() + "_on", "_on");

            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block)
                            .with(PropertyDispatch.initial(MultiblockControllerBlock.ACTIVE)
                                    .select(false, BlockModelGenerators.plainVariant(off))
                                    .select(true, BlockModelGenerators.plainVariant(on)))
                            .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));

            blockModels.registerSimpleItemModel(block, off);
        }
    }

    private void cube(BlockModelGenerators blockModels, Block block, String texturePath, int color) {
        Identifier texture =
                Identifier.fromNamespaceAndPath(ProjectCausality.MODID, texturePath);

        Identifier model = ModModelTemplates.TINTED_CUBE_ALL.create(
                block, TextureMapping.cube(new Material(texture)), blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                block, BlockModelGenerators.plainVariant(model)));

        blockModels.itemModelOutput.accept(
                block.asItem(), ItemModelUtils.tintedModel(model, new Constant(color)));
    }

    private void orientable(BlockModelGenerators blockModels, Block block, String frontPath, String sidePath, boolean horizontalOnly) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.FRONT, material(frontPath))
                .put(TextureSlot.SIDE, material(sidePath))
                .put(TextureSlot.TOP, material(sidePath));

        Identifier model = ModelTemplates.CUBE_ORIENTABLE.create(
                block, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(model))
                        .with(horizontalOnly
                                ? BlockModelGenerators.ROTATION_HORIZONTAL_FACING
                                : BlockModelGenerators.ROTATION_FACING));

        blockModels.registerSimpleItemModel(block, model);
    }

    private static Material material(String path) {
        return new Material(Identifier.fromNamespaceAndPath(ProjectCausality.MODID, path));
    }
}
