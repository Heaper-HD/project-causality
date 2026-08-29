package com.heaper.causality.material;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.core.material.MaterialProperty;
import com.heaper.causality.core.material.composition.Composition;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MaterialItem extends Item {

    private final Material material;
    private final MaterialForm form;

    public MaterialItem(Material material, MaterialForm form, Properties properties) {
        super(properties);
        this.material = material;
        this.form = form;
    }

    public Material material() {
        return this.material;
    }

    public MaterialForm form() {
        return this.form;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        MaterialTooltip.append(material, builder);
    }
}
