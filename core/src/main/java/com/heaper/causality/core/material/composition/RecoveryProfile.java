package com.heaper.causality.core.material.composition;

public record RecoveryProfile(String processId, double resolution, double efficiency) {
    public static final RecoveryProfile MACERATION = new RecoveryProfile("macerator", 0.10, 0.85);
    public static final RecoveryProfile CENTRIFUGE = new RecoveryProfile("centrifuge", 0.03, 0.9);
    public static final RecoveryProfile MAGNETIC_SEPARATION = new RecoveryProfile("magnetic_separator", 0.05, 0.95);
    public static final RecoveryProfile FROTH_FLOTATION = new RecoveryProfile("froth_flotation_cell", 0.01, 0.92);
    public static final RecoveryProfile GRAVITY_SEPARATION = new RecoveryProfile("gravity_separator", 0.02, 0.88);
    public static final RecoveryProfile FLOCCULATION = new RecoveryProfile("flocculator", 0.04, 0.91);

    public static final RecoveryProfile HEAP_LEACHING = new RecoveryProfile("heap_leacher", 0.00005, 0.80);
    public static final RecoveryProfile ELUTION = new RecoveryProfile("eluter", 0.005, 0.97);
    public static final RecoveryProfile CAKE_WASHING = new RecoveryProfile("cake_washer", 0.0005, 0.90);

    public static final RecoveryProfile ION_EXCHANGE = new RecoveryProfile("ion_exchanger", 0.0000005, 0.99);
    public static final RecoveryProfile SOLVENT_EXTRACTION = new RecoveryProfile("solvent_extractor", 0.00005, 0.97);
    public static final RecoveryProfile CARBON_ADSORPTION = new RecoveryProfile("carbon_adsorber", 0.000002, 0.94);
    public static final RecoveryProfile CHEMICAL_PRECIPITATION = new RecoveryProfile("chemical_precipitator", 0.000005, 0.98);
    public static final RecoveryProfile CEMENTATION = new RecoveryProfile("cementator", 0.00001, 0.95);

    public static final RecoveryProfile REVERSE_OSMOSIS = new RecoveryProfile("reverse_osmosis", 0.0002, 0.94);
    public static final RecoveryProfile BIOSORPTION = new RecoveryProfile("biosorber", 0.00002, 0.87);
    public static final RecoveryProfile BIO_TARGETING  = new RecoveryProfile("bio_targeter", 0.00000001, 0.98);

    public static final RecoveryProfile EVAPORATIVE_CRYSTALLIZATION = new RecoveryProfile("evaporative_crystallizer", 0.01, 0.995);
    public static final RecoveryProfile PYROMETALLURGICAL_ROASTING = new RecoveryProfile("pyro_roaster", 0.05, 0.88);
    public static final RecoveryProfile SUPERCRITICAL_EXTRACTION = new RecoveryProfile("supercritical_extractor", 0.000001, 0.92);
    public static final RecoveryProfile ELECTROLYSIS = new RecoveryProfile("electrolyzer", 0.005, 0.9);
}
