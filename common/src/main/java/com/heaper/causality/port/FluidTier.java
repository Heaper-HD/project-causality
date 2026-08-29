package com.heaper.causality.port;

import java.util.Locale;

public enum FluidTier {

    SMALL(8),
    STANDARD(32),
    LARGE(128),
    HUGE(512);

    private final int totalBuckets;

    FluidTier(int totalBuckets) {
        this.totalBuckets = totalBuckets;
    }

    public int totalBuckets() { return totalBuckets; }

    public String prefix() { return name().toLowerCase(Locale.ROOT); }

    public String displayName() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
