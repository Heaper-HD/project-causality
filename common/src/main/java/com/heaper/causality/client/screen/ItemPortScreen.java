package com.heaper.causality.client.screen;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.menu.ItemPortMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class ItemPortScreen extends AbstractContainerScreen<ItemPortMenu> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            ProjectCausality.MODID, "textures/gui/item_port.png");

    public ItemPortScreen(ItemPortMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
        super.extractContents(graphics, mouseX, mouseY, a);
    }
}
