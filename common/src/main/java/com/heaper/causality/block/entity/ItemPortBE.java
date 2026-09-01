package com.heaper.causality.block.entity;

import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.menu.ItemPortMenu;
import com.heaper.causality.port.*;
import com.heaper.causality.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

public class ItemPortBE extends MachinePortBE implements ItemPort {

    private final PortSize size;
    private final SlottedItemStorage storage;
    private final ItemDirectionGuard exposed;

    public ItemPortBE(BlockPos pos, BlockState state, PortDirection direction, PortSize size) {
        super(ModBlockEntities.ITEM_PORT.get(), pos, state, direction, PortType.ITEM);
        this.size = size;
        this.storage = new SlottedItemStorage(size.slots(), 64, this::setChanged);
        this.exposed = new ItemDirectionGuard(storage, direction);
    }

    public SlottedItemStorage storage() {
        return storage;
    }

    @Override
    public ItemDirectionGuard exposed() {
        return exposed;
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public void pushTick() {
        if (level == null || storage.isEmpty()) return;
        if (level.getGameTime() % PUSH_INTERVAL != 0) return;

        Direction facing = getBlockState().getValue(ItemPortBlock.FACING);
        BlockPos target = worldPosition.relative(facing);

        ResourceHandler<ItemResource> destination = level.getCapability(
                Capabilities.Item.BLOCK, target, facing.getOpposite());
        if (destination == null) return;

        try (Transaction transaction = Transaction.openRoot()) {
            boolean moved = false;

            for (int slot = 0; slot < storage.size(); slot++) {
                int amount = storage.getAmountAsInt(slot);
                if (amount == 0) continue;

                ItemResource resource = storage.getResource(slot);
                int inserted = destination.insert(resource, amount, transaction);
                if (inserted <= 0) continue;

                storage.extract(slot, resource, inserted, transaction);
                moved = true;
            }

            if (moved) transaction.commit();
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("Items", SlottedItemStorage.SlotEntry.CODEC.listOf(),
                storage.toEntries());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.read("Items", SlottedItemStorage.SlotEntry.CODEC.listOf()).ifPresent(storage::loadEntries);
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ItemPortMenu(i, inventory, this);
    }
}
