package com.heaper.causality.core.material;

public enum MaterialForm {
    FLUID, GAS,
    TAILING, SLAG,
    ORE, RAW, CRUSHED, PURIFIED, CENTRIFUGED,
    DUST, SMALL_DUST, TINY_DUST,
    INGOT, HOT_INGOT, PLATE, DOUBLE_PLATE, FOIL, ROD, LONG_ROD, RING, SCREW, BOLT, GEAR, NUGGET,
    CRYSTAL, WIRE_FINE;

    public String conventionTag(Material m) {
        return switch (this) {
            case INGOT -> "c:ingots/" + m.id();
            case NUGGET -> "c:nuggets/" + m.id();
            case PLATE -> "c:plates/" + m.id();
            case DUST -> "c:dusts/" + m.id();
            case ORE -> "c:ores/" + m.id();
            case RAW -> "c:raw_materials/" + m.id();
            case ROD -> "c:rods/" + m.id();
            case GEAR -> "c:gears/" + m.id();
            default -> "project_causality:" + name().toLowerCase() + "/" + m.id();
        };
    }
}
