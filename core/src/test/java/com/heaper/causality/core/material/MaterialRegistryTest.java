package com.heaper.causality.core.material;

import com.heaper.causality.core.material.composition.Composition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Material registry")
class MaterialRegistryTest {

    @BeforeAll
    static void setUp() {
        Materials.init();
    }

    private static List<Material> elements() {
        return MaterialRegistry.all().stream()
                .filter(m -> m.composition() instanceof Composition.Element)
                .toList();
    }

    private static Composition.Element elementOf(Material m) {
        return (Composition.Element) m.composition();
    }

    @Nested
    @DisplayName("Registry integrity")
    class RegistryIntegrity {

        @Test
        @DisplayName("init() complets without trowing - freee validation passes")
        void initSucceeds() {
            assertFalse(MaterialRegistry.all().isEmpty());
            assertTrue(MaterialRegistry.isFrozen());
        }

        @Test
        @DisplayName("freeze() is idempotent")
        void freezeIsIdempotent() {
            assertDoesNotThrow(MaterialRegistry::freeze);
        }

        @Test
        @DisplayName("all 118 elements are registered")
        void allElementsRegistered() {
            assertEquals(118, elements().size());
        }

        @Test
        @DisplayName("find() resolves known ids and returns empty for unknown ones")
        void findBehaviour() {
            assertTrue(MaterialRegistry.find("iron").isPresent());
            assertSame(Elements.IRON, MaterialRegistry.find("iron").orElseThrow());
            assertTrue(MaterialRegistry.find("not_a_real_material").isEmpty(),
                    "unknown ids must return empty, never throw");
        }

        @Test
        @DisplayName("all() is not modifiable by callers")
        void allIsImmutable() {
            assertThrows(UnsupportedOperationException.class,
                    () -> MaterialRegistry.all().clear());
        }
    }

    @Nested
    @DisplayName("Element data")
    class ElementData {

        @Test
        @DisplayName("atomic numbers 1..118 are each present exacly once")
        void atomicNumbersAreCompleteAndUnique() {
            Set<Integer> seen = new HashSet<>();
            for (Material m : elements()) {
                int z = elementOf(m).atomicNumber();
                assertTrue(seen.add(z), "duplicate atomic number " + z + " on " + m.id());
                assertTrue(z >= 1 && z <= 118, "out of range: " + m.id());
            }
            assertEquals(118, seen.size());
        }
    }

    @Test
    @DisplayName("element symbols are unique and well formed")
    void symbolsAreUniqueAndWellFormed() {
        Set<String> seen = new HashSet<>();
        for (Material m : elements()) {
            String s = elementOf(m).symbol();
            assertTrue(seen.add(s), "duplicate symbol " + s + " on " + m.id());
            assertTrue(s.matches("[A-Z][a-z]?"),
                    "malformed symbol '" + s + "' on " + m.id());
        }
    }

    @Test
    @DisplayName("material ids are valid ResourceLocation path")
    void idsAreValidResourceLocationPaths() {
        for (Material m : MaterialRegistry.all())
            assertTrue(m.id().matches("[a-z0-9_.-]+"),
                    "invalid id '" + m.id() + "' - would fail at item registration");
    }

    @Test
    @DisplayName("atomic masses are positive")
    void massesArePositive() {
        for (Material m : elements())
            assertTrue(elementOf(m).atomicMass() > 0,
                    m.id() + " has non-positive atomic mass");
    }

    @Test
    @DisplayName("atomic mass rises with atomic number apart from known inversions")
    void massOrderingHasOnlyKnowInversions() {
        Set<Integer> allowed = Set.of(19, 28, 53, 91, 108);

        List<Material> sorted = new ArrayList<>(elements());
        sorted.sort(Comparator.comparingInt(m -> elementOf(m).atomicNumber()));

        for (int i = 1; i < sorted.size(); i++) {
            Composition.Element prev = elementOf(sorted.get(i - 1));
            Composition.Element cur = elementOf(sorted.get(i));
            if (cur.atomicMass() >= prev.atomicNumber()) continue;
            assertTrue(allowed.contains(cur.atomicNumber()),
                    "unexpected mass inversion at " + sorted.get(i).id()
                    + " (" + cur.atomicMass() + " < " + prev.atomicMass()
                    + ") - check for a transposed digit");
        }
    }

    @Nested
    @DisplayName("Form derivation")
    class FormDerivation {

        @Test
        @DisplayName("transition metals get the full metalworking chain")
        void transitionMetalsGetFullChain() {
            assertTrue(Elements.IRON.hasForm(MaterialForm.INGOT));
            assertTrue(Elements.IRON.hasForm(MaterialForm.PLATE));
            assertTrue(Elements.IRON.hasForm(MaterialForm.ROD));
            assertTrue(Elements.IRON.hasForm(MaterialForm.NUGGET));
            assertTrue(Elements.IRON.hasForm(MaterialForm.DUST));
            assertTrue(Elements.COPPER.hasForm(MaterialForm.INGOT));
        }

        @Test
        @DisplayName("alkali metals get ingots but no plates or rods")
        void alkaliMetalsAreLimited() {
            assertTrue(Elements.SODIUM.hasForm(MaterialForm.INGOT));
            assertFalse(Elements.SODIUM.hasForm(MaterialForm.PLATE),
                    "sodium is too soft and reactive to be a structural material");
            assertFalse(Elements.SODIUM.hasForm(MaterialForm.ROD));
        }

