package com.heaper.causality.component;

import com.heaper.causality.block.CasingBlock;
import com.heaper.causality.core.material.MaterialProperty;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public final class ComponentTypes {
    public static final ComponentType CASING = ComponentType.builder("casing")
            .requires(MaterialProperty.TENSILE_STRENGTH, 150)
            .blockFactory(CasingBlock::new)
            .properties((material, props) -> props
                    .mapColor(MapColor.METAL)
                    .strength(
                            hardnessOf(material),
                            resistanceOf(material))
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops())
            .build();

    public static final List<ComponentType> ALL = List.of(CASING);

    private ComponentTypes() {}

    private static float hardnessOf(com.heaper.causality.core.material.Material m) {
        return m.get(MaterialProperty.HARDNESS).map(Double::floatValue).orElse(3.0f);
    }

    private static float resistanceOf(com.heaper.causality.core.material.Material m) {
        return m.get(MaterialProperty.TENSILE_STRENGTH)
                .map(t -> Math.min(t / 50.0f, 30.0f))
                .orElse(6.0f);
    }
}
