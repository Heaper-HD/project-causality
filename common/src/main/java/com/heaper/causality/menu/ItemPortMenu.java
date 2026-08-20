package com.heaper.causality.menu;

import com.heaper.causality.block.entity.ItemPortBE;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ItemPortMenu extends AbstractContainerMenu {
    private final ItemPortBE port;
    private final int portSlots;

    public ItemPortMenu(int containerId, Inventory playerInventory, ItemPortBE port) {
        super(ModMenus.ITEM_PORT.get(), containerId);
        this.port = port;
        this.portSlots = port.storage().size();
        boolean allowInsert = port.portDirection() == PortDirection.INPUT;

        int startX = (176 - portSlots * 18) / 2;
        for (int i = 0; i < portSlots; i++)
            addSlot(new ResourceHandlerSlot(port.storage(), i, startX + i * 18, 35, allowInsert));

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
    }

    private static ItemPortBE resolve(Inventory inventory, BlockPos blockPos) {
        if (inventory.player.level().getBlockEntity(blockPos) instanceof ItemPortBE be) return be;
        throw new IllegalStateException("no item port at " + blockPos);
    }

    @Override
    public boolean stillValid(Player player) {
        return !port.isRemoved()
                && player.distanceToSqr(Vec3.atCenterOf(port.getBlockPos())) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (index < portSlots) {
            if (!moveItemStackTo(stack, portSlots, slots.size(), true))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, portSlots, false))
                return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return original;
    }

    public static ItemPortMenu fromNetwork(int containerId, Inventory inv, RegistryFriendlyByteBuf buf) {
        return new ItemPortMenu(containerId, inv, resolve(inv, buf.readBlockPos()));
    }
}
