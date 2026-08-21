package com.heaper.causality.multiblock;

import com.heaper.causality.core.recipe.ProcessType;

public interface MultiblockDefinition {

    String id();

    int minSize();
    int maxSize();

    StructurePattern patternFor(int size);

    default StructurePattern defaultPattern() {
        return patternFor(minSize());
    }

    default PortRequirements portRequirements() {
        return PortRequirements.NONE;
    }

    default ProcessType processType() { return null; }
}
