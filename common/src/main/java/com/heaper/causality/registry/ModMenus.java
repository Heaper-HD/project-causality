package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.menu.ItemPortMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ProjectCausality.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ItemPortMenu>> ITEM_PORT =
            MENUS.register("item_port",
                    () -> IMenuTypeExtension.create(ItemPortMenu::fromNetwork));

    private ModMenus() {}

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
