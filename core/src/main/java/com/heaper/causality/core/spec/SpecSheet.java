package com.heaper.causality.core.spec;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class SpecSheet {

    private final Map<SpecAxis, Double> ratings;

    private SpecSheet(Map<SpecAxis, Double> ratings) {
        this.ratings = Map.copyOf(ratings);
    }

    public static final SpecSheet EMPTY = new SpecSheet(Map.of());

    public Optional<Double> get(SpecAxis axis) {
        return Optional.ofNullable(ratings.get(axis));
    }

    public double getOr(SpecAxis axis, double fallback) {
        return ratings.getOrDefault(axis, fallback);
    }

    public boolean has(SpecAxis axis) { return ratings.containsKey(axis); }

    public Map<SpecAxis, Double> ratings() { return ratings; }

    public boolean isEmpty() { return ratings.isEmpty(); }

    public Optional<SpecAxis> firstUnmet(Map<SpecAxis, Double> required) {
        for (Map.Entry<SpecAxis, Double> req : required.entrySet()) {
            SpecAxis axis = req.getKey();
            Double provided = ratings.get(axis);

            if (provided == null) return Optional.of(axis);

            boolean ok = axis.higherIsBetter()
                    ? provided >= req.getValue()
                    : provided <= req.getValue();

            if (!ok) return Optional.of(axis);
        }
        return Optional.empty();
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private final Map<SpecAxis, Double> ratings = new LinkedHashMap<>();

        private Builder() {}

        public Builder set(SpecAxis axis, double value) {
            ratings.put(axis, value);
            return this;
        }

        public Builder weakestLink(SpecAxis axis, double value) {
            ratings.merge(axis, value, Math::min);
            return this;
        }

        public SpecSheet build() {
            return ratings.isEmpty() ? EMPTY : new SpecSheet(ratings);
        }
    }
}
