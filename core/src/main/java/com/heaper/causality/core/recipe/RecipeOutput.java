package com.heaper.causality.core.recipe;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;

public sealed interface RecipeOutput {

    int count();

    double chance();

    String describe();

    record OfMaterial(Material material, MaterialForm form, int count, double chance) implements RecipeOutput {
        public OfMaterial(Material material, MaterialForm form, int count) {
            this(material, form, count, 1.0);
        }

        @Override
        public String describe() {
            String base = count + "x " + material.id() + " " + form.name().toLowerCase();
            return chance >= 1.0 ? base : base + " (" + Math.round(chance * 100) + "%)";
        }
    }

    record OfItem(String itemId, int count, double chance) implements RecipeOutput {
        public OfItem(String itemId, int count) {
            this(itemId, count, 1.0);
        }

        @Override
        public String describe() {
            return count + "x " + itemId;
        }
    }
}
