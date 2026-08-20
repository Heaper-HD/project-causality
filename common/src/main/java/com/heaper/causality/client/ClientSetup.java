package com.heaper.causality.client;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.client.screen.ItemPortScreen;
import com.heaper.causality.registry.ModMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientSetup {

    private ClientSetup() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ClientSetup::onClientSetup);
    }

    private static void onClientSetup(RegisterMenuScreensEvent event) {
        event.register(ModMenus.ITEM_PORT.get(), ItemPortScreen::new);
    }
}
