package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.*;
import com.heaper.causality.core.material.composition.Composition;
import com.mojang.datafixers.kinds.App;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.heaper.causality.client.appearance.TextureSet.*;

public final class AppearanceRegistry {
    private static final Map<Material, Appearance> APPEARANCES = new HashMap<>();

    private static final Appearance FALLBACK = new Appearance(ROUGH, 0xB0B0B0);

    private AppearanceRegistry() {}

    public static void set(Material material, Appearance appearance) {
        APPEARANCES.put(material, appearance);
    }

    public static void set(Material material, TextureSet set, int color) {
        APPEARANCES.put(material, new Appearance(set, color));
    }

    public static void set(Material material, TextureSet set,  int color, Decoration... decorations) {
        APPEARANCES.put(material, new Appearance(set, color, Map.of(), null, null, List.of(decorations)));
    }

    public static Appearance get(Material material) {
        Appearance a = APPEARANCES.get(material);
        if (a != null) return a;

        Appearance derived = new Appearance(defaultSet(material), defaultColor(material));
        APPEARANCES.put(material, derived);
        return derived;
    }

    public static int colorOf(Material material) {
        return get(material).color();
    }

    private static TextureSet defaultSet(Material material) {
        return switch (material.composition()) {
            case Composition.Element e -> e.category().isMetal() ? METALLIC : ROUGH;
            case Composition.Mixture m -> metallic(m.fractions()) ? METALLIC : ROUGH;
            case Composition.Compound c -> ROUGH;
        };
    }

    private static boolean metallic(Map<Material, Double> fractions) {
        double metal = fractions.entrySet().stream()
                .filter(e -> e.getKey().composition() instanceof Composition.Element el
                && el.category().isMetal())
                .mapToDouble(Map.Entry::getValue)
                .sum();
        return metal >= 0.5;
    }

    private static int defaultColor(Material material) {
        return switch (material.composition()) {
            case Composition.Element e -> categoryColor(e.category());
            case Composition.Mixture m -> blend(m.fractions());
            case Composition.Compound c -> blend(compoundFractions(c));
        };
    }

    public static int categoryColor(ElementCategory category) {
        return switch (category) {
            case ALKALI_METAL -> 0xD8C8A0;
            case ALKALINE_EARTH_METAL -> 0xD0D0B8;
            case TRANSITION_METAL -> 0xC0C0C8;
            case POST_TRANSITION_METAL -> 0xA8B0B8;
            case LANTHANIDE -> 0xB8A8C8;
            case ACTINIDE -> 0xA0B890;
            case METALLOID -> 0x9098A0;
            case REACTIVE_NONMETAL -> 0xC8C0A0;
            case HALOGEN -> 0xB8C8A8;
            case NOBLE_GAS -> 0xB0C8D8;
            case UNKNOWN -> 0x909090;
        };
    }

    private static Map<Material, Double> compoundFractions(Composition.Compound c) {
        double total = c.parts().stream().mapToInt(Composition.Part::count).sum();
        Map<Material, Double> out = new LinkedHashMap<>();
        for (Composition.Part p : c.parts())
            out.merge(p.material(), p.count() / total, Double::sum);
        return out;
    }

    private static int blend(Map<Material, Double> fractions) {
        if (fractions.isEmpty()) return FALLBACK.color();

        double r = 0, g = 0, b = 0, total = 0;

        for (Map.Entry<Material, Double> entry : fractions.entrySet()) {
            int c = colorOf(entry.getKey());
            double weight = entry.getValue() * (0.25 + saturation(c));

            r += ((c >> 16) & 0xFF) * weight;
            g += ((c >> 8) & 0xFF) * weight;
            b += (c & 0xFF) * weight;
            total += weight;
        }

        if (total <= 0) return FALLBACK.color();

        return (clamp(r / total) << 16) | (clamp(g / total) << 8) | clamp(b / total);
    }

    private static double saturation(int rbg) {
        int r = (rbg >> 16) & 0xFF, g = (rbg >> 8) & 0xFF, b = rbg & 0xFF;
        int max = Math.max(r, Math.max(g, b));
        int min = Math.min(r, Math.min(g, b));
        return max == 0 ? 0.0 : (max - min) / (double) max;
    }

    private static int clamp(double v) {
        return Math.clamp(Math.round(v), 0, 255);
    }

