package com.heaper.causality.port;

import java.util.Locale;

public enum PortSize {

    SMALL(1),
    STANDARD(4),
    LARGE(8),
    HUGE(16);

    private final int slots;

    PortSize(int slots) {
        this.slots = slots;
    }

    public int slots() { return slots; }

    public String prefix() { return name().toLowerCase(Locale.ROOT); }

    public String displayName() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
