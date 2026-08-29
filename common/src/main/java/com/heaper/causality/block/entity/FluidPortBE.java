package com.heaper.causality.block.entity;

import com.heaper.causality.block.MachinePortBlock;
import com.heaper.causality.port.*;
import com.heaper.causality.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

public class FluidPortBE extends MachinePortBE implements FluidPort {

    private final FluidPortSpec spec;
    private final SlottedFluidStorage storage;
    private final FluidDirectionGuard exposed;

    public FluidPortBE(BlockPos pos, BlockState state,
                       PortDirection direction, FluidPortSpec spec) {
        super(ModBlockEntities.FLUID_PORT.get(), pos, state, direction, PortType.FLUID);
        this.spec = spec;
        this.storage = new SlottedFluidStorage(
                spec.tankCount(), spec.capacityPerTank(), this::setChanged);
        this.exposed = new FluidDirectionGuard(storage, direction);
    }

    @Override
    public FluidPortSpec spec() {
        return spec;
    }

    @Override
    public SlottedFluidStorage storage() {
        return storage;
    }

    @Override
    public FluidDirectionGuard exposed() {
        return exposed;
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public void pushTick() {
        if (level == null || storage.isEmpty()) return;
        if (!settings().autoOutput()) return;
        if (level.getGameTime() % PUSH_INTERVAL != 0) return;

        Direction facing = getBlockState().getValue(MachinePortBlock.FACING);
        BlockPos target = worldPosition.relative(facing);

        ResourceHandler<FluidResource> destination = level.getCapability(
                Capabilities.Fluid.BLOCK, target, facing.getOpposite());
        if (destination == null) return;

        try (Transaction transaction = Transaction.openRoot()) {
            boolean moved = false;

            for (int tank = 0; tank < storage.size(); tank++) {
                int amount = storage.getAmountAsInt(tank);
                if (amount == 0) continue;

                FluidResource resource = storage.getResource(tank);
                int inserted = destination.insert(resource, amount, transaction);
                if (inserted <= 0) continue;

                storage.extract(tank, resource, inserted, transaction);
                moved = true;
            }

            if (moved) transaction.commit();
        }
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
        //return new FluidPortMenu(i, inv, this);
    }
}
