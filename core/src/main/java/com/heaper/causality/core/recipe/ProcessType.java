package com.heaper.causality.core.recipe;

public record ProcessType(String id) {

    public static final ProcessType MACERATOR = new ProcessType("macerator");
    public static final ProcessType CENTRIFUGE = new ProcessType("centrifuge");
    public static final ProcessType ELECTROLYZER = new ProcessType("electrolyzer");
    public static final ProcessType FORTH_FLOTATION = new ProcessType("forth_flotation");

    public static final ProcessType BENDER = new ProcessType("bender");
    public static final ProcessType WIRE_MILL = new ProcessType("wire_mill");
    public static final ProcessType ARC_FURNACE = new ProcessType("arc_furnace");
    public static final ProcessType INDUCTION_FURNACE = new ProcessType("induction_furnace");

    public static final ProcessType ASSEMBLER = new ProcessType("assembler");
}
