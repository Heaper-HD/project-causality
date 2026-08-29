package com.heaper.causality.port;

import javax.sound.sampled.Port;

public record PortSettings(int stackLimit, boolean autoOutput, boolean autoInput) {

    public static final PortSettings DEFAULT = new PortSettings(0, true, false);

    public PortSettings withStackLimit(int limit) {
        return new PortSettings(limit, autoOutput, autoInput);
    }

    public PortSettings withAutoOutput(boolean value) {
        return new PortSettings(stackLimit, value, autoInput);
    }

    public PortSettings withAutoInput(boolean value) {
        return new PortSettings(stackLimit, autoOutput, value);
    }
}
