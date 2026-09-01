package com.heaper.causality.recipe;

import com.heaper.causality.core.recipe.*;
import com.heaper.causality.core.spec.SpecSheet;
import com.heaper.causality.port.ItemPort;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public final class MachineProcessor {

    private MachineProcessor() {}

    public static Optional<MachineRecipe> findRunnable(
            ProcessType process, SpecSheet spec,
            List<ItemPort> inputs, List<ItemPort> outputs) {
        for (MachineRecipe recipe : RecipeRegistry.forProcess(process)) {
            if (!recipe.canRunOn(spec)) continue;
            if (!inputsAvailable(recipe, inputs)) continue;
            if (!outputsFit(recipe, outputs)) continue;
            return Optional.of(recipe);
        }
        return Optional.empty();
    }

    private static boolean inputsAvailable(MachineRecipe recipe, List<ItemPort> inputs) {
        try (Transaction transaction = Transaction.openRoot()) {
            return consumeInputs(recipe, inputs, transaction);
        }
    }

    private static boolean outputsFit(MachineRecipe recipe, List<ItemPort> outputs) {
        try (Transaction transaction = Transaction.openRoot()) {
            return placeOutputs(recipe, outputs, transaction, null);
        }
    }

    public static boolean consume(MachineRecipe recipe,
                                   List<ItemPort> inputs) {
        try (Transaction transaction = Transaction.openRoot()) {
            if (!consumeInputs(recipe, inputs, transaction)) return false;
            transaction.commit();
            return true;
        }
    }

    public static boolean deliver(MachineRecipe recipe, List<ItemPort> outputs, RandomSource random) {
        try (Transaction transaction = Transaction.openRoot()) {
            if (!placeOutputs(recipe, outputs, transaction, random)) return false;
            transaction.commit();
            return true;
        }

    }

    private static boolean consumeInputs(MachineRecipe recipe,
                                         List<ItemPort> ports, Transaction transaction) {
        for (RecipeIngredient ingredient : recipe.inputs()) {
            Optional<ItemResource> resolved = IngredientResolver.resolve(ingredient);
            if (resolved.isEmpty()) return false;

            ItemResource resource = resolved.get();
            int remaining = ingredient.count();

            for (ItemPort port : ports) {
                if (remaining <= 0) break;
                remaining -= port.storage().extract(resource, remaining, transaction);
            }

            if (remaining > 0) return false;
        }
        return true;
    }

    private static boolean placeOutputs(MachineRecipe recipe,
                                        List<ItemPort> ports, Transaction transaction,
                                        RandomSource random) {
        for (RecipeOutput output : recipe.outputs()) {
            if (random != null && output.chance() < 1.0 && random.nextDouble() > output.chance())
                continue;

            Optional<ItemResource> resolved = IngredientResolver.resolve(output);
            if (resolved.isEmpty()) return false;

            ItemResource resource = resolved.get();
            int remaining = output.count();

            for (ItemPort port : ports) {
                if (remaining <= 0) break;
                remaining -= port.storage().insert(resource, remaining, transaction);
            }

            if (remaining > 0) return false;
        }

        return true;
    }
}
