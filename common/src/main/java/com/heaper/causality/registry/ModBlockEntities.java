package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.block.MultiblockControllerBlock;
import com.heaper.causality.block.entity.ItemPortBE;
import com.heaper.causality.block.entity.MultiblockControllerBE;
import com.heaper.causality.port.ItemPort;
import com.heaper.causality.port.PortDirection;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

import static com.heaper.causality.registry.ModBlocks.ITEM_INPUT_PORT;
import static com.heaper.causality.registry.ModBlocks.ITEM_OUTPUT_PORT;

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
                        return new MultiblockControllerBE(pos, state, null);
                    },
                    Set.of(ModBlocks.TEST_CONTROLLER.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemPortBE>>
            ITEM_PORT = BLOCK_ENTITIES.register("item_port",
            () -> new BlockEntityType<>(
                    (pos, state) -> new ItemPortBE(pos, state,
                            state.getBlock() instanceof ItemPortBlock b
                                    ? b.direction()
                                    : PortDirection.INPUT),
                    Set.of(ITEM_INPUT_PORT.get(), ITEM_OUTPUT_PORT.get())
            ));

    private ModBlockEntities() {}

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
