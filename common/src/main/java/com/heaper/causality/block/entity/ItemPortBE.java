package com.heaper.causality.block.entity;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.menu.ItemPortMenu;
import com.heaper.causality.port.DirectionGuard;
import com.heaper.causality.port.ItemPort;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.SlottedItemStorage;
import com.heaper.causality.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class ItemPortBE extends BlockEntity implements ItemPort, MenuProvider {

    private static final int DATA_VERSION = 1;
    private static final int SLOTS = 4;
    private static final int SLOT_CAPACITY = 64;

    private final PortDirection direction;
    private final SlottedItemStorage storage;
    private final DirectionGuard exposed;

    private @Nullable BlockPos controllerPos;

    public ItemPortBE(BlockPos pos, BlockState state, PortDirection direction) {
        super(ModBlockEntities.ITEM_PORT.get(), pos, state);
        this.direction = direction;
        this.storage = new SlottedItemStorage(SLOTS, SLOT_CAPACITY, this::setChanged);
        this.exposed = new DirectionGuard(storage, direction);
    }

    @Override
    public PortDirection portDirection() {
        return direction;
    }

    @Override
    public DirectionGuard exposed() {
        return exposed;
    }

    @Override
    public SlottedItemStorage storage() {
        return storage;
    }

    @Override
    public @Nullable BlockPos controllerPos() {
        return controllerPos;
    }

    @Override
    public void setControllerPos(@Nullable BlockPos controllerPos) {
        this.controllerPos = controllerPos;
        setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("DataVersion", DATA_VERSION);

        if (controllerPos != null)
            output.putLong("Controller", controllerPos.asLong());

        output.store("Items", SlottedItemStorage.SlotEntry.CODEC.listOf(), storage.toEntries());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        long owner = input.getLongOr("Controller", Long.MIN_VALUE);
        controllerPos = owner == Long.MIN_VALUE ? null : BlockPos.of(owner);

        input.read("Items", SlottedItemStorage.SlotEntry.CODEC.listOf()).ifPresent(storage::loadEntries);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block." + ProjectCausality.MODID + "."
        + (direction == PortDirection.INPUT ? "item_input_port" : "item_output_port"));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ItemPortMenu(i, inventory, this);
    }
}
