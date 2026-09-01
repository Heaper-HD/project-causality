package com.heaper.causality.multiblock;

import com.heaper.causality.core.recipe.ProcessType;

public final class Multiblocks {

    public static final MultiblockDefinition CRUSHER =
            MultiblockDefinition.builder("crusher")
                    .displayerName("Crusher")
                    .size(3, 3)
                    .process(ProcessType.MACERATOR)
                    .ports(PortRequirements.NONE)
                    .pattern(size -> StructurePattern.builder()
                            .layer("CCC",
                                    "CCC",
                                    "CCC")
                            .layer("C@C",
                                    "C C",
                                    "CCC")
                            .layer("CCC",
                                    "CCC",
                                    "CCC")
                            .wall('C')
                            .build())
                    .build();

    private Multiblocks() {}

    public static void init() {
        MultiblockRegistry.freeze();
    }
}
