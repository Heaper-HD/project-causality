package com.heaper.causality.component;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialProperty;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class ComponentType {
    private final String id;
    private final List<Predicate<Material>> requirements;
    private final BiFunction<Material, BlockBehaviour.Properties, Block> factory;
    private final BiFunction<Material, BlockBehaviour.Properties, BlockBehaviour.Properties> propertyTuner;

    private ComponentType(Builder b) {
        this.id = b.id;
        this.requirements = List.copyOf(b.requirements);
        this.factory = b.factory;
        this.propertyTuner = b.propertyTuner;
    }

    public String id() { return id; }

    public String nameFor(Material material) {
        return material.id() + "_" + id;
    }

    public boolean eligible(Material material) {
        if (material.excludes(id)) return false;
        return requirements.stream().allMatch(r -> r.test(material));
    }

    public BlockBehaviour.Properties propertiesFor(Material material) {
        return propertyTuner.apply(material, BlockBehaviour.Properties.of());
    }

    public Block create(Material material, BlockBehaviour.Properties props) {
        return factory.apply(material, props);
    }

    public static Builder builder (String id) { return new Builder(id); }

    public static final class Builder {
        private final String id;
        private final List<Predicate<Material>> requirements = new ArrayList<>();
        private BiFunction<Material, BlockBehaviour.Properties, Block> factory;
        private BiFunction<Material, BlockBehaviour.Properties, BlockBehaviour.Properties> propertyTuner = (m, p) -> p;

        private Builder(String id) { this.id = id; }

        public Builder requires(MaterialProperty<?> property) {
            requirements.add(m -> m.has(property));
            return this;
        }

        public <T extends Comparable<T>> Builder requires(MaterialProperty<T> property, T minimum) {
            requirements.add(m -> m.get(property)
                    .map(v -> v.compareTo(minimum) >= 0)
                    .orElse(false));
            return this;
        }

        public Builder requires(Predicate<Material> rule) {
            requirements.add(rule);
            return this;
        }

        public Builder blockFactory(BiFunction<Material, BlockBehaviour.Properties, Block> factory) {
            this.factory = factory;
            return this;
        }

        public Builder properties(BiFunction<Material, BlockBehaviour.Properties, BlockBehaviour.Properties> tuner) {
            this.propertyTuner = tuner;
            return this;
        }

        public ComponentType build() {
            if (factory == null)
                throw new IllegalStateException("component type " + id + " has no block factory");
            return new ComponentType(this);
        }
    }
}
