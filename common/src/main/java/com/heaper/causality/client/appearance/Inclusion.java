package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record Inclusion(String sprite, Material source, Set<String> families) {

    public Inclusion {
        families = Set.copyOf(families);
    }

    public static Inclusion of(String sprite, Material source, String... families) {
        return new Inclusion(sprite, source, Set.of(families));
    }

    public boolean appliesTo(MaterialForm form) {
        return families.contains(form.family());
    }

    public Layer layerFor(MaterialForm form) {
        return new Layer("inclusion/" + sprite + "/" + form.family(),
                new LayerTint.From(source));
    }

    public Set<String> requiredTextures() {
        return families.stream()
                .map(f -> "item/inclusion" + sprite + "/" + f)
                .collect(Collectors.toUnmodifiableSet());
    }
}
