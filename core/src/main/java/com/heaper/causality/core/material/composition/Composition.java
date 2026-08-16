package com.heaper.causality.core.material.composition;

import com.heaper.causality.core.material.ElementCategory;
import com.heaper.causality.core.material.Material;

import java.util.List;
import java.util.Map;

public sealed interface Composition {
    record Compound(List<Part> parts) implements Composition {}

    record Mixture(Map<Material, Double> fractions) implements Composition {}

    record Element(String symbol, int atomicNumber, double atomicMass, ElementCategory category) implements Composition {}

    record Part(Material material, int count) {}
}
