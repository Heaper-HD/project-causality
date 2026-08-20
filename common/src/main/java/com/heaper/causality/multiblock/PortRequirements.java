package com.heaper.causality.multiblock;

public record PortRequirements(int minItemInputs, int maxItemInputs, int minItemOutputs, int maxItemOutputs) {

    public static final PortRequirements NONE = new PortRequirements(0, 0, 0, 0);

    public static PortRequirements items(int maxInputs, int maxOutputs) {
        return new PortRequirements(1, maxInputs, 1, maxOutputs);
    }

    public String checkOrNull(int inputs, int outputs) {
        if (inputs < minItemInputs)
            return "Needs at least " + minItemInputs + " item input port"
                + (minItemInputs == 1 ? "" : "s") + ", found" + inputs;
        if (inputs > maxItemInputs)
            return "Too many item input ports: " + inputs + ", maximum" + maxItemInputs;
        if (outputs < minItemOutputs)
            return "Needs at least " + minItemOutputs + " item input port"
                    + (minItemOutputs == 1 ? "" : "s") + ", found" + outputs;
        if (outputs > maxItemOutputs)
            return "Too many item input ports: " + inputs + ", maximum" + maxItemOutputs;
        return null;
    }
}
