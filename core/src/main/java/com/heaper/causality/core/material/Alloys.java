package com.heaper.causality.core.material;

public final class Alloys {

    public static final Material BRONZE = Material.builder("bronze")
            .mixture(Elements.COPPER, 0.88, Elements.TIN, 0.12)
            .property(MaterialProperty.TENSILE_STRENGTH, 350)
            .property(MaterialProperty.HARDNESS, 3.0)
            .property(MaterialProperty.MELTING_POINT, 1223)
            .property(MaterialProperty.DENSITY, 8.80)
            .build();

    public static final Material BRASS = Material.builder("brass")
            .mixture(Elements.COPPER, 0.65, Elements.ZINC, 0.35)
            .property(MaterialProperty.TENSILE_STRENGTH, 340)
            .property(MaterialProperty.HARDNESS, 3.0)
            .property(MaterialProperty.MELTING_POINT, 1173)
            .property(MaterialProperty.DENSITY, 8.50)
            .build();

    public static final Material PIG_IRON = Material.builder("pig_iron")
            .mixture(Elements.IRON, 0.955, Elements.CARBON, 0.045)
            .property(MaterialProperty.TENSILE_STRENGTH, 180)
            .property(MaterialProperty.HARDNESS, 4.0)
            .property(MaterialProperty.MELTING_POINT, 1450)
            .property(MaterialProperty.DENSITY, 7.20)
            .build();

    public static final Material STEEL = Material.builder("steel")
            .mixture(Elements.IRON, 0.992, Elements.CARBON, 0.008)
            .property(MaterialProperty.TENSILE_STRENGTH, 550)
            .property(MaterialProperty.HARDNESS, 4.5)
            .property(MaterialProperty.MELTING_POINT, 1700)
            .property(MaterialProperty.DENSITY, 7.85)
            .build();

    public static final Material STAINLESS_STEEL = Material.builder("stainless_steel")
            .mixture(Elements.IRON, 0.70, Elements.CHROMIUM, 0.18,
                    Elements.NICKEL, 0.10, Elements.MANGANESE, 0.02)
            .property(MaterialProperty.TENSILE_STRENGTH, 620)
            .property(MaterialProperty.HARDNESS, 5.5)
            .property(MaterialProperty.MELTING_POINT, 1723)
            .property(MaterialProperty.DENSITY, 8.00)
            .build();

    public static final Material CUPRONICKEL = Material.builder("cupronickel")
            .mixture(Elements.COPPER, 0.75, Elements.NICKEL, 0.25)
            .property(MaterialProperty.TENSILE_STRENGTH, 380)
            .property(MaterialProperty.HARDNESS, 3.5)
            .property(MaterialProperty.MELTING_POINT, 1443)
            .property(MaterialProperty.DENSITY, 8.90)
            .build();

    public static final Material NICHROME = Material.builder("nichrome")
            .mixture(Elements.NICKEL, 0.80, Elements.CHROMIUM, 0.20)
            .property(MaterialProperty.TENSILE_STRENGTH, 670)
            .property(MaterialProperty.HARDNESS, 5.5)
            .property(MaterialProperty.MELTING_POINT, 1673)
            .property(MaterialProperty.DENSITY, 8.40)
            .build();

    public static final Material TITANIUM_ALLOY = Material.builder("titanium_alloy")
            .mixture(Elements.TITANIUM, 0.90, Elements.ALUMINIUM, 0.06,
                    Elements.VANADIUM, 0.04)
            .property(MaterialProperty.TENSILE_STRENGTH, 950)
            .property(MaterialProperty.HARDNESS, 6.5)
            .property(MaterialProperty.MELTING_POINT, 1933)
            .property(MaterialProperty.DENSITY, 4.43)
            .build();

    private Alloys() {}

    public static void init() {}
}
