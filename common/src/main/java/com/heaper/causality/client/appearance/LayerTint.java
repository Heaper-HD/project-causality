package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.Material;

public sealed interface LayerTint {

    int UNTINTED = 0xFFFFFF;

    LayerTint OWN = new Own();
    LayerTint NONE = new None();

    int resolve(Material owner);

    record Own() implements LayerTint {
        @Override
        public int resolve(Material owner) {
            return AppearanceRegistry.colorOf(owner);
        }
    }

    record Fixed(int rbg) implements LayerTint {
        @Override
        public int resolve(Material owner) {
            return rbg;
        }
    }

    record From(Material source) implements LayerTint {
        @Override
        public int resolve(Material owner) {
            return AppearanceRegistry.colorOf(source);
        }
    }

    record None() implements LayerTint {
        @Override
        public int resolve(Material owner) {
            return UNTINTED;
        }
    }
}
