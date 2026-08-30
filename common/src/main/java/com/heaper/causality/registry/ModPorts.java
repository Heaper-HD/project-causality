package com.heaper.causality.registry;

import com.heaper.causality.block.FluidPortBlock;
import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.port.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.*;
import java.util.stream.Collectors;

public final class ModPorts {


    public record ItemKey(PortDirection direction, PortSize size) {}

    public record FluidKey(PortDirection direction, FluidPortSpec spec) {}

    private static final Map<ItemKey, DeferredBlock<ItemPortBlock>> ITEM_PORTS =
            new LinkedHashMap<>();

    private static final Map<ItemKey, DeferredItem<BlockItem>> ITEM_PORT_ITEMS =
            new LinkedHashMap<>();

    private static final Map<FluidKey, DeferredBlock<FluidPortBlock>> FLUID_PORTS =
            new LinkedHashMap<>();

    private static final Map<FluidKey, DeferredItem<BlockItem>> FLUID_PORT_ITEMS =
            new LinkedHashMap<>();

    private static boolean registered = false;

    private ModPorts() {}

    static void registerAll() {
        if (registered) throw new IllegalStateException("ModPorts.registerAll() called twice");
        registered = true;

        itemPorts();
        fluidPorts();
    }

    private static void itemPorts() {
        for (PortDirection direction : PortDirection.values()) {
            for (PortSize size : PortSize.values()) {
                String name = size.prefix() + "_item_"
                        + direction.name().toLowerCase(Locale.ROOT) + "_port";

                DeferredBlock<ItemPortBlock> block = ModBlocks.BLOCKS.registerBlock(
                        name,
                        props -> new ItemPortBlock(direction, size, props),
                        ModPorts::portProperties);

                DeferredItem<BlockItem> item =
                        ModItems.ITEMS.registerSimpleBlockItem(name, block);

                ItemKey key = new ItemKey(direction, size);
                ITEM_PORTS.put(key, block);
                ITEM_PORT_ITEMS.put(key, item);
            }
        }
    }

    private static void fluidPorts() {
        for (PortDirection direction : PortDirection.values())
            for (FluidTier tier : FluidTier.values())
                for (FluidTanks tanks : FluidTanks.values()) {

                    FluidPortSpec spec = new FluidPortSpec(tier, tanks);
                    String name = spec.registerName(direction);

                    DeferredBlock<FluidPortBlock> block = ModBlocks.BLOCKS.registerBlock(
                            name,
                            props -> new FluidPortBlock(direction, spec, props),
                            ModPorts::portProperties);

                    DeferredItem<BlockItem> item =
                            ModItems.ITEMS.registerSimpleBlockItem(name, block);

                    FluidKey key = new FluidKey(direction, spec);
                    FLUID_PORTS.put(key, block);
                    FLUID_PORT_ITEMS.put(key, item);
                }
    }

    private static BlockBehaviour.Properties portProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(4.0f, 6.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops();
    }

    public static Optional<DeferredBlock<ItemPortBlock>> get(PortDirection d, PortSize s) {
        return Optional.ofNullable(ITEM_PORTS.get(new ItemKey(d, s)));
    }

    public static Map<ItemKey, DeferredBlock<ItemPortBlock>> all() {
        return Collections.unmodifiableMap(ITEM_PORTS);
    }

    public static Map<FluidKey, DeferredBlock<FluidPortBlock>> allFluid() {
        return Collections.unmodifiableMap(FLUID_PORTS);
    }


    public static Map<ItemKey, DeferredItem<BlockItem>> allItems() {
        return Collections.unmodifiableMap(ITEM_PORT_ITEMS);
    }

    public static Set<Block> blocks() {
        return ITEM_PORTS.values().stream()
                .map(DeferredBlock::get)
                .collect(Collectors.toUnmodifiableSet());
    }
}
