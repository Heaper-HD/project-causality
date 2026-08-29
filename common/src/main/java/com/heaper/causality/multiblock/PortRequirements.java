package com.heaper.causality.multiblock;

import com.heaper.causality.port.PortDirection;
import com.heaper.causality.port.PortType;

import java.util.EnumMap;
import java.util.Map;

public final class PortRequirements {

    public record Bounds(int min, int max) {
        public static final Bounds ANY = new Bounds(0, Integer.MAX_VALUE);
    }

    public static final PortRequirements NONE = builder().build();

    public final Map<PortType, Bounds> inputs;
    public final Map<PortType, Bounds> outputs;

    private PortRequirements(Map<PortType, Bounds> inputs, Map<PortType, Bounds> outputs) {
        this.inputs = Map.copyOf(inputs);
        this.outputs = Map.copyOf(outputs);
    }

    public String checkOrNull(Map<PortType, Integer> inputCounts,
                              Map<PortType, Integer> outputCounts) {
        String error = check(inputs, inputCounts, PortDirection.INPUT);
        return error != null ? error : check(outputs, outputCounts, PortDirection.OUTPUT);
    }

    public static String check(Map<PortType, Bounds> required,
                               Map<PortType, Integer> actual,
                               PortDirection direction) {
        String word = direction == PortDirection.INPUT ? "input" : "output";

        for (Map.Entry<PortType, Bounds> entry : required.entrySet()) {
            PortType type = entry.getKey();
            Bounds bounds = entry.getValue();
            int count = actual.getOrDefault(type, 0);

            if (count < bounds.min())
                return "Needs at least " + bounds.min() + " " + type.id() + " " + word
                        + " port" + (bounds.min() == 1 ? "" : "s") + ", found " + count;

            if (count > bounds.max())
                return "Too many " + type.id() + " " + word + " ports: "
                        + count + ", maximum " + bounds.max();
        }
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final Map<PortType, Bounds> inputs = new EnumMap<>(PortType.class);
        private final Map<PortType, Bounds> outputs = new EnumMap<>(PortType.class);

        private Builder() {}

        public Builder input(PortType type, int min, int max) {
            inputs.put(type, new Bounds(min, max));
            return this;
        }

        public Builder output(PortType type, int min, int max) {
            outputs.put(type, new Bounds(min, max));
            return this;
        }

        public Builder forbidInput(PortType type) {
            return input(type, 0, 0);
        }

        public Builder forbidOutput(PortType type) {
            return output(type, 0, 0);
        }

        public PortRequirements build() {
            return new PortRequirements(inputs, outputs);
        }
    }
}
