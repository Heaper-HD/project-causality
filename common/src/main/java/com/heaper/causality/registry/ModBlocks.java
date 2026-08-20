package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.block.MultiblockControllerBlock;
import com.heaper.causality.component.ComponentType;
import com.heaper.causality.component.ComponentTypes;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.multiblock.Multiblocks;
import com.heaper.causality.port.PortDirection;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ProjectCausality.MODID);

    private static final Map<ComponentKey, DeferredBlock<Block>> COMPONENTS = new LinkedHashMap<>();

    private static final Map<ComponentKey, DeferredItem<? extends Item>> COMPONENT_ITEMS = new LinkedHashMap<>();

    public record ComponentKey(ComponentType type, Material material) {}

    public static final DeferredBlock<MultiblockControllerBlock> TEST_CONTROLLER =
            BLOCKS.registerBlock(
                    "test_controller",
                    props -> new MultiblockControllerBlock(Multiblocks.TEST_3X3, props),
                    () -> BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(5.0f, 6.0f)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> TEST_CONTROLLER_ITEM =
            ModItems.ITEMS.registerSimpleBlockItem("test_controller", TEST_CONTROLLER);

    public static final DeferredBlock<ItemPortBlock> ITEM_INPUT_PORT =
            BLOCKS.registerBlock(
                    "item_input_port",
                    props -> new ItemPortBlock(PortDirection.INPUT, props),
                    ModBlocks::portProperties
            );

    public static final DeferredItem<BlockItem> ITEM_INPUT_PORT_ITEM =
            ModItems.ITEMS.registerSimpleBlockItem("item_input_port", ITEM_INPUT_PORT);

    public static final DeferredBlock<ItemPortBlock> ITEM_OUTPUT_PORT =
            BLOCKS.registerBlock(
                    "item_output_port",
                    props -> new ItemPortBlock(PortDirection.OUTPUT, props),
                    ModBlocks::portProperties
            );

    public static final DeferredItem<BlockItem> ITEM_OUTPUT_PORT_ITEM =
            ModItems.ITEMS.registerSimpleBlockItem("item_output_port", ITEM_OUTPUT_PORT);

    private ModBlocks() {}

    public static void register(IEventBus modEventBus) {
        if (!MaterialRegistry.isFrozen())
            throw new IllegalStateException("Materials.init() must be run before ModBlocks.register()");

        int count = 0;
        for (ComponentType type : ComponentTypes.ALL) {
            for (Material material : MaterialRegistry.all()) {
                if (!type.eligible(material)) continue;

                String name = type.nameFor(material);
                ComponentKey key = new ComponentKey(type, material);

                DeferredBlock<Block> block = BLOCKS.registerBlock(
                        name,
                        props -> type.create(material, props),
                        () -> type.propertiesFor(material));

                DeferredItem<BlockItem> item = ModItems.ITEMS.registerSimpleBlockItem(name, block);

                COMPONENTS.put(key, block);
                COMPONENT_ITEMS.put(key, item);
                count++;
            }
        }

        ProjectCausality.LOGGER.info("Registered {} component blocks", count);
        BLOCKS.register(modEventBus);
    }

    public static Optional<DeferredBlock<Block>> get(ComponentType type, Material material) {
        return Optional.ofNullable(COMPONENTS.get(new ComponentKey(type, material)));
    }

    public static Map<ComponentKey, DeferredBlock<Block>> all() {
        return Collections.unmodifiableMap(COMPONENTS);
    }

    public static Map<ComponentKey, DeferredItem<? extends Item>> allItems() {
        return Collections.unmodifiableMap(COMPONENT_ITEMS);
    }

    private static BlockBehaviour.Properties portProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(4.0f, 6.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops();
    }
}
