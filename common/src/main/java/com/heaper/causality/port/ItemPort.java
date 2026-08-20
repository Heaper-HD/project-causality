package com.heaper.causality.port;

public interface ItemPort extends MachinePort {

    DirectionGuard exposed();

    SlottedItemStorage storage();
}
