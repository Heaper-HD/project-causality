package com.heaper.causality.client;

import com.heaper.causality.registry.ModMultiblocks;
import com.heaper.causality.registry.ModPorts;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;
import java.util.stream.Stream;

public class PortColorHandler {

    public void register(IEventBus modEventBus) {
        modEventBus.addListener(PortColorHandler::registerBlockColors);
    }

    private static void registerBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
         event.register(List.of(new CasingTint()),
                 Stream.concat(
                         ModPorts.blocks().stream(),
                         ModMultiblocks.blocks().stream())
                         .toArray(Block[]::new));
    }
}
