package com.heaper.causality.port;

import net.minecraft.core.BlockPos;
import org.jspecify.annotations.Nullable;

public interface MachinePort {

    PortDirection portDirection();

    @Nullable BlockPos controllerPos();

    void setControllerPos(BlockPos pos);

    default boolean isClaimed() { return controllerPos() != null; }
}
