package com.heaper.causality.block.entity;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.port.MachinePort;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.PortSettings;
import com.heaper.causality.port.PortType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public abstract class MachinePortBE extends BlockEntity implements MachinePort, MenuProvider {

    protected static final int PUSH_INTERVAL = 10;

    private final PortDirection direction;
    private final PortType type;

    private @Nullable BlockPos controllerPos;
    private @Nullable Material formedMaterial;
    private PortSettings settings = PortSettings.DEFAULT;

    protected MachinePortBE(BlockEntityType<?> beType, BlockPos pos, BlockState state,
                            PortDirection direction, PortType type) {
        super(beType, pos, state);
        this.direction = direction;
        this.type = type;
    }

    @Override
    public PortDirection portDirection() {
        return direction;
    }

    @Override
    public PortType portType() {
        return type;
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

    public @Nullable Material formedMaterial() {
        return formedMaterial;
    }

    public void setFormedMaterial(@Nullable Material formedMaterial) {
        if (this.formedMaterial == formedMaterial) return;
        this.formedMaterial = formedMaterial;
        setChanged();
        if (level != null) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
        }
    }

    public PortSettings settings() {
        return settings;
    }

    public void setSettings(PortSettings settings) {
        this.settings = settings;
        setChanged();
    }

    public abstract void pushTick();

    public abstract boolean isEmpty();

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        if (controllerPos != null) output.putLong("Controller", controllerPos.asLong());
        if (formedMaterial != null) output.putString("Material", formedMaterial.id());

        output.putInt("StackLimit", settings.stackLimit());
        output.putBoolean("AutoOutput", settings.autoOutput());
        output.putBoolean("AutoInput", settings.autoInput());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        long owner = input.getLongOr("Controller", Long.MIN_VALUE);
        controllerPos = owner == Long.MIN_VALUE ? null : BlockPos.of(owner);

        formedMaterial = MaterialRegistry.find(input.getStringOr("Material", "")).orElse(null);

        settings = new PortSettings(
                input.getIntOr("StackLimit", 0),
                input.getBooleanOr("AutoOutput", true),
                input.getBooleanOr("AutoInput", false));

        if (level != null && level.isClientSide())
            level.setBlocksDirty(worldPosition,
                    Blocks.AIR.defaultBlockState(), getBlockState());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomOnly(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
