package com.heaper.causality.core.recipe;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;

public sealed interface RecipeIngredient {

    int count();

    String describe();

    record OfMaterial(Material material, MaterialForm form, int count)
            implements RecipeIngredient {

        @Override public String describe() {
            return count + "x " + material.id() + " " + form.name().toLowerCase();
        }
    }


    record OfComponent(String componentTypeId, int count) implements RecipeIngredient {

        @Override public String describe() {
            return count + "x any " + componentTypeId;
        }
    }

    record OfTier(int minTier, int count) implements RecipeIngredient {

        @Override public String describe() {
            return count + "x processor (tier " + minTier + "+)";
        }
    }

    record OfItem(String itemId, int count) implements RecipeIngredient {
        @Override
        public String describe() {
            return count + "x " + itemId;
        }
    }

    record OfTag(String tag, int count) implements RecipeIngredient {
        @Override
        public String describe() {
            return count + "x #" + tag;
        }
    }

    static RecipeIngredient of(Material material, MaterialForm form, int count) {
        return new OfMaterial(material, form, count);
    }

    static RecipeIngredient component(String componentTypeId, int count) {
        return new OfComponent(componentTypeId, count);
    }

    static RecipeIngredient tier(int minTier, int count) {
        return new OfTier(minTier, count);
    }

    static RecipeIngredient item(String itemId, int count) {
        return new OfItem(itemId, count);
    }

    static RecipeIngredient tag(String tag, int count) {
        return new OfTag(tag, count);
    }
}
