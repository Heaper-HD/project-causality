package com.heaper.causality.core.material;

import com.heaper.causality.core.material.composition.Composition;

import java.util.*;

public final class Material {
    private final String id;
    private final Composition composition;
    private final Set<MaterialForm> forms;
    private final Map<MaterialProperty<?>, Object> properties;
    private final Set<String> excludedComponents;

    private Material(Builder b) {
        this.id = b.id;
        this.composition = Objects.requireNonNull(b.composition, "composition for " + b.id);
        this.forms = Collections.unmodifiableSet(EnumSet.copyOf(b.forms));
        this.properties = Map.copyOf(b.properties);
        this.excludedComponents = Set.copyOf(b.excludedComponents);
    }

    public String id() { return id; }
    public Composition composition() { return composition; }
    public Set<MaterialForm> forms() { return forms; }
    public boolean hasForm(MaterialForm f) { return forms.contains(f); }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(MaterialProperty<T> p) {
        return Optional.ofNullable((T) properties.get(p));
    }

    public <T> T require(MaterialProperty<T> p) {
        return get(p).orElseThrow(() ->
                new IllegalStateException(id + " is missing required property " + p.id()));
    }

    public boolean has(MaterialProperty<?> p) { return properties.containsKey(p); }

    public boolean excludes(String componentTypeId) { return excludedComponents.contains(componentTypeId); }

    @Override public String toString() { return "Material[" + id + "]"; }

    public static Builder builder (String id) { return new Builder(id); }

    public static final class Builder {
        private final String id;
        private Composition composition;
        private Set<MaterialForm> forms = null;
        private final Map<MaterialProperty<?>, Object> properties = new LinkedHashMap<>();
        private final Set<String> excludedComponents = new HashSet<>();

        private Builder(String id) { this.id = id; }

        public Builder element(String symbol, int z, double atomicMass, ElementCategory category) {
            this.composition = new Composition.Element(symbol, z, atomicMass, category);
            return this;
        }

        public Builder compound(Object... pairs) {
            List<Composition.Part> parts = new ArrayList<>();
            for (int i = 0; i < pairs.length; i += 2)
                parts.add(new Composition.Part((Material) pairs[i], (Integer) pairs[i + 1]));
            this.composition = new Composition.Compound(List.copyOf(parts));
            return this;
        }

        public Builder mixture(Object... pairs) {
            Map<Material, Double> f = new LinkedHashMap<>();
            for (int i = 0; i < pairs.length; i += 2)
                f.put((Material) pairs[i], ((Number) pairs[i + 1]).doubleValue());
            this.composition = new Composition.Mixture(Map.copyOf(f));
            return this;
        }

        public Builder forms(MaterialForm... fs) {
            if (forms == null) forms = EnumSet.noneOf(MaterialForm.class);
            forms.addAll(Arrays.asList(fs));
            return this;
        }

        public Builder noForms() {
            this.forms = EnumSet.noneOf(MaterialForm.class);
            return this;
        }

        public <T> Builder property(MaterialProperty<T> p, T value) {
            properties.put(p, value);
            return this;
        }

        public Builder excludeComponent(String componentTypeId) {
            excludedComponents.add(componentTypeId);
            return this;
        }

        Composition composition() { return composition; }

        PhysicalState state() {
            return (PhysicalState) properties.getOrDefault(MaterialProperty.STATE, PhysicalState.SOLID);
        }

        Occurrence occurrence() {
            return (Occurrence) properties.getOrDefault(
                    MaterialProperty.OCCURRENCE, Occurrence.NATURAL);
        }

        public Material build() {
            if (forms == null) forms = MaterialForms.derive(this);
            return MaterialRegistry.register(new Material(this));
        }
    }
}
