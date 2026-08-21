package com.heaper.causality.recipe;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.core.recipe.MachineRecipe;
import com.heaper.causality.core.recipe.RecipeCodecs;
import com.heaper.causality.core.recipe.RecipeRegistry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class MachineRecipeLoader extends SimpleJsonResourceReloadListener<MachineRecipe> {

    public MachineRecipeLoader() {
        super(RecipeCodecs.RECIPE, FileToIdConverter.json("machine_recipes"));
    }

    @Override
    protected void apply(Map<Identifier, MachineRecipe> parsed,
                         ResourceManager resourceManager, ProfilerFiller profiler) {

        RecipeRegistry.clear();

        int loaded = 0;
        for (MachineRecipe recipe : parsed.values()) {
            try {
                RecipeRegistry.add(recipe);
                loaded++;
            } catch (IllegalStateException e) {
                ProjectCausality.LOGGER.error("skipping recipe: {}", e.getMessage());
            }
        }

        RecipeRegistry.freeze();
        ProjectCausality.LOGGER.info("Loaded {} machine recipes", loaded);
    }
}
