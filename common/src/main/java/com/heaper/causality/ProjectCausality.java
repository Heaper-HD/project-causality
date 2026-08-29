package com.heaper.causality;

import com.heaper.causality.client.ClientSetup;
import com.heaper.causality.client.MaterialTooltipHandler;
import com.heaper.causality.client.appearance.AppearanceRegistry;
import com.heaper.causality.core.material.Materials;
import com.heaper.causality.datagen.DataGenerators;
import com.heaper.causality.event.StructureEvents;
import com.heaper.causality.multiblock.Multiblocks;
import com.heaper.causality.registry.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(ProjectCausality.MODID)
public class ProjectCausality {
    public static final String MODID = "project_causality";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProjectCausality(IEventBus modEventBus, ModContainer modContainer) {
        Materials.init();
        AppearanceRegistry.bootstrap();
        Multiblocks.init();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        DataGenerators.register(modEventBus);
        StructureEvents.register();

        if (FMLEnvironment.getDist() == Dist.CLIENT)
            ClientSetup.register(modEventBus);

        MaterialTooltipHandler.register();

        LOGGER.debug("Project causality loaded");
    }
}
