package com.heaper.causality.core.material;

public final class Materials {

    private static boolean initialized = false;

    private Materials() {}

    public static void init() {
        if (initialized) return;

        Elements.init();
        Alloys.init();
        Compounds.init();
        Minerals.init();

        MaterialRegistry.freeze();
        initialized = true;
    }
}
