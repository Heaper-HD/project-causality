package com.heaper.causality.port;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.Arrays;

public class SlottedFluidStorage extends SnapshotJournal<SlottedFluidStorage.Snapshot> implements ResourceHandler<FluidResource> {

    public record Snapshot(FluidResource[] resources, int[] amounts) {}

    private final FluidResource[] resources;
    private final int[] amounts;
    private final int capacityPerTank;

    private final Runnable onChanged;

    public SlottedFluidStorage(int tanks, int capacityPerTank, Runnable onChanged) {
        this.resources = new FluidResource[tanks];
        this.amounts = new int[tanks];
        this.capacityPerTank = capacityPerTank;
        this.onChanged = onChanged;
        Arrays.fill(resources, FluidResource.EMPTY);
    }

    @Override
    protected Snapshot createSnapshot() {
        return new Snapshot(resources.clone(), amounts.clone());
    }

    @Override
    protected void revertToSnapshot(Snapshot snapshot) {
        System.arraycopy(snapshot.resources(), 0, resources, 0, resources.length);
        System.arraycopy(snapshot.amounts(), 0, amounts, 0, amounts.length);
    }

    @Override
    protected void onRootCommit(Snapshot originalState) {
        onChanged.run();
    }

    @Override
    public int size() {
        return resources.length;
    }

    @Override
    public FluidResource getResource(int index) {
        return resources[index];
    }

    @Override
    public int getAmountAsInt(int index) {
        return amounts[index];
    }

    @Override
    public long getAmountAsLong(int index) {
        return amounts[index];
    }

    @Override
    public int getCapacityAsInt(int index, FluidResource resource) {
        return capacityPerTank;
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return capacityPerTank;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return true;
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0) return 0;

        boolean empty = amounts[index] == 0;
        if (!empty && !resources[index].equals(resource)) return 0;

        int moved = Math.min(capacityPerTank - amounts[index], amount);
        if (moved <= 0) return 0;

        updateSnapshots(transaction);
        resources[index] = resource;
        amounts[index] += moved;
        return moved;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0) return 0;
        if (amounts[index] == 0 || !resources[index].equals(resource)) return 0;

        int moved = Math.min(amounts[index], amount);
        if (moved <= 0) return 0;

        updateSnapshots(transaction);
        amounts[index] -= moved;
        if (amounts[index] == 0) resources[index] = FluidResource.EMPTY;
        return moved;
    }

    @Override
    public int insert(FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0) return 0;

        int inserted = 0;

        for (int pass = 0; pass < 2 && inserted < amount; pass++) {
            boolean fillEmpty = pass == 1;

            for (int i = 0; i < resources.length && inserted < amount; i++) {
                if ((amounts[i] == 0) != fillEmpty) continue;
                inserted += insert(i, resource, amount - inserted, transaction);
            }
        }
        return inserted;
    }

    @Override
    public int extract(FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0) return 0;

        int extracted = 0;
        for (int i = 0; i < resources.length && extracted < amount; i++)
            extracted += extract(i, resource, amount - extracted, transaction);

        return extracted;
    }

    public boolean isEmpty() {
        for (int amount : amounts) if (amount > 0) return false;
        return true;
    }

    public record TankEntry(int tank, FluidResource resource, int amount) {

    }
}
