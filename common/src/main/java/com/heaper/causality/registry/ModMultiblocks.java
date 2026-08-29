package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.block.MultiblockControllerBlock;
import com.heaper.causality.multiblock.MultiblockDefinition;
import com.heaper.causality.multiblock.MultiblockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
public class ModMultiblocks {

    private static final Map<MultiblockDefinition, DeferredBlock<MultiblockControllerBlock>>
        CONTROLLERS = new LinkedHashMap<>();

    private static final Map<MultiblockDefinition, DeferredItem<BlockItem>>
        CONTROLLER_ITEMS = new LinkedHashMap<>();

    private ModMultiblocks() {}

    static void registerAll() {
        if (!MultiblockRegistry.isFrozen())
            throw new IllegalStateException("Multiblocks.init() must run before ModBlocks.register()");

        for (MultiblockDefinition definition : MultiblockRegistry.all()) {
            String name = definition.id() + "_controller";

            DeferredBlock<MultiblockControllerBlock> block = ModBlocks.BLOCKS.registerBlock(
                    name,
                    props -> new MultiblockControllerBlock(definition, props),
                    ModMultiblocks::controllerProperties);

            DeferredItem<BlockItem> item =
                    ModItems.ITEMS.registerSimpleBlockItem(name, block);

            CONTROLLERS.put(definition, block);
            CONTROLLER_ITEMS.put(definition, item);
        }

        ProjectCausality.LOGGER.info("Registered {} multiblock controllers", CONTROLLERS.size());
    }

    private static BlockBehaviour.Properties controllerProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops();
    }

    public static Optional<DeferredBlock<MultiblockControllerBlock>> get(MultiblockDefinition d) {
        return Optional.ofNullable(CONTROLLERS.get(d));
    }

    public static Map<MultiblockDefinition, DeferredBlock<MultiblockControllerBlock>> all() {
        return Collections.unmodifiableMap(CONTROLLERS);
    }

    private static Map<MultiblockDefinition, DeferredItem<BlockItem>> allItems() {
        return Collections.unmodifiableMap(CONTROLLER_ITEMS);
    }

    public static java.util.Set<net.minecraft.world.level.block.Block> blocks() {
        return CONTROLLERS.values().stream()
                .map(DeferredBlock::get)
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }
}
