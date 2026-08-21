package com.heaper.causality.core.recipe;

import com.heaper.causality.core.spec.SpecAxis;
import com.heaper.causality.core.spec.SpecSheet;

import java.util.*;

public final class MachineRecipe {

    private final String id;
    private final ProcessType process;
    private final List<RecipeIngredient> inputs;
    private final List<RecipeOutput> outputs;
    private final int durationTicks;
    private final Map<SpecAxis, Double> requirements;

    private MachineRecipe(Builder b) {
        this.id = b.id;
        this.process = b.process;
        this.inputs = List.copyOf(b.inputs);
        this.outputs = List.copyOf(b.outputs);
        this.durationTicks = b.durationTicks;
        this.requirements = Map.copyOf(b.requirements);
    }

    MachineRecipe(
            String id, ProcessType process,
            List<RecipeIngredient> inputs, List<RecipeOutput> outputs,
            int durationTicks, Map<SpecAxis, Double> requirements) {
        this.id = id;
        this.process = process;
        this.inputs = List.copyOf(inputs);
        this.outputs = List.copyOf(outputs);
        this.durationTicks = durationTicks;
        this.requirements = Map.copyOf(requirements);
    }

    public String id() { return id; }
    public ProcessType process() { return process; }
    public List<RecipeIngredient> inputs() { return inputs; }
    public List<RecipeOutput> outputs() { return outputs; }
    public int durationTicks() { return durationTicks; }
    public Map<SpecAxis, Double> requirements() { return requirements; }

    public Optional<SpecAxis> unmetBy(SpecSheet spec) {
        return spec.firstUnmet(requirements);
    }

    public boolean canRunOn(SpecSheet spec) {
        return unmetBy(spec).isEmpty();
    }

    @Override
    public String toString() {
        return "MachineRecipe[" + id + "]";
    }

    public static Builder builder(String id, ProcessType process) {
        return new Builder(id, process);
    }

    public static final class Builder {
        private final String id;
        private final ProcessType process;
        private final List<RecipeIngredient> inputs = new ArrayList<>();
        private final List<RecipeOutput> outputs = new ArrayList<>();
        private int durationTicks = 100;
        private final Map<SpecAxis, Double> requirements = new LinkedHashMap<>();

        private Builder(String id, ProcessType process) {
            this.id = id;
            this.process = process;
        }

        public Builder input(RecipeIngredient ingredient) {
            inputs.add(ingredient);
            return this;
        }

        public Builder output(RecipeOutput output) {
            outputs.add(output);
            return this;
        }

        public Builder duration(int durationTicks) {
            this.durationTicks = durationTicks;
            return this;
        }

        public Builder requires(SpecAxis axis, double value) {
            requirements.put(axis, value);
            return this;
        }

        public MachineRecipe build() {
            if (inputs.isEmpty())
                throw new IllegalStateException("recipe " + id + " has no inputs");
            if (outputs.isEmpty())
                throw new IllegalStateException("recipe " + id + " has no outputs");
            if (durationTicks <= 0)
                throw new IllegalStateException("recipe " + id + " has non-positive duration");
            return new MachineRecipe(this);
        }
    }
}
