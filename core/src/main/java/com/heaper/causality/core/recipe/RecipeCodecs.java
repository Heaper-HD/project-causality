package com.heaper.causality.core.recipe;

import com.heaper.causality.core.material.MaterialCodecs;
import com.heaper.causality.core.spec.SpecAxis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;

public final class RecipeCodecs {

    private RecipeCodecs() {}

    private static final MapCodec<RecipeIngredient.OfMaterial> OF_MATERIAL =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    MaterialCodecs.MATERIAL.fieldOf("material")
                            .forGetter(RecipeIngredient.OfMaterial::material),
                    MaterialCodecs.FORM.fieldOf("form")
                            .forGetter(RecipeIngredient.OfMaterial::form),
                    Codec.INT.optionalFieldOf("count", 1)
                            .forGetter(RecipeIngredient.OfMaterial::count)
            ).apply(i, RecipeIngredient.OfMaterial::new));

    private static final MapCodec<RecipeIngredient.OfComponent> OF_COMPONENT =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("component")
                            .forGetter(RecipeIngredient.OfComponent::componentTypeId),
                    Codec.INT.optionalFieldOf("count", 1)
                            .forGetter(RecipeIngredient.OfComponent::count)
            ).apply(i, RecipeIngredient.OfComponent::new));

    private static final MapCodec<RecipeIngredient.OfTier> OF_TIER =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.INT.fieldOf("min_tier")
                            .forGetter(RecipeIngredient.OfTier::minTier),
                    Codec.INT.optionalFieldOf("count", 1)
                            .forGetter(RecipeIngredient.OfTier::count)
            ).apply(i, RecipeIngredient.OfTier::new));

    private static final MapCodec<RecipeIngredient.OfItem> OF_ITEM =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("item")
                            .forGetter(RecipeIngredient.OfItem::itemId),
                    Codec.INT.optionalFieldOf("count", 1)
                            .forGetter(RecipeIngredient.OfItem::count)
            ).apply(i, RecipeIngredient.OfItem::new));

    private static final MapCodec<RecipeIngredient.OfTag> OF_TAG =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("tag")
                            .forGetter(RecipeIngredient.OfTag::tag),
                    Codec.INT.optionalFieldOf("count", 1)
                            .forGetter(RecipeIngredient.OfTag::count)
            ).apply(i, RecipeIngredient.OfTag::new));

    public static final Codec<RecipeIngredient> INGREDIENT =
            Codec.STRING.dispatch("type",
                    RecipeCodecs::ingredientTypeOf,
                    RecipeCodecs::ingredientCodecFor);

    private static String ingredientTypeOf(RecipeIngredient ingredient) {
        return switch (ingredient) {
            case RecipeIngredient.OfMaterial ignored -> "material";
            case RecipeIngredient.OfComponent ignored -> "component";
            case RecipeIngredient.OfTier ignored -> "processor";
            case RecipeIngredient.OfItem ignored -> "item";
            case RecipeIngredient.OfTag ignored -> "tag";
        };
    }

    private static MapCodec<? extends RecipeIngredient> ingredientCodecFor(String type) {
        return switch (type) {
            case "material" -> OF_MATERIAL;
            case "component" -> OF_COMPONENT;
            case "processor" -> OF_TIER;
            case "item" -> OF_ITEM;
            case "tag" -> OF_TAG;
            default -> OF_MATERIAL;
        };
    }

    private static final MapCodec<RecipeOutput.OfMaterial> OUT_MATERIAL =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    MaterialCodecs.MATERIAL.fieldOf("material")
                            .forGetter(RecipeOutput.OfMaterial::material),
                    MaterialCodecs.FORM.fieldOf("form")
                            .forGetter(RecipeOutput.OfMaterial::form),
                    Codec.INT.optionalFieldOf("count", 1)
                            .forGetter(RecipeOutput.OfMaterial::count),
                    Codec.DOUBLE.optionalFieldOf("chance", 1.0)
                            .forGetter(RecipeOutput.OfMaterial::chance)
            ).apply(i, RecipeOutput.OfMaterial::new));

    private static final MapCodec<RecipeOutput.OfItem> OUT_ITEM =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("item").forGetter(RecipeOutput.OfItem::itemId),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(RecipeOutput.OfItem::count),
                    Codec.DOUBLE.optionalFieldOf("chance", 1.0)
                            .forGetter(RecipeOutput.OfItem::chance)
            ).apply(i, RecipeOutput.OfItem::new));

    public static final Codec<RecipeOutput> OUTPUT =
            Codec.STRING.dispatch("type",
                    output -> output instanceof RecipeOutput.OfMaterial ? "material" : "item",
                    type -> "material".equals(type) ? OUT_MATERIAL : OUT_ITEM);

    public static final Codec<ProcessType> PROCESS_TYPE =
            Codec.STRING.xmap(ProcessType::new, ProcessType::id);

    private static final Codec<Map<SpecAxis, Double>> REQUIREMENTS =
            Codec.unboundedMap(SpecAxis.CODEC, Codec.DOUBLE);

    public static final Codec<MachineRecipe> RECIPE =
            RecordCodecBuilder.create(i -> i.group(
                    Codec.STRING.fieldOf("id").forGetter(MachineRecipe::id),
                    PROCESS_TYPE.fieldOf("process").forGetter(MachineRecipe::process),
                    INGREDIENT.listOf().fieldOf("inputs").forGetter(MachineRecipe::inputs),
                    OUTPUT.listOf().fieldOf("outputs").forGetter(MachineRecipe::outputs),
                    Codec.INT.optionalFieldOf("duration", 100)
                            .forGetter(MachineRecipe::durationTicks),
                    REQUIREMENTS.optionalFieldOf("requires", Map.of())
                            .forGetter(MachineRecipe::requirements)
            ).apply(i, MachineRecipe::new));
}
