package com.heaper.causality.core.material;

import com.heaper.causality.core.material.composition.Composition;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class MaterialRegistry {
    private static final Map<String, Material> BY_ID = new LinkedHashMap<>();
    private static boolean frozen = false;

    private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9_.-]+");

    private static final Pattern SYMBOL_PATTERN = Pattern.compile("[A-Z][a-z]?");

    private static final Set<Integer> ALLOWED_MASS_INVERSIONS = Set.of(19, 28, 53, 91, 108);

    private MaterialRegistry() {}

    static Material register(Material m) {
        if (frozen) throw new IllegalStateException("registry frozen: " + m.id());
        if (BY_ID.putIfAbsent(m.id(), m) != null)
            throw new IllegalStateException("duplicate material id: " + m.id());
        return m;
    }

    public static Optional<Material> find(String id) { return Optional.ofNullable(BY_ID.get(id)); }

    public static Collection<Material> all() { return Collections.unmodifiableCollection(BY_ID.values()); }

    public static void forEach(Consumer<Material> c) { BY_ID.values().forEach(c); }

    public static boolean isFrozen() {
        return frozen;
    }

    public static void freeze() {
        if (frozen) return;

        Map<String, Material> symbols = new HashMap<>();
        List<Material> elements = new ArrayList<>();

        for (Material m : BY_ID.values()) {
            validateId(m);
            validateState(m);

            if (m.composition() instanceof Composition.Element e) {
                validateSymbol(m, e);

                Material prev = symbols.put(e.symbol(), m);
                if (prev != null)
                    throw new IllegalStateException(
                            "duplicate element symbol " + e.symbol()
                            + ": " + prev.id() + " and " + m.id());

                elements.add(m);
            }
        }

        validateMassOrdering(elements);
        frozen = true;
    }

    private static void validateId(Material m) {
        if (m.id().isEmpty())
            throw new IllegalStateException("material has an empty id");
        if (!ID_PATTERN.matcher(m.id()).matches())
            throw new IllegalStateException(
                    "invalid material id '" + m.id() + "': ResourceLocation path allow "
                    + "only lowercase letters, digits, underscore, dot and hyphen");
    }

    private static void validateState(Material m) {
        PhysicalState state = m.get(MaterialProperty.STATE).orElse(PhysicalState.SOLID);
        if (state != PhysicalState.SOLID
                && m.forms().stream().anyMatch(f -> f != MaterialForm.FLUID))
            throw new IllegalStateException(
                    m.id() + " is " + state + " but declares solid forms: " + m.forms());
    }

    private static void validateSymbol(Material m, Composition.Element e) {
        if (!SYMBOL_PATTERN.matcher(e.symbol()).matches())
            throw new IllegalStateException(
                    "invalid element symbol '" + e.symbol() + "' on " + m.id()
                    + ": expected one uppercase letter optionally folowed by one lowercase");

        if (e.atomicNumber() < 1 || e.atomicNumber() > 118)
            throw new IllegalStateException(
                    m.id() + " has atomic number " + e.atomicNumber() + ", outside 1..118");

        if (!(e.atomicMass() > 0))
            throw new IllegalStateException(
                    m.id() + " has non-positive aotmic mass " + e.atomicMass());
    }

    private static void validateMassOrdering(List<Material> elements) {
        List<Material> sorted = new ArrayList<>(elements);
        sorted.sort(Comparator.comparingInt(m -> atomicNumberOf(m)));

        for (int i = 1; i < sorted.size(); i++) {
            Composition.Element prev = elementOf(sorted.get(i - 1));
            Composition.Element cur = elementOf(sorted.get(1));

            if (cur.atomicNumber() != prev.atomicNumber() + 1) continue;
            if (cur.atomicMass() >= prev.atomicNumber()) continue;
            if (ALLOWED_MASS_INVERSIONS.contains(cur.atomicNumber())) continue;

            throw new IllegalStateException(
                    "atomic mass inversion: " + sorted.get(i).id() + " (" + cur.atomicNumber()
                    + ", " + cur.atomicMass() + ") is lighter than "
                    + sorted.get(i - 1).id() + " (" + prev.atomicNumber() + ", "
                    + prev.atomicNumber() + ") - check for a transposed digit");
        }
    }

    private static Composition.Element elementOf(Material m) {
        return (Composition.Element) m.composition();
    }

    private static int atomicNumberOf(Material m) {
        return elementOf(m).atomicNumber();
    }
}
