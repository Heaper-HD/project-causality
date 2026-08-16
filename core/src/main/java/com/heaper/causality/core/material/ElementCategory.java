package com.heaper.causality.core.material;

public enum ElementCategory {
    ALKALI_METAL,
    ALKALINE_EARTH_METAL,
    TRANSITION_METAL,
    POST_TRANSITION_METAL,
    LANTHANIDE,
    ACTINIDE,
    METALLOID,
    REACTIVE_NONMETAL,
    HALOGEN,
    NOBLE_GAS,
    UNKNOWN;

    public boolean isMetal() {
        return switch (this) {
            case ALKALI_METAL, ALKALINE_EARTH_METAL, TRANSITION_METAL,
                POST_TRANSITION_METAL, LANTHANIDE, ACTINIDE -> true;
            default -> false;
        };
    }
}
