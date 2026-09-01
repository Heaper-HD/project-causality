package com.heaper.causality.client.appearance;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum TextureSet implements StringRepresentable {
    METALLIC,
    CRYSTALLINE,
    CAST,
    ROUGH,
    SHINY;

    public String folder() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getSerializedName() {
        return folder();
    }
}
