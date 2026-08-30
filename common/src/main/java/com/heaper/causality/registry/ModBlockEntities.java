package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.block.FluidPortBlock;
import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.block.MultiblockControllerBlock;
import com.heaper.causality.block.entity.FluidPortBE;
import com.heaper.causality.block.entity.ItemPortBE;
import com.heaper.causality.block.entity.MultiblockControllerBE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ProjectCausality.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MultiblockControllerBE>>
            MULTIBLOCK_CONTROLLER = BLOCK_ENTITIES.register(
                    "multiblock_controller",
            () -> new BlockEntityType<>(
                    (pos, state) -> {
                        if (state.getBlock() instanceof MultiblockControllerBlock c)
                            return new MultiblockControllerBE(pos, state, c.definition());
                        throw new IllegalStateException("controller BE on non-controller block: " + state);
                    },
                    ModMultiblocks.blocks()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemPortBE>>
            ITEM_PORT = BLOCK_ENTITIES.register("item_port",
            () -> new BlockEntityType<>(
                    (pos, state) -> {
                        if (state.getBlock() instanceof ItemPortBlock b)
                            return new ItemPortBE(pos, state, b.direction(), b.size());
                        throw new IllegalStateException(
                                "port BE on non-port block: " + state);
                    },
                    ModPorts.blocks()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FluidPortBE>>
            FLUID_PORT = BLOCK_ENTITIES.register("fluid_port",
            () -> new BlockEntityType<>(
                    (pos, state) -> {
                        if (state.getBlock() instanceof FluidPortBlock b)
                            return new FluidPortBE(pos, state, b.direction(), b.spec());
                        throw new IllegalStateException(
                                "port BE on non-port block: " + state);
                    },
                    ModPorts.fluidBlocks()));

    private ModBlockEntities() {}

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
