package com.heaper.causality.recipe;

import com.heaper.causality.core.recipe.RecipeIngredient;
import com.heaper.causality.core.recipe.RecipeOutput;
import com.heaper.causality.material.Unification;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.Optional;

public class IngredientResolver {

    private IngredientResolver() {}

    public static Optional<ItemResource> resolve(RecipeIngredient ingredient) {
        return switch (ingredient) {
            case RecipeIngredient.OfMaterial m -> Unification.resolve(m.material(), m.form()).map(ItemResource::of);

            case RecipeIngredient.OfItem i -> itemById(i.itemId()).map(ItemResource::of);

            case RecipeIngredient.OfComponent ignored -> Optional.empty();
            case RecipeIngredient.OfTier ignored -> Optional.empty();
            case RecipeIngredient.OfTag ignored -> Optional.empty();
        };
    }

    public static Optional<ItemResource> resolve(RecipeOutput output) {
        return switch (output) {
            case RecipeOutput.OfMaterial m ->
                Unification.resolve(m.material(), m.form()).map(ItemResource::of);
            case RecipeOutput.OfItem i -> itemById(i.itemId()).map(ItemResource::of);
        };
    }

    private static Optional<Item> itemById(String id) {
        return BuiltInRegistries.ITEM.getOptional(Identifier.parse(id));
    }
}