        @Test
        @DisplayName("matealloids get ingot, plate and dust only")
        void metalloidForms() {
            assertEquals(
                    Set.of(MaterialForm.INGOT, MaterialForm.PLATE, MaterialForm.DUST),
                    Elements.SILICON.forms()
            );
        }

        @Test
        @DisplayName("reactive nonmetals get dust only")
        void nonmetalForms() {
            assertTrue(Elements.SULFUR.hasForm(MaterialForm.DUST));
            assertFalse(Elements.SULFUR.hasForm(MaterialForm.INGOT));
        }

        @Test
        @DisplayName("solid halogens derive dusts")
        void solidHalogensDeriveDusts() {
            assertTrue(Elements.IODINE.hasForm(MaterialForm.DUST));
            assertTrue(Elements.ASTATINE.hasForm(MaterialForm.DUST));
            assertFalse(Elements.IODINE.hasForm(MaterialForm.INGOT),
                    "iodine is brittle and crystalline, not castable");
        }

        @Test
        @DisplayName("UNKOWN category elements get no forms")
        void unknownCategoryHasNoForms() {
            assertTrue(Elements.OGANESSON.forms().isEmpty());
            assertTrue(Elements.FLEROVIUM.forms().isEmpty());
        }

        @Test
        @DisplayName("synthetic elements derive no forms regardless of category")
        void syntheticElementsGetNoForms() {
            assertEquals(Occurrence.SYNTHETIC,
                    Elements.CURIUM.get(MaterialProperty.OCCURRENCE).orElseThrow());
            assertTrue(Elements.CURIUM.forms().isEmpty());
        }

        @Test
        @DisplayName("TRACE elements keep their forms, plutonium is reactor fuel")
        void traceElementsKeepForms() {
            assertEquals(Occurrence.TRACE,
                    Elements.PLUTONIUM.get(MaterialProperty.OCCURRENCE).orElseThrow());
            assertTrue(Elements.PLUTONIUM.hasForm(MaterialForm.INGOT));
        }

        @Test
        @DisplayName("forms() is not modifiable by callers")
        void formsAreImmutable() {
            assertThrows(UnsupportedOperationException.class,
                    () -> Elements.IRON.forms().add(MaterialForm.GEAR));
        }
    }

    @Nested
    @DisplayName("Physical state")
    class PhysicalStateRules {

        @Test
        @DisplayName("liquids are FLUID onlu - no ingots for mercury")
        void liquidsAreFluidOnly() {
            assertEquals(Set.of(MaterialForm.FLUID), Elements.MERCURY.forms());
            assertEquals(Set.of(MaterialForm.FLUID), Elements.BROMINE.forms());
        }

        @Test
        @DisplayName("gases are FLUID only")
        void gasesAreFluidOnly() {
            assertEquals(Set.of(MaterialForm.FLUID), Elements.OXYGEN.forms());
            assertEquals(Set.of(MaterialForm.FLUID), Elements.HELIUM.forms());
            assertEquals(Set.of(MaterialForm.FLUID), Elements.CHLORINE.forms());
        }

        @Test
        @DisplayName("no non-solid declared a solid form")
        void noSolidFormsOnNonSolids() {
            for (Material m : MaterialRegistry.all()) {
                PhysicalState state = m.get(MaterialProperty.STATE).orElse(PhysicalState.SOLID);
                if (state != PhysicalState.SOLID)
                    assertTrue(m.forms().stream().allMatch(f -> f == MaterialForm.FLUID),
                            m.id() + " is " + state + " but has " + m.forms());
            }
        }

        @Test
        @DisplayName("exactly 13 elements are non-solid at STP")
        void nonSolidCount() {
            long nonSolid = elements().stream()
                    .filter(m -> m.get(MaterialProperty.STATE)
                            .orElse(PhysicalState.SOLID) != PhysicalState.SOLID)
                    .count();
            assertEquals(13, nonSolid,
                    "11 gases (H He N O F Ne Cl Ar Kr Xe Rn) + 2 liquids (Br Hg)");
        }
    }

    @Nested
    @DisplayName("Categories and properties")
    class CategoriesAndProperties {

        @Test
        @DisplayName("isMetal() agrees with the metal categories")
        void isMetalIsConsistent() {
            assertTrue(ElementCategory.TRANSITION_METAL.isMetal());
            assertTrue(ElementCategory.ACTINIDE.isMetal());
            assertTrue(ElementCategory.ALKALI_METAL.isMetal());
            assertFalse(ElementCategory.METALLOID.isMetal());
            assertFalse(ElementCategory.NOBLE_GAS.isMetal());
            assertFalse(ElementCategory.HALOGEN.isMetal());
            assertFalse(ElementCategory.UNKNOWN.isMetal());
        }

        @Test
        @DisplayName("every element declares a state and an occurrence")
        void classificationIsComplete() {
            for (Material m : elements()) {
                assertTrue(m.has(MaterialProperty.STATE), m.id() + " has no STATE");
                assertTrue(m.has(MaterialProperty.OCCURRENCE), m.id() + " has no OCCURRENCE");
            }
        }

        @Test
        @DisplayName("require() throws a message naming the material")
        void requireFailsLoudly() {
            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    () -> Elements.IRON.require(MaterialProperty.MELTING_POINT));
            assertTrue(ex.getMessage().contains("iron"));
        }
    }
}
