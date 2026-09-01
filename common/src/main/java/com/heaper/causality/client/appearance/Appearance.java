package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.MaterialForm;
import com.mojang.datafixers.kinds.App;
import org.jspecify.annotations.Nullable;

import java.util.*;

public record Appearance(
        TextureSet set, int color,
        Map<MaterialForm, Layer> exclusives,
        @Nullable Habit habit,
        @Nullable Inclusion inclusion,
        List<Decoration> decorations) {

    public static final int MAX_LAYERS = 4;

    public Appearance {
        exclusives = Map.copyOf(exclusives);
        decorations = List.copyOf(decorations);
    }

    public Appearance(TextureSet set, int color) {
        this(set, color, Map.of(), null, null, List.of());
    }

    public Appearance with(Inclusion inclusion) {
        return new Appearance(set, color, exclusives, habit, inclusion, decorations);
    }

    public Appearance withHabit(Habit habit) {
        return new Appearance(set, color, exclusives, habit, inclusion, decorations);
    }

    public Appearance decorated(Decoration... added) {
        return new Appearance(set, color, exclusives, habit, inclusion, List.of(added));
    }

    public Appearance exclusive(MaterialForm form, Layer layer) {
        Map<MaterialForm, Layer> merged = new HashMap<>(exclusives);
        merged.put(form, layer);
        return new Appearance(set, color, merged, habit, inclusion, decorations);
    }

    public List<Layer> layers(MaterialForm form) {
        Layer base = exclusives.get(form);
        if (base == null && habit != null && habit.appliesTo(form))
            base = habit.layerFor(form);
        if (base == null)
            base = Layer.of(basePath(form));

        if (inclusion == null && decorations.isEmpty()) return List.of(base);

        List<Layer> result = new ArrayList<>(MAX_LAYERS);
        result.add(base);

        if (inclusion != null && inclusion.appliesTo(form))
            result.add(inclusion.layerFor(form));

        for (Decoration d : decorations) {
            if (result.size() >= MAX_LAYERS) break;
            if (d.appliesTo(form)) result.add(d.layerFor(form));
        }

        return List.copyOf(result);
    }

    public String basePath(MaterialForm form) {
        return set.folder() + "/" + form.name().toLowerCase(Locale.ROOT);
    }
}
