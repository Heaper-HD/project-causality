package com.heaper.causality.port;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlottedItemStorage extends SnapshotJournal<SlottedItemStorage.Snapshot> implements ResourceHandler<ItemResource> {

    public record Snapshot(ItemResource[] resources, int[] amounts) {}

    public record SlotEntry(int slot, ItemResource resource, int amount) {
        public static final Codec<SlotEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.INT.fieldOf("slot").forGetter(SlotEntry::slot),
                ItemResource.CODEC.fieldOf("resource").forGetter(SlotEntry::resource),
                Codec.INT.fieldOf("amount").forGetter(SlotEntry::amount)
        ).apply(i, SlotEntry::new));
    }

    private final ItemResource[] resources;
    private final int[] amounts;
    private final int slotCapacity;

    private final Runnable onChanged;

    public SlottedItemStorage(int slots, int slotCapacity, Runnable onChanged) {
        this.resources = new ItemResource[slots];
        this.amounts = new int[slots];
        this.slotCapacity = slotCapacity;
        this.onChanged = onChanged;
        Arrays.fill(resources, ItemResource.EMPTY);
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

    public List<SlotEntry> toEntries() {
        List<SlotEntry> out = new ArrayList<>();
        for (int i = 0; i < resources.length; i++)
            if (amounts[i] > 0) out.add(new SlotEntry(i, resources[i], amounts[i]));
        return out;
    }

    public void loadEntries(List<SlotEntry> entries) {
        Arrays.fill(resources, ItemResource.EMPTY);
        Arrays.fill(amounts, 0);
        for (SlotEntry e : entries) {
            if (e.slot() < 0 || e.slot() >= resources.length) continue;
            resources[e.slot()] = e.resource();
            amounts[e.slot()] = e.amount();
        }
    }

    @Override public int size() { return resources.length; }

    @Override public ItemResource getResource(int index) { return resources[index]; }

    @Override
    public int getAmountAsInt(int index) { return amounts[index]; }

    @Override
    public long getAmountAsLong(int index) { return amounts[index]; }

    @Override
    public int getCapacityAsInt(int index, ItemResource resource) {
        return Math.min(slotCapacity, resource.getMaxStackSize());
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        return Math.min(slotCapacity, resource.getMaxStackSize());
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return true;
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0) return 0;

        boolean empty = amounts[index] == 0;
        if (!empty && !resources[index].equals(resource)) return 0;
        if (!isValid(index, resource)) return 0;

        int room = getCapacityAsInt(index, resource) - amounts[index];
        int moved = Math.min(room, amount);
        if (moved <= 0) return 0;

        updateSnapshots(transaction);
        resources[index] = resource;
        amounts[index] += moved;
        return moved;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0 ) return 0;
        if (amounts[index] == 0 || !resources[index].equals(resource)) return 0;

        int moved = Math.min(amounts[index], amount);
        if (moved <= 0) return 0;

        updateSnapshots(transaction);
        amounts[index] -= moved;
        if (amounts[index] == 0) resources[index] = ItemResource.EMPTY;
        return moved;
    }

    @Override
    public int insert(ItemResource resource, int amount, TransactionContext transaction) {
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
    public int extract(ItemResource resource, int amount, TransactionContext transaction) {
        if (resource.isEmpty() || amount <= 0) return 0;

        int extracted = 0;
        for (int i = 0; i< resources.length && extracted < amount; i++)
            extracted += extract(i, resource, amount - extracted, transaction);

        return extracted;
    }

    public ItemStack stackInSlot(int slot) {
        return amounts[slot] == 0
                ? ItemStack.EMPTY
                : resources[slot].toStack(amounts[slot]);
    }

    public boolean isEmpty() {
        for (int amount : amounts) if (amount > 0) return false;
        return true;
    }
}
