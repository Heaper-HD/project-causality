package com.heaper.causality.client.appearance;

public record Layer(String texture, LayerTint tint) {

    public static Layer of(String texture) {
        return new Layer(texture, LayerTint.OWN);
    }

    public static Layer untinted(String texture) {
        return new Layer(texture, LayerTint.NONE);
    }

    public static Layer fixed(String texture, int rbg) {
        return new Layer(texture, new LayerTint.Fixed(rbg));
    }
}
