package com.heaper.causality.client;

import com.heaper.causality.material.MaterialTooltip;
import com.heaper.causality.material.VanillaOverlap;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public final class MaterialTooltipHandler {

    private MaterialTooltipHandler() {}

    public static void register() {
        NeoForge.EVENT_BUS.addListener(MaterialTooltipHandler::onTooltip);
    }

    private static void onTooltip(ItemTooltipEvent event) {
        VanillaOverlap.materialOf(event.getItemStack().getItem())
                .ifPresent(stack ->
                        MaterialTooltip.append(stack.material(), event.getToolTip()::add));
    }
}
