package com.heaper.causality.port;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public record FluidDirectionGuard(SlottedFluidStorage storage, PortDirection direction) implements ResourceHandler<FluidResource> {

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public FluidResource getResource(int index) {
        return storage.getResource(index);
    }

    @Override
    public int getAmountAsInt(int index) {
        return storage.getAmountAsInt(index);
    }

    @Override
    public long getAmountAsLong(int index) {
        return storage.getAmountAsLong(index);
    }

    @Override
    public int getCapacityAsInt(int index, FluidResource resource) {
        return storage.getCapacityAsInt(index, resource);
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return storage.getCapacityAsLong(index, resource);
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return storage.isValid(index, resource);
    }

    @Override
    public int insert(FluidResource resource, int amount, TransactionContext transaction) {
        if (direction == PortDirection.OUTPUT) return 0;
        return storage.insert(resource, amount, transaction);
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (direction == PortDirection.OUTPUT) return 0;
        return storage().insert(index, resource, amount, transaction);
    }

    @Override
    public int extract(FluidResource resource, int amount, TransactionContext transaction) {
        if (direction == PortDirection.INPUT) return 0;
        return storage.extract(resource, amount, transaction);
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (direction == PortDirection.INPUT) return 0;
        return storage().extract(index, resource, amount, transaction);
    }
}