    public static void bootstrap() {
        // Period 1-2
        set(Elements.HYDROGEN, ROUGH, 0xB4D2E6);
        set(Elements.HELIUM, ROUGH, 0xF0E6A0);
        set(Elements.LITHIUM, METALLIC, 0xD8D8E0);
        set(Elements.BERYLLIUM, METALLIC, 0xB4C8B4);
        set(Elements.BORON, CRYSTALLINE, 0x6E5A50);
        set(Elements.CARBON, ROUGH, 0x505050);
        set(Elements.NITROGEN, ROUGH, 0xA0C8E6);
        set(Elements.OXYGEN, ROUGH, 0x9FD4E8);
        set(Elements.FLUORINE, ROUGH, 0xE0F0A0);
        set(Elements.NEON, ROUGH, 0xFF8C50);

        // Period 3
        set(Elements.SODIUM, METALLIC, 0xE6E6DC);
        set(Elements.MAGNESIUM, METALLIC, 0xC8DCC8);
        set(Elements.ALUMINIUM, METALLIC, 0x80C8F0);
        set(Elements.SILICON, CRYSTALLINE, 0x3C3C50);
        set(Elements.PHOSPHORUS, ROUGH, 0xFFFF00);
        set(Elements.SULFUR, CRYSTALLINE, 0xC8C800);
        set(Elements.CHLORINE, ROUGH, 0xC8E06E);
        set(Elements.ARGON, ROUGH, 0xC8A0E6);

        // Period 4
        set(Elements.POTASSIUM, METALLIC, 0xE6DCC8);
        set(Elements.CALCIUM, METALLIC, 0xE6E6D2);
        set(Elements.SCANDIUM, METALLIC, 0xD2D2DC);
        set(Elements.TITANIUM, METALLIC, 0xDCA0F0);
        set(Elements.VANADIUM, METALLIC, 0xB4B4C8);
        set(Elements.CHROMIUM, SHINY, 0xFFE6E6);
        set(Elements.MANGANESE, METALLIC, 0xB49696);
        set(Elements.IRON, METALLIC, 0xC8C8C8);
        set(Elements.COBALT, METALLIC, 0x5050C8);
        set(Elements.NICKEL, METALLIC, 0xC8C8FA);
        set(Elements.COPPER, METALLIC, 0xE07030);
        set(Elements.ZINC, METALLIC, 0xC8D8D8);
        set(Elements.GALLIUM, METALLIC, 0xC8C8D2);
        set(Elements.GERMANIUM, CRYSTALLINE, 0x8C8C96);
        set(Elements.ARSENIC, CRYSTALLINE, 0x9B9B87);
        set(Elements.SELENIUM, METALLIC, 0x8C6E50);
        set(Elements.BROMINE, METALLIC, 0xA02800);
        set(Elements.KRYPTON, METALLIC, 0xA0E6E6);

        // Period 5
        set(Elements.RUBIDIUM,   METALLIC, 0xE6D2C8);
        set(Elements.STRONTIUM,  METALLIC, 0xE6E6C8);
        set(Elements.YTTRIUM,    METALLIC, 0xD2E6D2);
        set(Elements.ZIRCONIUM,  METALLIC, 0xC8D2D2);
        set(Elements.NIOBIUM,    METALLIC, 0xA5A5B4);
        set(Elements.MOLYBDENUM, METALLIC, 0x9BA5AF);
        set(Elements.TECHNETIUM, METALLIC, 0xA0A0A5);
        set(Elements.RUTHENIUM,  SHINY,    0xAAAAB4);
        set(Elements.RHODIUM,    SHINY,    0xC8C8D2);
        set(Elements.PALLADIUM,  SHINY,    0xC8C8C8);
        set(Elements.SILVER,     SHINY,    0xDCDCF0);
        set(Elements.CADMIUM,    METALLIC, 0xE6D28C);
        set(Elements.INDIUM,     METALLIC, 0xC8C8DC);
        set(Elements.TIN,        METALLIC, 0xDCDCDC);
        set(Elements.ANTIMONY,   CRYSTALLINE,    0xB4B4C8);
        set(Elements.TELLURIUM,  CRYSTALLINE,    0xC8B48C);
        set(Elements.IODINE,     CRYSTALLINE,    0x50327D);
        set(Elements.XENON,      ROUGH,    0xB4C8E6);

        // Period 6
        set(Elements.CAESIUM,    METALLIC, 0xE6C878);
        set(Elements.BARIUM,     METALLIC, 0xD2E6C8);

        // Lanthenides
        set(Elements.LANTHANUM,    METALLIC, 0xD2D2DC);
        set(Elements.CERIUM,       METALLIC, 0xD6D6C8);
        set(Elements.PRASEODYMIUM, METALLIC, 0xC8D6C0);
        set(Elements.NEODYMIUM,    METALLIC, 0xC0D2D6);
        set(Elements.PROMETHIUM,   METALLIC, 0xC8D2E0);
        set(Elements.SAMARIUM,     METALLIC, 0xD2C8DC);
        set(Elements.EUROPIUM,     METALLIC, 0xDCC8D6);
        set(Elements.GADOLINIUM,   METALLIC, 0xD6D2E6);
        set(Elements.TERBIUM,      METALLIC, 0xC8DCD2);
        set(Elements.DYSPROSIUM,   METALLIC, 0xD2E0DC);
        set(Elements.HOLMIUM,      METALLIC, 0xDCE0C8);
        set(Elements.ERBIUM,       METALLIC, 0xE0D2C8);
        set(Elements.THULIUM,      METALLIC, 0xC8C8E0);
        set(Elements.YTTERBIUM,    METALLIC, 0xE0DCD2);
        set(Elements.LUTETIUM,     METALLIC, 0xD2DCE0);

        set(Elements.HAFNIUM,    METALLIC, 0xC8C8D2);
        set(Elements.TANTALUM,   METALLIC, 0xB4B4C8);
        set(Elements.TUNGSTEN,   METALLIC, 0x323246);
        set(Elements.RHENIUM,    METALLIC, 0xB4B4BE);
        set(Elements.OSMIUM,     SHINY,    0x7D96C8);
        set(Elements.IRIDIUM,    SHINY,    0xE6E6F0);
        set(Elements.PLATINUM,   SHINY,    0xFFFFC8);
        set(Elements.GOLD,       SHINY,    0xFFE650);
        set(Elements.MERCURY,    SHINY,    0xC8C8D2);
        set(Elements.THALLIUM,   METALLIC, 0xA5A5A0);
        set(Elements.LEAD,       METALLIC, 0x8C6E8C);
        set(Elements.BISMUTH,    SHINY,    0xC8A0C8);
        set(Elements.POLONIUM,   METALLIC, 0xA5A0A0);
        set(Elements.ASTATINE,   ROUGH,    0x785A50);
        set(Elements.RADON,      ROUGH,    0xC8B4E6);

        // Period 7
        set(Elements.FRANCIUM,   METALLIC, 0xC8B48C);
        set(Elements.RADIUM,     METALLIC, 0xE6F0DC);

        // Actinides
        set(Elements.ACTINIUM,     METALLIC, 0x96C8B4);
        set(Elements.THORIUM,      METALLIC, 0x50A050);
        set(Elements.PROTACTINIUM, METALLIC, 0xA0C8A0);
        set(Elements.URANIUM,      METALLIC, 0x32F032);
        set(Elements.NEPTUNIUM,    METALLIC, 0x5A96AA);
        set(Elements.PLUTONIUM,    METALLIC, 0x8296AA);
        set(Elements.AMERICIUM,    METALLIC, 0xAA96C8);
        set(Elements.CURIUM,       METALLIC, 0x9B8CBE);
        set(Elements.BERKELIUM,    METALLIC, 0x8C82B4);
        set(Elements.CALIFORNIUM,  METALLIC, 0xA08CAA);
        set(Elements.EINSTEINIUM,  METALLIC, 0xB48CA0);
        set(Elements.FERMIUM,      METALLIC, 0xA08296);
        set(Elements.MENDELEVIUM,  METALLIC, 0x96788C);
        set(Elements.NOBELIUM,     METALLIC, 0x8C6E82);
        set(Elements.LAWRENCIUM,   METALLIC, 0x826478);

        // Superheavies
        set(Elements.RUTHERFORDIUM, METALLIC, 0x8C8C96);
        set(Elements.DUBNIUM,       METALLIC, 0x87879B);
        set(Elements.SEABORGIUM,    METALLIC, 0x8282A0);
        set(Elements.BOHRIUM,       METALLIC, 0x7D7DA5);
        set(Elements.HASSIUM,       METALLIC, 0x7878AA);
        set(Elements.MEITNERIUM,    METALLIC, 0x7373AF);
        set(Elements.DARMSTADTIUM,  METALLIC, 0x6E6EB4);
        set(Elements.ROENTGENIUM,   METALLIC, 0x6969B9);
        set(Elements.COPERNICIUM,   METALLIC, 0x6464BE);
        set(Elements.NIHONIUM,      METALLIC, 0x6E64BE);
        set(Elements.FLEROVIUM,     METALLIC, 0x7864BE);
        set(Elements.MOSCOVIUM,     METALLIC, 0x8264BE);
        set(Elements.LIVERMORIUM,   METALLIC, 0x8C64BE);
        set(Elements.TENNESSINE,    ROUGH,    0x9664BE);
        set(Elements.OGANESSON,     ROUGH,    0xA064BE);

        // Compounds
        set(Compounds.HEMATITE, new Appearance(ROUGH, 0x7A2E28).withHabit(Habit.BOTRYOIDAL));
        set(Compounds.MAGNETITE, new Appearance(ROUGH, 0x33333A).withHabit(Habit.OCTAHEDRAL));
        set(Compounds.SILICA, new Appearance(CRYSTALLINE, 0xD8D2C4).withHabit(Habit.PRISMATIC));
        set(Compounds.ALUMINA, new Appearance(ROUGH, 0xE0DAD0).withHabit(Habit.MASSIVE));
        set(Compounds.CHALCOPYRITE, new Appearance(SHINY, 0xC9A227).withHabit(Habit.TABULAR));
        set(Compounds.PYRITE, new Appearance(CRYSTALLINE, 0xD4C24A).withHabit(Habit.CUBIC));
        set(Compounds.CASSITERITE, new Appearance(ROUGH, 0x4A3A2E).withHabit(Habit.PRISMATIC));
        set(Compounds.RUTILE, new Appearance(ROUGH, 0x8C3A2A).withHabit(Habit.ACICULAR));

        // Minerals
        set(Minerals.BANDED_IRON, new Appearance(ROUGH, 0x8A4A38)
                .with(Inclusion.of("banding", Compounds.HEMATITE, "chunk", "grit"))
                .decorated(Decoration.RUST, Decoration.DUSTY));

        set(Minerals.BROW_IRON, new Appearance(ROUGH, 0x7A5A3A)
                .with(Inclusion.of("specks", Compounds.HEMATITE, "chunk", "grit"))
                .decorated(Decoration.RUST, Decoration.EFFLORESCENT));

        set(Minerals.MAGNETITE_ORE, new Appearance(ROUGH, 0x4A4A52)
                .with(Inclusion.of("studs", Compounds.MAGNETITE, "chunk", "grit"))
                .decorated(Decoration.DUSTY));

        set(Minerals.COPPER_ORE, new Appearance(ROUGH, 0x6E6A4A)
                .with(Inclusion.of("flecks", Compounds.CHALCOPYRITE, "chunk", "grit"))
                .decorated(Decoration.WET));

        set(Minerals.TIN_ORE, new Appearance(ROUGH, 0x8A8270)
                .with(Inclusion.of("veins", Compounds.CASSITERITE, "chunk", "grit"))
                .decorated(Decoration.DUSTY));

        set(Minerals.TITANIUM_SAND, new Appearance(ROUGH, 0xA89878)
                .with(Inclusion.of("specks", Compounds.RUTILE, "chunk", "grit"))
                .decorated(Decoration.DUSTY));

        set(Alloys.BRONZE, new Appearance(CAST, 0xC08048).decorated(Decoration.PATINA));
        set(Alloys.BRASS, new Appearance(CAST, 0xD4B85A).decorated(Decoration.TARNISH));
        set(Alloys.PIG_IRON, new Appearance(CAST, 0x6A5A50).decorated(Decoration.SOOTED));
        set(Alloys.STEEL, new Appearance(METALLIC, 0x8A8A92).decorated(Decoration.MILL_SCALE));
        set(Alloys.STAINLESS_STEEL, new Appearance(SHINY, 0xC4C8CC));
        set(Alloys.CUPRONICKEL, new Appearance(METALLIC, 0xC8A898));
        set(Alloys.NICHROME, new Appearance(METALLIC, 0x9A9A88));
        set(Alloys.TITANIUM_ALLOY, new Appearance(METALLIC, 0xB8A8C0));
    }
}
