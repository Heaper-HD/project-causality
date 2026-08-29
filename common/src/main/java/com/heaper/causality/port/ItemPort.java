package com.heaper.causality.port;

public interface ItemPort extends MachinePort {

    ItemDirectionGuard exposed();

    SlottedItemStorage storage();
}
