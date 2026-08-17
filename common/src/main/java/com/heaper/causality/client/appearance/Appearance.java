package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.MaterialForm;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public record Appearance(TextureSet set, int color, Map<MaterialForm, String> overrides) {
    public Appearance(TextureSet set, int color) {
        this(set, color, Map.of());
    }

    public Optional<String> override(MaterialForm form) {
        return Optional.ofNullable(overrides.get(form));
    }

    public String texturePath(MaterialForm form) {
        return override(form).orElseGet(
                () -> set.folder() + "/" + form.name().toLowerCase(Locale.ROOT));
    }

    public boolean isExclusive(MaterialForm form) {
        return overrides.containsKey(form);
    }
}
