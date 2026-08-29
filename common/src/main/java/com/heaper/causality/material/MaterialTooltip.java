package com.heaper.causality.material;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialProperty;
import com.heaper.causality.core.material.composition.Composition;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class MaterialTooltip {

    private static final List<MaterialProperty<?>> SHOWN = List.of(
            MaterialProperty.MELTING_POINT,
            MaterialProperty.TENSILE_STRENGTH,
            MaterialProperty.HARDNESS,
            MaterialProperty.DENSITY);

    private MaterialTooltip() {}

    public static void append(Material material, Consumer<Component> lines) {
        appendComposition(material, lines);

        if (shiftHeld()) appendProperties(material, lines);
        else if (hasProperties(material))
            lines.accept(Component.translatable("tooltip.project_causality.hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY));
    }

    private static boolean shiftHeld() {
        Window window = Minecraft.getInstance().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT);
    }


    private static void appendComposition(Material material, Consumer<Component> lines) {
        switch (material.composition()) {

            case Composition.Element element -> {
                lines.accept(Component.literal(element.symbol())
                        .withStyle(ChatFormatting.DARK_GRAY));
            }

            case Composition.Compound compound -> {
                StringBuilder formula = new StringBuilder();
                for (Composition.Part part : compound.parts()) {
                    formula.append(symbolOf(part.material()));
                    if (part.count() > 1) formula.append(subscript(part.count()));
                }
                lines.accept(Component.literal(formula.toString())
                        .withStyle(ChatFormatting.DARK_GRAY));
            }

            case Composition.Mixture mixture -> {
                for (Map.Entry<Material, Double> e : mixture.fractions().entrySet())
                    lines.accept(Component.literal(" "
                                    + Math.round(e.getValue() * 100) + "% "
                                    + titleCase(e.getKey().id()))
                            .withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

    private static void appendProperties(Material material, Consumer<Component> lines) {
        lines.accept(Component.translatable("tooltip.project_causality.properties")
                .withStyle(ChatFormatting.GRAY));

        for (MaterialProperty<?> property : SHOWN) {
            material.get(castProperty(property)).ifPresent(value ->
                    lines.accept(Component.literal(" "
                                    + titleCase(property.id()) + ": "
                                    + format(value) + " " + property.unit())
                            .withStyle(ChatFormatting.DARK_GRAY)));
        }
    }

    private static boolean hasProperties(Material material) {
        return SHOWN.stream().anyMatch(material::has);
    }

    private static String subscript(int number) {
        StringBuilder sb = new StringBuilder();
        for (char c : String.valueOf(number).toCharArray())
            sb.append(c >= '0' && c <= '9' ? (char) ('\u2080' + (c - '0')) : c);
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private static MaterialProperty<Object> castProperty(MaterialProperty<?> property) {
        return (MaterialProperty<Object>) property;
    }

    private static String format(Object value) {
        if (value instanceof Double d)
            return d == Math.floor(d) ? String.valueOf(d.longValue()) : String.format("%.2f", d);
        return String.valueOf(value);
    }

    private static String symbolOf(Material material) {
        return material.composition() instanceof Composition.Element e
                ? e.symbol()
                : material.id();
    }

    private static String titleCase(String snake) {
        StringBuilder sb = new StringBuilder(snake.length());
        boolean upper = true;
        for (char c : snake.toCharArray()) {
            if (c == '_') {
                sb.append(' ');
                upper = true;
            } else {
                sb.append(upper ? Character.toUpperCase(c) : c);
                upper = false;
            }
        }
        return sb.toString();
    }
}
