package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.MaterialForm;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public enum Habit {
    CUBIC,
    OCTAHEDRAL,
    PRISMATIC,
    TABULAR,
    BOTRYOIDAL,
    ACICULAR,
    MASSIVE;

    private static final Set<String> FAMILIES = Set.of("crystal", "chunk");

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean appliesTo(MaterialForm form) {
        return FAMILIES.contains(form.family());
    }

    public Layer layerFor(MaterialForm form) {
        return new Layer("habit/" + id() + "/" + form.family(), LayerTint.OWN);
    }

    public Set<String> requiredTextures() {
        return FAMILIES.stream()
                .map(f -> "item/habit/" + id() + "/" + f)
                .collect(Collectors.toUnmodifiableSet());
    }
}
