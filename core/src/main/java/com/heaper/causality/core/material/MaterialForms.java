package com.heaper.causality.core.material;

import com.heaper.causality.core.material.composition.Composition;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static com.heaper.causality.core.material.MaterialForm.*;

public final class MaterialForms {
    public static Set<MaterialForm> derive(Material.Builder b) {
        if (b.state() != PhysicalState.SOLID) return EnumSet.of(FLUID);
        if (b.composition() instanceof Composition.Mixture mix) {
            double metallic = mix.fractions().entrySet().stream()
                    .filter(entry -> entry.getKey().composition() instanceof Composition.Element el
                    && el.category().isMetal())
                    .mapToDouble(Map.Entry::getValue)
                    .sum();

            return metallic >= 0.5
                    ? EnumSet.of(INGOT, PLATE, ROD, DUST, NUGGET, SMALL_DUST, TINY_DUST)
                    : EnumSet.noneOf(MaterialForm.class);
        }
        if (!(b.composition() instanceof Composition.Element e))
            return EnumSet.noneOf(MaterialForm.class);
        if (b.occurrence() == Occurrence.SYNTHETIC)
            return EnumSet.noneOf(MaterialForm.class);
        return switch (e.category()) {
            case ALKALI_METAL, ALKALINE_EARTH_METAL ->
                    EnumSet.of(INGOT, DUST, SMALL_DUST);
            case TRANSITION_METAL, POST_TRANSITION_METAL ->
                EnumSet.of(INGOT, PLATE, ROD, DUST, NUGGET, SMALL_DUST, TINY_DUST);
            case LANTHANIDE, ACTINIDE ->
                    EnumSet.of(INGOT, DUST, SMALL_DUST);
            case METALLOID -> EnumSet.of(INGOT, PLATE, DUST);
            case REACTIVE_NONMETAL, HALOGEN -> EnumSet.of(DUST, SMALL_DUST);
            case NOBLE_GAS, UNKNOWN -> EnumSet.noneOf(MaterialForm.class);
        };
    }
}
