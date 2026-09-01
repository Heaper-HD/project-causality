package com.heaper.causality.core.recipe;

import java.util.*;

public final class RecipeRegistry {

    private static final Map<String, MachineRecipe> BY_ID = new LinkedHashMap<>();
    private static final Map<ProcessType, List<MachineRecipe>> BY_PROCESS = new LinkedHashMap<>();

    private static boolean frozen = false;

    private RecipeRegistry() {}

    public static MachineRecipe add(MachineRecipe recipe) {
        return register(recipe);
    }

    public static Optional<MachineRecipe> find(String id) {
        return Optional.ofNullable(BY_ID.get(id));
    }

    static MachineRecipe register(MachineRecipe recipe) {
        if (frozen) throw new IllegalStateException("recipe registry frozen: " + recipe.id());
        if (BY_ID.putIfAbsent(recipe.id(), recipe) != null)
            throw new IllegalStateException("duplicate recipe id: " + recipe.id());

        BY_PROCESS.computeIfAbsent(recipe.process(), p -> new ArrayList<>()).add(recipe);

        return recipe;
    }

    public static Collection<MachineRecipe> all() {
        return Collections.unmodifiableCollection(BY_ID.values());
    }

    public static List<MachineRecipe> forProcess(ProcessType process) {
        return Collections.unmodifiableList(BY_PROCESS.getOrDefault(process, List.of()));
    }

    public static int size() { return BY_ID.size(); }

    public static boolean isFrozen() { return frozen; }

    public static void freeze() {
        if (frozen) return;
        frozen = true;
    }

    public static void clear() {
        BY_ID.clear();
        BY_PROCESS.clear();
        frozen = false;
    }
}
