package com.heaper.causality.port;

public interface FluidPort extends MachinePort {

    FluidDirectionGuard exposed();

    SlottedFluidStorage storage();

    FluidPortSpec spec();
}
