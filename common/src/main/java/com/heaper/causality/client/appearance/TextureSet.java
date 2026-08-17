package com.heaper.causality.client.appearance;

import java.util.Locale;

public enum TextureSet {
    METALLIC,
    ROUGH,
    SHINY;

    public String folder() {
        return name().toLowerCase(Locale.ROOT);
    }
}
