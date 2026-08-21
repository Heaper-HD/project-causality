package com.heaper.causality.multiblock;

import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.component.ComponentTypes;
import com.heaper.causality.core.recipe.ProcessType;

public final class Multiblocks {
    public static final MultiblockDefinition TEST_3X3 = new MultiblockDefinition() {
        @Override
        public String id() {
            return "crusher";
        }
        @Override
        public int minSize() {
            return 3;
        }
        @Override
        public int maxSize() {
            return 3;
        }

        @Override
        public ProcessType processType() {
            return ProcessType.MACERATOR;
        }

        @Override
        public PortRequirements portRequirements() {
            return PortRequirements.items(2, 2);
        }


        @Override
        public StructurePattern patternFor(int size) {
            return StructurePattern.builder()
                    .layer("CCC",
                            "CCC",
                            "CCC")
                    .layer("C@C",
                            "C C",
                            "CCC")
                    .layer("CCC",
                            "CCC",
                            "CCC")
                    .where('C', BlockMatcher.anyOf(
                            BlockMatcher.component(ComponentTypes.CASING),
                            BlockMatcher.blockType(ItemPortBlock.class, "an item port")))
                    .build();
        }
    };

    private Multiblocks() {}
}
