package com.heaper.causality.core.material;

import static com.heaper.causality.core.material.ElementCategory.*;
import static com.heaper.causality.core.material.Occurrence.*;
import static com.heaper.causality.core.material.PhysicalState.*;

public final class Elements {
    public static final Material HYDROGEN = Material.builder("hydrogen")
            .element("H", 1, 1.008, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material HELIUM = Material.builder("helium")
            .element("He", 2, 4.0026, NOBLE_GAS)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material LITHIUM = Material.builder("lithium")
            .element("Li", 3, 6.94, ALKALI_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BERYLLIUM = Material.builder("beryllium")
            .element("Be", 4, 9.0122, ALKALINE_EARTH_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BORON = Material.builder("boron")
            .element("B", 5, 10.81, METALLOID)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CARBON = Material.builder("carbon")
            .element("C", 6, 12.011, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NITROGEN = Material.builder("nitrogen")
            .element("N", 7, 14.007, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material OXYGEN = Material.builder("oxygen")
            .element("O", 8, 15.999, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material FLUORINE = Material.builder("fluorine")
            .element("F", 9, 18.998, HALOGEN)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material NEON = Material.builder("neon")
            .element("Ne", 10, 20.180, NOBLE_GAS)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material SODIUM = Material.builder("sodium")
            .element("Na", 11, 22.990, ALKALI_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MAGNESIUM = Material.builder("magnesium")
            .element("Mg", 12, 24.305, ALKALINE_EARTH_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ALUMINIUM = Material.builder("aluminium")
            .element("Al", 13, 26.982, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SILICON = Material.builder("silicon")
            .element("Si", 14, 28.085, METALLOID)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PHOSPHORUS = Material.builder("phosphorus")
            .element("P", 15, 30.974, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SULFUR = Material.builder("sulfur")
            .element("S", 16, 32.06, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CHLORINE = Material.builder("chlorine")
            .element("Cl", 17, 35.45, HALOGEN)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material ARGON = Material.builder("argon")
            .element("Ar", 18, 39.95, NOBLE_GAS)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material POTASSIUM = Material.builder("potassium")
            .element("K", 19, 39.098, ALKALI_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CALCIUM = Material.builder("calcium")
            .element("Ca", 20, 40.078, ALKALINE_EARTH_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SCANDIUM = Material.builder("scandium")
            .element("Sc", 21, 44.956, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TITANIUM = Material.builder("titanium")
            .element("Ti", 22, 47.867, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material VANADIUM = Material.builder("vanadium")
            .element("V", 23, 50.942, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CHROMIUM = Material.builder("chromium")
            .element("Cr", 24, 51.996, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MANGANESE = Material.builder("manganese")
            .element("Mn", 25, 54.938, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material IRON = Material.builder("iron")
            .element("Fe", 26, 55.845, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material COBALT = Material.builder("cobalt")
            .element("Co", 27, 58.933, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NICKEL = Material.builder("nickel")
            .element("Ni", 28, 58.693, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material COPPER = Material.builder("copper")
            .element("Cu", 29, 63.546, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ZINC = Material.builder("zinc")
            .element("Zn", 30, 65.38, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material GALLIUM = Material.builder("gallium")
            .element("Ga", 31, 69.723, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material GERMANIUM = Material.builder("germanium")
            .element("Ge", 32, 72.630, METALLOID)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ARSENIC = Material.builder("arsenic")
            .element("As", 33, 74.922, METALLOID)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SELENIUM = Material.builder("selenium")
            .element("Se", 34, 78.971, REACTIVE_NONMETAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BROMINE = Material.builder("bromine")
            .element("Br", 35, 79.904, HALOGEN)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, LIQUID)
            .build();

    public static final Material KRYPTON = Material.builder("krypton")
            .element("Kr", 36, 83.798, NOBLE_GAS)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material RUBIDIUM = Material.builder("rubidium")
            .element("Rb", 37, 85.468, ALKALI_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material STRONTIUM = Material.builder("strontium")
            .element("Sr", 38, 87.62, ALKALINE_EARTH_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material YTTRIUM = Material.builder("yttrium")
            .element("Y", 39, 88.906, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ZIRCONIUM = Material.builder("zirconium")
            .element("Zr", 40, 91.224, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NIOBIUM = Material.builder("niobium")
            .element("Nb", 41, 92.906, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MOLYBDENUM = Material.builder("molybdenum")
            .element("Mo", 42, 95.95, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TECHNETIUM = Material.builder("technetium")
            .element("Tc", 43, 98.0, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material RUTHENIUM = Material.builder("ruthenium")
            .element("Ru", 44, 101.07, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material RHODIUM = Material.builder("rhodium")
            .element("Rh", 45, 102.91, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PALLADIUM = Material.builder("palladium")
            .element("Pd", 46, 106.42, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SILVER = Material.builder("silver")
            .element("Ag", 47, 107.87, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CADMIUM = Material.builder("cadmium")
            .element("Cd", 48, 112.41, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material INDIUM = Material.builder("indium")
            .element("In", 49, 114.82, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TIN = Material.builder("tin")
            .element("Sn", 50, 118.71, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ANTIMONY = Material.builder("antimony")
            .element("Sb", 51, 121.76, METALLOID)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TELLURIUM = Material.builder("tellurium")
            .element("Te", 52, 127.60, METALLOID)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material IODINE = Material.builder("iodine")
            .element("I", 53, 126.90, HALOGEN)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material XENON = Material.builder("xenon")
            .element("Xe", 54, 131.29, NOBLE_GAS)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material CAESIUM = Material.builder("caesium")
            .element("Cs", 55, 132.91, ALKALI_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BARIUM = Material.builder("barium")
            .element("Ba", 56, 137.33, ALKALINE_EARTH_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material LANTHANUM = Material.builder("lanthanum")
            .element("La", 57, 138.91, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CERIUM = Material.builder("cerium")
            .element("Ce", 58, 140.12, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PRASEODYMIUM = Material.builder("praseodymium")
            .element("Pr", 59, 140.91, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NEODYMIUM = Material.builder("neodymium")
            .element("Nd", 60, 144.24, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PROMETHIUM = Material.builder("promethium")
            .element("Pm", 61, 145.0, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SAMARIUM = Material.builder("samarium")
            .element("Sm", 62, 150.36, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material EUROPIUM = Material.builder("europium")
            .element("Eu", 63, 151.96, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material GADOLINIUM = Material.builder("gadolinium")
            .element("Gd", 64, 157.25, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TERBIUM = Material.builder("terbium")
            .element("Tb", 65, 158.93, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material DYSPROSIUM = Material.builder("dysprosium")
            .element("Dy", 66, 162.50, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material HOLMIUM = Material.builder("holmium")
            .element("Ho", 67, 164.93, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ERBIUM = Material.builder("erbium")
            .element("Er", 68, 167.26, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material THULIUM = Material.builder("thulium")
            .element("Tm", 69, 168.93, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material YTTERBIUM = Material.builder("ytterbium")
            .element("Yb", 70, 173.05, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material LUTETIUM = Material.builder("lutetium")
            .element("Lu", 71, 174.97, LANTHANIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material HAFNIUM = Material.builder("hafnium")
            .element("Hf", 72, 178.49, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TANTALUM = Material.builder("tantalum")
            .element("Ta", 73, 180.95, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TUNGSTEN = Material.builder("tungsten")
            .element("W", 74, 183.84, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material RHENIUM = Material.builder("rhenium")
            .element("Re", 75, 186.21, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material OSMIUM = Material.builder("osmium")
            .element("Os", 76, 190.23, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material IRIDIUM = Material.builder("iridium")
            .element("Ir", 77, 192.22, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PLATINUM = Material.builder("platinum")
            .element("Pt", 78, 195.08, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material GOLD = Material.builder("gold")
            .element("Au", 79, 196.97, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MERCURY = Material.builder("mercury")
            .element("Hg", 80, 200.59, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, LIQUID)
            .build();

    public static final Material THALLIUM = Material.builder("thallium")
            .element("Tl", 81, 204.38, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material LEAD = Material.builder("lead")
            .element("Pb", 82, 207.2, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BISMUTH = Material.builder("bismuth")
            .element("Bi", 83, 208.98, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material POLONIUM = Material.builder("polonium")
            .element("Po", 84, 209.0, POST_TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ASTATINE = Material.builder("astatine")
            .element("At", 85, 210.0, HALOGEN)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material RADON = Material.builder("radon")
            .element("Rn", 86, 222.0, NOBLE_GAS)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, GAS)
            .build();

    public static final Material FRANCIUM = Material.builder("francium")
            .element("Fr", 87, 223.0, ALKALI_METAL)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material RADIUM = Material.builder("radium")
            .element("Ra", 88, 226.0, ALKALINE_EARTH_METAL)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ACTINIUM = Material.builder("actinium")
            .element("Ac", 89, 227.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material THORIUM = Material.builder("thorium")
            .element("Th", 90, 232.04, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PROTACTINIUM = Material.builder("protactinium")
            .element("Pa", 91, 231.04, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material URANIUM = Material.builder("uranium")
            .element("U", 92, 238.03, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, NATURAL)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NEPTUNIUM = Material.builder("neptunium")
            .element("Np", 93, 237.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material PLUTONIUM = Material.builder("plutonium")
            .element("Pu", 94, 244.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, TRACE)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material AMERICIUM = Material.builder("americium")
            .element("Am", 95, 243.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CURIUM = Material.builder("curium")
            .element("Cm", 96, 247.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BERKELIUM = Material.builder("berkelium")
            .element("Bk", 97, 247.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material CALIFORNIUM = Material.builder("californium")
            .element("Cf", 98, 251.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material EINSTEINIUM = Material.builder("einsteinium")
            .element("Es", 99, 252.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material FERMIUM = Material.builder("fermium")
            .element("Fm", 100, 257.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MENDELEVIUM = Material.builder("mendelevium")
            .element("Md", 101, 258.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NOBELIUM = Material.builder("nobelium")
            .element("No", 102, 259.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material LAWRENCIUM = Material.builder("lawrencium")
            .element("Lr", 103, 266.0, ACTINIDE)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material RUTHERFORDIUM = Material.builder("rutherfordium")
            .element("Rf", 104, 267.0, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material DUBNIUM = Material.builder("dubnium")
            .element("Db", 105, 268.0, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material SEABORGIUM = Material.builder("seaborgium")
            .element("Sg", 106, 269.0, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material BOHRIUM = Material.builder("bohrium")
            .element("Bh", 107, 270.0, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material HASSIUM = Material.builder("hassium")
            .element("Hs", 108, 269.0, TRANSITION_METAL)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MEITNERIUM = Material.builder("meitnerium")
            .element("Mt", 109, 278.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material DARMSTADTIUM = Material.builder("darmstadtium")
            .element("Ds", 110, 281.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material ROENTGENIUM = Material.builder("roentgenium")
            .element("Rg", 111, 282.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material COPERNICIUM = Material.builder("copernicium")
            .element("Cn", 112, 285.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material NIHONIUM = Material.builder("nihonium")
            .element("Nh", 113, 286.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material FLEROVIUM = Material.builder("flerovium")
            .element("Fl", 114, 289.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material MOSCOVIUM = Material.builder("moscovium")
            .element("Mc", 115, 290.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material LIVERMORIUM = Material.builder("livermorium")
            .element("Lv", 116, 293.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material TENNESSINE = Material.builder("tennessine")
            .element("Ts", 117, 294.0, HALOGEN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static final Material OGANESSON = Material.builder("oganesson")
            .element("Og", 118, 294.0, UNKNOWN)
            .property(MaterialProperty.OCCURRENCE, SYNTHETIC)
            .property(MaterialProperty.STATE, SOLID)
            .build();

    public static void init() {}
}
