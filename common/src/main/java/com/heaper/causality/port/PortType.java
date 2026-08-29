package com.heaper.causality.port;

import java.util.Locale;

public enum PortType {

    ITEM,
    FLUID,
    ENERGY;

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String displayName() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
