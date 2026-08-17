package com.heaper.causality;

import com.heaper.causality.client.appearance.AppearanceRegistry;
import com.heaper.causality.core.material.Materials;
import com.heaper.causality.datagen.DataGenerators;
import com.heaper.causality.registry.ModCreateTabs;
import com.heaper.causality.registry.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ProjectCausality.MODID)
public class ProjectCausality {
    public static final String MODID = "project_causality";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProjectCausality(IEventBus modEventBus, ModContainer modContainer) {
        Materials.init();
        AppearanceRegistry.bootstrap();

        ModItems.register(modEventBus);
        ModCreateTabs.register(modEventBus);

        DataGenerators.register(modEventBus);

        LOGGER.debug("Project causality loaded");
    }
}
