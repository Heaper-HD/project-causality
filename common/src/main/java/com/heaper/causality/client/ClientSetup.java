package com.heaper.causality.client;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.client.screen.ItemPortScreen;
import com.heaper.causality.registry.ModBlocks;
import com.heaper.causality.registry.ModMenus;
import com.heaper.causality.registry.ModMultiblocks;
import com.heaper.causality.registry.ModPorts;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.stream.Stream;

public class ClientSetup {

    private ClientSetup() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ClientSetup::onClientSetup);
        modEventBus.addListener(ClientSetup::onBlockTints);
    }

    private static void onClientSetup(RegisterMenuScreensEvent event) {
        event.register(ModMenus.ITEM_PORT.get(), ItemPortScreen::new);
    }

    private static void onBlockTints(RegisterColorHandlersEvent.BlockTintSources event) {
        ComponentTint.bootstrap();

        event.register(List.of(new ComponentTint()), blocks(ModBlocks.all().values()));

        CasingTint casing = new CasingTint();

        event.register(List.of(casing), blocks(ModPorts.all().values()));
        event.register(List.of(casing), blocks(ModMultiblocks.all().values()));
    }

    private static Block[] blocks(Iterable<? extends DeferredBlock<? extends Block>> holders) {
        return Stream.of(holders)
                .flatMap(i -> java.util.stream.StreamSupport.stream(i.spliterator(), false))
                .map(DeferredBlock::get)
                .toArray(Block[]::new);
    }
}
