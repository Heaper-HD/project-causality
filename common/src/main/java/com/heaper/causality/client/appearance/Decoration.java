package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.MaterialForm;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.heaper.causality.core.material.MaterialForm.*;

public enum Decoration {
    RUST         (new LayerTint.Fixed(0x8C4A21), "chunk", "grit", "ingot", "plate"),
    PATINA       (new LayerTint.Fixed(0x4FA88B), "ingot", "plate"),
    TARNISH      (new LayerTint.Fixed(0x3A3038), "ingot", "plate"),
    DULL_OXIDE   (new LayerTint.Fixed(0xB8BCBE), "ingot", "plate"),
    MILL_SCALE   (new LayerTint.Fixed(0x2A2E38), "ingot", "plate"),
    PITTED       (new LayerTint.Fixed(0x000000), "ingot", "plate", "rod"),

    WET          (LayerTint.NONE,                "chunk", "grit"),
    DUSTY        (new LayerTint.Fixed(0xC9BFAE), "chunk", "grit"),
    CRACKED      (new LayerTint.Fixed(0x000000), "chunk", "crystal"),
    EFFLORESCENT (new LayerTint.Fixed(0xE8E4D8), "chunk", "grit", "waste"),

    MACHINED     (new LayerTint.Fixed(0xFFFFFF), "rod", "gear", "fastener", "ring"),
    OILED        (new LayerTint.Fixed(0x6B5A2E), "rod", "gear", "fastener"),
    WORN         (new LayerTint.Fixed(0x000000), "gear", "rod", "plate", "ring"),
    GRAINED      (new LayerTint.Fixed(0x000000), "ingot", "plate"),
    LAMINATED    (new LayerTint.Fixed(0xFFFFFF), "plate"),

    CLUMPED      (new LayerTint.Fixed(0x000000), "dust", "grit"),
    HYGROSCOPIC  (LayerTint.NONE,                "dust"),

    VITREOUS     (LayerTint.NONE,                "waste", "crystal"),
    SOOTED       (new LayerTint.Fixed(0x1C1A18), "waste", "ingot"),

    IRIDESCENT   (LayerTint.NONE,                "ingot", "crystal", "chunk"),
    FROSTED      (new LayerTint.Fixed(0xD8ECF5), "ingot", "chunk"),
    ANODIZED     (LayerTint.NONE,                "plate");

    private final LayerTint tint;
    private final Set<String> families;

    Decoration(LayerTint tint, String... families) {
        this.tint = tint;
        this.families = Set.of(families);
    }

    public boolean appliesTo(MaterialForm form) {
        return families.contains(form.family());
    }

    public Set<String> families() {
        return families;
    }

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Layer layerFor(MaterialForm form) {
        return new Layer("overlay/" + id() + "/" + form.family(), tint);
    }

    public Set<String> requiredTexture() {
        return families.stream()
                .map(f -> "item/overlay/" + id() + "/" + f)
                .collect(Collectors.toUnmodifiableSet());
    }
}
