package com.heaper.causality.core.recipe.derive;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.core.material.composition.Composition;
import com.heaper.causality.core.material.composition.RecoveryProfile;
import com.heaper.causality.core.recipe.RecipeOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecoveryCalculator {

    private static final double OUTPUT_UNITS_PER_INPUT = 2.0;

    private RecoveryCalculator() {}

    public static List<RecipeOutput> recover(Material material, RecoveryProfile profile) {
        Map<Material, Double> fractions = directFractions(material);
        if (fractions.isEmpty()) return List.of();

        List<RecipeOutput> outputs = new ArrayList<>();

        for (Map.Entry<Material, Double> entry : fractions.entrySet()) {
            Material component = entry.getKey();
            double fraction = entry.getValue();

            if (fraction < profile.resolution()) continue;

            if (!component.hasForm(MaterialForm.DUST)) continue;

            double expected = fraction * OUTPUT_UNITS_PER_INPUT * profile.efficiency();

            int guaranteed = (int) Math.floor(expected);
            double remainder = expected - guaranteed;

            if (guaranteed > 0)
                outputs.add(new RecipeOutput.OfMaterial(
                        component, MaterialForm.DUST, guaranteed));

            if (remainder > 0.01)
                outputs.add(new RecipeOutput.OfMaterial(
                        component, MaterialForm.DUST, 1, round(remainder)));
        }

        return List.copyOf(outputs);
    }

    private static Map<Material, Double> directFractions(Material material) {
        return switch (material.composition()) {
            case Composition.Mixture mixture -> mixture.fractions();

            case Composition.Compound compound -> {
                double totalMass = 0;
                for (Composition.Part part : compound.parts())
                    totalMass += part.count() * massOf(part.material());

                if (totalMass <= 0) yield Map.of();

                Map<Material, Double> byMass = new java.util.LinkedHashMap<>();
                for (Composition.Part part : compound.parts())
                    byMass.merge(part.material(),
                            part.count() * massOf(part.material()) / totalMass,
                            Double::sum);
                yield byMass;
            }

            case Composition.Element ignored -> Map.of();
        };
    }

    private static double massOf(Material material) {
        return switch (material.composition()) {
            case Composition.Element element -> element.atomicMass();
            case Composition.Compound compound -> {
                double total = 0;
                for (Composition.Part part : compound.parts()) {
                    total += part.count() * massOf(part.material());
                }
                yield total;
            }
            case Composition.Mixture mixture -> {
                double total = 0;
                for (Map.Entry<Material, Double> e : mixture.fractions().entrySet())
                    total += e.getValue() * massOf(e.getKey());
                yield total;
            }
        };
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
