package com.heaper.causality.multiblock;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialProperty;
import com.heaper.causality.core.spec.SpecAxis;
import com.heaper.causality.core.spec.SpecSheet;

import java.util.Map;

public class SpecCalculator {

    private SpecCalculator() {}

    public static SpecSheet fromCasings(Map<Material, Integer> materials) {
        if (materials.isEmpty()) return SpecSheet.EMPTY;

        SpecSheet.Builder builder = SpecSheet.builder();
        int totalBlocks = materials.values().stream().mapToInt(Integer::intValue).sum();

        for (Material material : materials.keySet()) {
            material.get(MaterialProperty.MELTING_POINT).ifPresent(mp ->
                    builder.weakestLink(SpecAxis.MAX_TEMPERATURE, mp * 0.6));

            material.get(MaterialProperty.TENSILE_STRENGTH).ifPresent(ts ->
                    builder.weakestLink(SpecAxis.STRUCTURAL_LOAD, ts));

            material.get(MaterialProperty.THERMAL_CONDUCTIVITY).ifPresent(tc ->
                    builder.weakestLink(SpecAxis.HEAT_DISSIPATION, tc * totalBlocks));
        }

        return builder.build();
    }
}
