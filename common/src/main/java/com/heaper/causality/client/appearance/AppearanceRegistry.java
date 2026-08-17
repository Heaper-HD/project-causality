package com.heaper.causality.client.appearance;

import com.heaper.causality.core.material.Elements;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.composition.Composition;

import java.util.HashMap;
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

    public static Appearance get(Material material) {
        Appearance a = APPEARANCES.get(material);
        if (a != null) return a;
        return new Appearance(defaultSet(material), defaultColor(material));
    }

    public static int colorOf(Material material) {
        return get(material).color();
    }

    private static TextureSet defaultSet(Material material) {
        if (!(material.composition() instanceof Composition.Element e)) return ROUGH;
        return e.category().isMetal() ? METALLIC : ROUGH;
    }

    private static int defaultColor(Material material) {
        if (!(material.composition() instanceof Composition.Element e)) return FALLBACK.color();
        return switch (e.category()) {
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

    public static void bootstrap() {
        // Period 1-2
        set(Elements.HYDROGEN, ROUGH, 0xB4D2E6);
        set(Elements.HELIUM, ROUGH, 0xF0E6A0);
        set(Elements.LITHIUM, METALLIC, 0xD8D8E0);
        set(Elements.BERYLLIUM, METALLIC, 0xB4C8B4);
        set(Elements.BORON, ROUGH, 0x6E5A50);
        set(Elements.CARBON, ROUGH, 0x505050);
        set(Elements.NITROGEN, ROUGH, 0xA0C8E6);
        set(Elements.OXYGEN, ROUGH, 0x9FD4E8);
        set(Elements.FLUORINE, ROUGH, 0xE0F0A0);
        set(Elements.NEON, ROUGH, 0xFF8C50);

        // Period 3
        set(Elements.SODIUM, METALLIC, 0xE6E6DC);
        set(Elements.MAGNESIUM, METALLIC, 0xC8DCC8);
        set(Elements.ALUMINIUM, METALLIC, 0x80C8F0);
        set(Elements.SILICON, ROUGH, 0x3C3C50);
        set(Elements.PHOSPHORUS, ROUGH, 0xFFFF00);
        set(Elements.SULFUR, ROUGH, 0xC8C800);
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
        set(Elements.GERMANIUM, METALLIC, 0x8C8C96);
        set(Elements.ARSENIC, METALLIC, 0x9B9B87);
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
        set(Elements.ANTIMONY,   ROUGH,    0xB4B4C8);
        set(Elements.TELLURIUM,  ROUGH,    0xC8B48C);
        set(Elements.IODINE,     ROUGH,    0x50327D);
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
    }
}
