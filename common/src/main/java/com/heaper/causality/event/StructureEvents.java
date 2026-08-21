package com.heaper.causality.event;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.multiblock.StructureIndex;
import com.heaper.causality.recipe.MachineRecipeLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

public final class StructureEvents {

    private StructureEvents() {}

    public static void register() {
        NeoForge.EVENT_BUS.addListener(StructureEvents::onBreak);
        NeoForge.EVENT_BUS.addListener(StructureEvents::onPlace);
        NeoForge.EVENT_BUS.addListener((AddServerReloadListenersEvent event) ->
                event.addListener(
                        Identifier.fromNamespaceAndPath(ProjectCausality.MODID, "machine_recipes"),
                        new MachineRecipeLoader()));
    }

    private static void onBreak(BreakBlockEvent event) {
        if (event.getLevel() instanceof Level level)
            StructureIndex.onBlockChanged(level, event.getPos());
    }

    private static void onPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel() instanceof Level level)
            StructureIndex.onBlockChanged(level, event.getPos());
    }
}
