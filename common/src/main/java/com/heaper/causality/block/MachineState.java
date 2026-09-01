package com.heaper.causality.block;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum MachineState implements StringRepresentable {
    OFF,
    IDLE,
    RUNNING;

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
