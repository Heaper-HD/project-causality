package com.heaper.causality.core.material;

public record MaterialProperty<T>(String id, Class<T> type, String unit) {
    // Classification
    public static final MaterialProperty<PhysicalState> STATE = of("state", PhysicalState.class);
    public static final MaterialProperty<Occurrence> OCCURRENCE = of("occurrence", Occurrence.class);

    // Thermal
    public static final MaterialProperty<Integer> MELTING_POINT = of("melting_point", Integer.class, "K");
    public static final MaterialProperty<Integer> BOILING_POINT = of("boiling_point", Integer.class, "K");
    public static final MaterialProperty<Integer> SPECIFIC_HEAT = of("specific_heat", Integer.class, "J/(kg*K)");
    public static final MaterialProperty<Integer> HEAT_OF_FUSION = of("heat_of_fusion", Integer.class, "kJ/kg");
    public static final MaterialProperty<Integer> THERMAL_CONDUCTIVITY = of("thermal_conductivity", Integer.class, "W/(m*K)");

    // Mechanical
    public static final MaterialProperty<Double> DENSITY = of("density", Double.class, "g/cm3");
    public static final MaterialProperty<Double> HARDNESS = of("hardness", Double.class, "Mohs");
    public static final MaterialProperty<Integer> TENSILE_STRENGTH = of("tensile_strength", Integer.class, "MPa");
    public static final MaterialProperty<Double> DUCTILITY = of("tensile_strength", Double.class, "% elongation");

    // Electrical & magnetic
    public static final MaterialProperty<Double> RESISTIVITY = of("resistivity", Double.class, "ohm*m");
    public static final MaterialProperty<Double> MAGNETIC_SUSCEPTIBILITY = of("magnetic_susceptibility", Double.class, "dimensionless");

    // Nuclear life
    public static final MaterialProperty<Double> NEUTRON_ABSORPTION = of("neutron_absorption", Double.class, "barn");
    public static final MaterialProperty<Double> HALF_LIFE = of("half_life", Double.class, "s");

    // Factories
    public static <T> MaterialProperty<T> of(String id, Class<T> type) {
        return new MaterialProperty<>(id, type, "");
    }

    public static <T> MaterialProperty<T> of(String id, Class<T> type, String unit) {
        return new MaterialProperty<>(id, type, unit);
    }

    public String describe() {
        return unit.isEmpty() ? id : id + " (" + unit + ")";
    }
}
