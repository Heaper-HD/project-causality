package com.heaper.causality.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class ResourceHandlerSlot extends Slot {

    private static final Container DUMMY = new SimpleContainer(0);

    private final ResourceHandler<ItemResource> handler;
    private final int index;
    private final boolean allowInsert;

    public ResourceHandlerSlot(ResourceHandler<ItemResource> handler, int index, int x, int y, boolean allowInsert) {
        super(DUMMY, index, x, y);
        this.handler = handler;
        this.index = index;
        this.allowInsert = allowInsert;
    }

    @Override
    public ItemStack getItem() {
        int amount = handler.getAmountAsInt(index);
        return amount == 0 ? ItemStack.EMPTY : handler.getResource(index).toStack(amount);
    }

    @Override
    public void set(ItemStack itemStack) {
        try (Transaction transaction = Transaction.openRoot()) {
            int current = handler.getAmountAsInt(index);
            if (current > 0)
                handler.extract(index, handler.getResource(index), current, transaction);

            if (!itemStack.isEmpty())
                handler.insert(index, ItemResource.of(itemStack), itemStack.getCount(), transaction);

            transaction.commit();
        }
    }

    @Override
    public ItemStack remove(int amount) {
        int available = handler.getAmountAsInt(index);
        if (available == 0) return ItemStack.EMPTY;

        ItemResource resource = handler.getResource(index);

        try (Transaction transaction = Transaction.openRoot()) {
            int removed = handler.extract(index, resource, amount, transaction);
            transaction.commit();
            return removed == 0 ? ItemStack.EMPTY : resource.toStack(removed);
        }
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        if (!allowInsert || itemStack.isEmpty()) return false;

        try (Transaction transaction = Transaction.openRoot()) {
            return handler.insert(index, ItemResource.of(itemStack), itemStack.getCount(), transaction) > 0;
        }
    }

    @Override
    public boolean mayPickup(Player player) {
        return handler.getAmountAsInt(index) > 0;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack) {
        return handler.getCapacityAsInt(index, ItemResource.of(itemStack));
    }

    @Override
    public void setChanged() {
    }
}
