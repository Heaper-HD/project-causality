package com.heaper.causality.datagen;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class DataGenerators {

    private DataGenerators() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(DataGenerators::gatherData);
    }

    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new MaterialLangProvider (generator.getPackOutput()));
        generator.addProvider(true, new MaterialModelProvider(generator.getPackOutput()));
    }
}
