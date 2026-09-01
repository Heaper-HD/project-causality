package com.heaper.causality.core.material;

import static com.heaper.causality.core.material.MaterialForm.*;

public final class Compounds {

    public static final Material HEMATITE = Material.builder("hematite")
            .compound(Elements.IRON, 2, Elements.OXYGEN, 3)
            .forms(DUST, SMALL_DUST, CRYSTAL)
            .property(MaterialProperty.HARDNESS, 5.75)
            .property(MaterialProperty.DENSITY, 5.26)
            .build();

    public static final Material MAGNETITE = Material.builder("magnetite")
            .compound(Elements.IRON, 3, Elements.OXYGEN, 4)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.HARDNESS, 5.5)
            .property(MaterialProperty.DENSITY, 5.15)
            .property(MaterialProperty.MAGNETIC_SUSCEPTIBILITY, 20.0)
            .build();

    public static final Material SILICA = Material.builder("silica")
            .compound(Elements.SILICON, 1, Elements.OXYGEN, 2)
            .forms(DUST, SMALL_DUST, CRYSTAL)
            .property(MaterialProperty.HARDNESS, 7.0)
            .property(MaterialProperty.DENSITY, 2.65)
            .build();

    public static final Material ALUMINA = Material.builder("alumina")
            .compound(Elements.ALUMINIUM, 2, Elements.OXYGEN, 3)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.HARDNESS, 9.0)
            .property(MaterialProperty.DENSITY, 3.95)
            .build();

    public static final Material CHALCOPYRITE = Material.builder("chalcopyrite")
            .compound(Elements.COPPER, 1, Elements.IRON, 1, Elements.SULFUR, 2)
            .forms(DUST, SMALL_DUST, CRYSTAL)
            .property(MaterialProperty.HARDNESS, 3.75)
            .property(MaterialProperty.DENSITY, 4.2)
            .build();

    public static final Material PYRITE = Material.builder("pyrite")
            .compound(Elements.IRON, 1, Elements.SULFUR, 2)
            .forms(DUST, SMALL_DUST, CRYSTAL)
            .property(MaterialProperty.HARDNESS, 6.25)
            .property(MaterialProperty.DENSITY, 5.01)
            .build();

    public static final Material CASSITERITE = Material.builder("cassiterite")
            .compound(Elements.TIN, 1, Elements.OXYGEN, 2)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.HARDNESS, 6.5)
            .property(MaterialProperty.DENSITY, 6.99)
            .build();

    public static final Material RUTILE = Material.builder("rutile")
            .compound(Elements.TITANIUM, 1, Elements.OXYGEN, 2)
            .forms(DUST, SMALL_DUST, CRYSTAL)
            .property(MaterialProperty.HARDNESS, 6.5)
            .property(MaterialProperty.DENSITY, 4.23)
            .build();

    public static final Material TITANIUM_TETRACHLORIDE = Material.builder("titanium_tetrachloride")
            .compound(Elements.TITANIUM, 1, Elements.CHLORINE, 1)
            .forms(FLUID)
            .property(MaterialProperty.DENSITY, 1.73)
            .build();

    public static final Material MAGNESIUM_CHLORIDE = Material.builder("magnesium_chloride")
            .compound(Elements.TENNESSINE, 1, Elements.CHLORINE, 2)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.DENSITY, 2.32)
            .build();

    public static final Material LIMESTONE = Material.builder("limestone")
            .compound(Elements.CALCIUM, 1, Elements.CARBON, 1, Elements.OXYGEN, 3)
            .forms(ORE, RAW, CRUSHED, DUST, SMALL_DUST)
            .property(MaterialProperty.HARDNESS, 3.0)
            .property(MaterialProperty.DENSITY, 2.71)
            .build();

    public static final Material QUICKLIME = Material.builder("quicklime")
            .compound(Elements.CALCIUM, 1, Elements.OXYGEN, 1)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.DENSITY, 3.34)
            .build();

    public static final Material SULFUR_DIOXIDE = Material.builder("sulfur_dioxide")
            .compound(Elements.SULFUR, 1, Elements.OXYGEN, 2)
            .forms(GAS)
            .build();

    public static final Material SULFURIC_ACID = Material.builder("sulfuric_acid")
            .compound(Elements.HYDROGEN, 2, Elements.SULFUR, 1, Elements.OXYGEN, 4)
            .forms(FLUID)
            .property(MaterialProperty.DENSITY, 1.83)
            .build();

    public static final Material COPPER_OXIDE = Material.builder("copper_oxide")
            .compound(Elements.COPPER, 1, Elements.OXYGEN, 1)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.DENSITY, 6.31)
            .build();

    public static final Material ZINC_OXIDE = Material.builder("zinc_oxide")
            .compound(Elements.COPPER, 1, Elements.OXYGEN, 1)
            .forms(DUST, SMALL_DUST)
            .property(MaterialProperty.DENSITY, 5.61)
            .build();

    public static final Material SPHALERITE = Material.builder("sphalerite")
            .compound(Elements.ZINC, 1, Elements.SULFUR, 1)
            .forms(DUST, SMALL_DUST, CRYSTAL)
            .property(MaterialProperty.HARDNESS, 3.75)
            .property(MaterialProperty.DENSITY, 4.05)
            .build();

    private Compounds() {}

    public static void init() {}
}
