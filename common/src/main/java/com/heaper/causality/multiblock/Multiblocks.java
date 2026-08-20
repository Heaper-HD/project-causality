package com.heaper.causality.multiblock;

import com.heaper.causality.block.ItemPortBlock;
import com.heaper.causality.component.ComponentTypes;

public final class Multiblocks {
    public static final MultiblockDefinition TEST_3X3 = new MultiblockDefinition() {
        @Override
        public String id() {
            return "test_3x3";
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

        @Override
        public PortRequirements portRequirements() {
            return PortRequirements.items(4, 4);
        }
    };

    private Multiblocks() {}
}
