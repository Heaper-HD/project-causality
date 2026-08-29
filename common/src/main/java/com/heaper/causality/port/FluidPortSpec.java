package com.heaper.causality.port;

import java.util.Locale;

public record FluidPortSpec(FluidTier tier, FluidTanks tanks) {

    public int capacityPerTank() {
        return tier.totalBuckets() * 1000 / tanks.count();
    }

    public int tankCount() { return tanks.count(); }

    public String registerName(PortDirection direction) {
        return tier.prefix() + "_" + tanks.prefix() + "_fluid_"
                + direction.name().toLowerCase(Locale.ROOT) + "_port";
    }

    public String displayName(PortDirection direction) {
        return tier.displayName() + " " + tanks.displayName() + " Fluid "
                + (direction == PortDirection.INPUT ? "Input" : "Output") + " Port";
    }
}
