package com.heaper.causality.datagen;

import com.google.gson.JsonElement;
import com.heaper.causality.ProjectCausality;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.core.material.MaterialProperty;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.core.material.composition.RecoveryProfile;
import com.heaper.causality.core.recipe.*;
import com.heaper.causality.core.recipe.derive.RecoveryCalculator;
import com.heaper.causality.core.spec.SpecAxis;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MachineRecipeProvider implements DataProvider {

    private final PackOutput.PathProvider path;

    private final Set<String> claimed = new HashSet<>();

    private final List<MachineRecipe> recipes = new ArrayList<>();

    public MachineRecipeProvider(PackOutput output) {
        this.path = output.createPathProvider(
                PackOutput.Target.DATA_PACK, "machine_recipes");
    }

    @Override
    public String getName() {
        return "Project Causality Machine Recipes";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        recipes.clear();
        claimed.clear();

        handWritten();
        generated();

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (MachineRecipe recipe : recipes) {
            JsonElement json = RecipeCodecs.RECIPE
                    .encodeStart(JsonOps.INSTANCE, recipe)
                    .getOrThrow(msg -> new IllegalStateException(
                            "failed to encode " + recipe.id() + ": " + msg));

            Path file = path.json(Identifier.fromNamespaceAndPath(
                    ProjectCausality.MODID, recipe.id()));

            futures.add(DataProvider.saveStable(cache, json, file));
        }

        ProjectCausality.LOGGER.info("Generated {} machine recipes", recipes.size());
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private void add(MachineRecipe recipe) {
        if (!claimed.add(recipe.id())) {
            ProjectCausality.LOGGER.debug("skipping derived recipe {} - already claimed",
                    recipe.id());
            return;
        }
        recipes.add(recipe);
    }

    private void handWritten() {

    }

    private void generated() {
        for (Material material : MaterialRegistry.all()) {
            crushing(material);
            milling(material);
        }
    }

    private void crushing(Material material) {
        if (!material.hasForm(MaterialForm.RAW)) return;
        if (!material.hasForm(MaterialForm.CRUSHED)) return;

        add(MachineRecipe.builder("crush_" + material.id(), ProcessType.MACERATOR)
                .input(RecipeIngredient.of(material, MaterialForm.RAW, 1))
                .output(new RecipeOutput.OfMaterial(material, MaterialForm.CRUSHED, 2))
                .duration(baseDuration(material))
                .requires(SpecAxis.STRUCTURAL_LOAD, requiredLoad(material))
                .build());
    }

    private void milling(Material material) {
        if (!material.hasForm(MaterialForm.CRUSHED)) return;

        List<RecipeOutput> outputs =
                RecoveryCalculator.recover(material, RecoveryProfile.MACERATION);

        if (outputs.isEmpty()) return;

        MachineRecipe.Builder builder =
                MachineRecipe.builder("mill_" + material.id(), ProcessType.MACERATOR)
                    .input(RecipeIngredient.of(material, MaterialForm.CRUSHED, 1))
                    .duration(baseDuration(material))
                    .requires(SpecAxis.STRUCTURAL_LOAD, requiredLoad(material));

        outputs.forEach(builder::output);
        add(builder.build());
    }

    private static int baseDuration(Material material) {
        double hardness = material.get(MaterialProperty.HARDNESS).orElse(3.0);
        return (int) Math.round(60 + hardness * 20);
    }

    private static double requiredLoad(Material material) {
        double hardness = material.get(MaterialProperty.HARDNESS).orElse(3.0);
        return Math.round(hardness * 40);
    }
}
