package com.heaper.causality.multiblock;

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
}
