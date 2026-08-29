package com.heaper.causality.block.entity;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.port.MachinePort;
import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.PortSettings;
import com.heaper.causality.port.PortType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
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
        this.formedMaterial = formedMaterial;
        setChanged();
        if (level != null)
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
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
    }
}
