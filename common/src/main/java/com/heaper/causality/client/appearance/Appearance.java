package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.MaterialForm;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public record Appearance(TextureSet set, int color, Map<MaterialForm, Layer> exclusives) {

    public static final int MAX_LAYERS = 4;

    public Appearance {
        exclusives = Map.copyOf(exclusives);
    }

    public Appearance(TextureSet set, int color) {
        this(set, color, Map.of());
    }

    public List<Layer> layers(MaterialForm form) {
        Layer exclusive = exclusives.get(form);
        if (exclusive != null) return List.of(exclusive);

        return List.of(Layer.of(basePath(form)));
    }

    public String basePath(MaterialForm form) {
        return set.folder() + "/" + form.name().toLowerCase(Locale.ROOT);
    }
}
