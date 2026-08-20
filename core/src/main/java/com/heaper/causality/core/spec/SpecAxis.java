package com.heaper.causality.core.spec;

public record SpecAxis(String id, String unit, boolean higherIsBetter) {
    public static final SpecAxis MAX_TEMPERATURE =
            new SpecAxis("max_temperature", "K", true);

    public static final SpecAxis STRUCTURAL_LOAD =
            new SpecAxis("structural_load", "MPa", true);

    public static final SpecAxis MAX_VOLTAGE =
            new SpecAxis("max_voltage", "V", true);

    public static final SpecAxis MAX_AMPERAGE =
            new SpecAxis("max_amperage", "A", true);

    public static final SpecAxis HEAT_DISSIPATION =
            new SpecAxis("heat_dissipation", "W", true);

    public String describe() {
        return unit.isEmpty() ? id : id + " (" + unit + ")";
    }
}
