package com.heaper.causality.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class DataGenerators {

    private DataGenerators() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(DataGenerators::gatherData);
    }

    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(true, new ModLangProvider(output));
        generator.addProvider(true, new ModModelProvider(output));
        generator.addProvider(true, new MachineRecipeProvider(output));
    }
}
