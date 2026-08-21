package com.heaper.causality.core.spec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.*;

public record SpecAxis(String id, String unit, boolean higherIsBetter) {

    private static final Map<String, SpecAxis> BY_ID = new LinkedHashMap<>();

    public SpecAxis {

    }

    private static SpecAxis define(String id, String unit, boolean higherIsBetter) {
        SpecAxis axis = new SpecAxis(id, unit, higherIsBetter);
        BY_ID.put(id, axis);
        return axis;
    }

    public static final SpecAxis MAX_TEMPERATURE =
            define("max_temperature", "K", true);

    public static final SpecAxis STRUCTURAL_LOAD =
            define("structural_load", "MPa", true);

    public static final SpecAxis MAX_VOLTAGE =
            define("max_voltage", "V", true);

    public static final SpecAxis MAX_AMPERAGE =
            define("max_amperage", "A", true);

    public static final SpecAxis HEAT_DISSIPATION =
            define("heat_dissipation", "W", true);

    public static SpecAxis register(String id, String unit, boolean higherIsBetter) {
        if (BY_ID.containsKey(id))
            throw new IllegalStateException("duplicate spec axis: " + id);
        return define(id, unit, higherIsBetter);
    }

    public static Optional<SpecAxis> find(String id) {
        return Optional.ofNullable(BY_ID.get(id));
    }

    public static Collection<SpecAxis> all() {
        return Collections.unmodifiableCollection(BY_ID.values());
    }

    public static final Codec<SpecAxis> CODEC = Codec.STRING.comapFlatMap(
            id -> find(id)
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "unkown spec axis: " + id)),
            SpecAxis::id);

    public String describe() {
        return unit.isEmpty() ? id : id + " (" + unit + ")";
    }
}
