package com.heaper.causality.core.material;

import static com.heaper.causality.core.material.MaterialForm.*;

public class Minerals {
    public static final Material BANDED_IRON = Material.builder("banded_iron")
            .mixture(Compounds.HEMATITE, 0.58,
                    Compounds.SILICA, 0.32,
                    Compounds.ALUMINA, 0.04)
            .forms(ORE, RAW, CRUSHED, PURIFIED, DUST)
            .property(MaterialProperty.HARDNESS, 5.0)
            .property(MaterialProperty.DENSITY, 4.10)
            .build();

    public static final Material BROW_IRON = Material.builder("brown_iron")
            .mixture(Compounds.HEMATITE, 0.42,
                    Compounds.ALUMINA, 0.18,
                    Compounds.SILICA, 0.22)
            .forms(ORE, RAW, CRUSHED, PURIFIED, DUST)
            .property(MaterialProperty.HARDNESS, 4.0)
            .property(MaterialProperty.DENSITY, 3.60)
            .build();

    public static final Material MAGNETITE_ORE = Material.builder("magnetite_ore")
            .mixture(Compounds.MAGNETITE, 0.50,
                    Compounds.SILICA, 0.30,
                    Compounds.PYRITE, 0.05)
            .forms(ORE, RAW, CRUSHED, PURIFIED, DUST)
            .property(MaterialProperty.HARDNESS, 5.5)
            .property(MaterialProperty.DENSITY, 4.30)
            .build();

    public static final Material COPPER_ORE = Material.builder("copper_ore")
            .mixture(Compounds.CHALCOPYRITE, 0.45,
                    Compounds.PYRITE, 0.20,
                    Compounds.SILICA, 0.28)
            .forms(ORE, RAW, CRUSHED, PURIFIED, DUST)
            .property(MaterialProperty.HARDNESS, 4.0)
            .property(MaterialProperty.DENSITY, 4.00)
            .build();

    public static final Material TIN_ORE = Material.builder("tin_ore")
            .mixture(Compounds.CASSITERITE, 0.38,
                    Compounds.SILICA, 0.45,
                    Compounds.PYRITE, 0.03)
            .forms(ORE, RAW, CRUSHED, PURIFIED, DUST)
            .property(MaterialProperty.HARDNESS, 6.0)
            .property(MaterialProperty.DENSITY, 4.50)
            .build();

    public static final Material TITANIUM_SAND = Material.builder("titanium_sand")
            .mixture(Compounds.RUTILE, 0.30,
                    Compounds.SILICA, 0.50,
                    Compounds.MAGNETITE, 0.12)
            .forms(ORE, RAW, CRUSHED, PURIFIED, DUST)
            .property(MaterialProperty.HARDNESS, 6.0)
            .property(MaterialProperty.DENSITY, 4.20)
            .build();

    private Minerals() {}

    public static void init() {}
}
