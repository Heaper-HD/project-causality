package com.heaper.causality.port;

public enum FluidTanks {

    X1(1),
    X2(2),
    X4(4),
    X8(8);

    private final int count;

    FluidTanks(int count) {
        this.count = count;
    }

    public int count() { return count; }

    public String prefix() { return "x" + count; }

    public String displayName() { return count + "x"; }
}
