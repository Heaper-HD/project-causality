package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.component.ComponentTypes;
import com.heaper.causality.core.material.Elements;
import com.heaper.causality.core.material.MaterialForm;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProjectCausality.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ELEMENTS =
            TABS.register("elements", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + ProjectCausality.MODID + ".elements"))
                    .icon(() -> ModItems.get(Elements.IRON, MaterialForm.INGOT)
                            .map(i -> new ItemStack(i.get()))
                            .orElse(ItemStack.EMPTY))
                    .displayItems((params, output) ->
                            ModItems.display().values().forEach(s -> output.accept(s.get())))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS =
            TABS.register("blocks", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + ProjectCausality.MODID + ".blocks"))
                    .icon(() -> ModBlocks.get(ComponentTypes.CASING, Elements.IRON)
                            .map(i -> new ItemStack(i.get()))
                            .orElse(ItemStack.EMPTY))
                    .displayItems((params, output) ->
                            ModBlocks.allItems().values().forEach(s -> output.accept(s.get())))
                    .build());

    private ModCreativeTabs() {}

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
