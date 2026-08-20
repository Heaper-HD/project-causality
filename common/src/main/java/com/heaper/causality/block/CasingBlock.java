package com.heaper.causality.block;

import com.heaper.causality.component.ComponentType;
import com.heaper.causality.component.ComponentTypes;
import com.heaper.causality.core.material.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CasingBlock extends Block implements ComponentBlock {
    private final Material material;

    public CasingBlock(Material material, BlockBehaviour.Properties properties) {
        super(properties);
        this.material = material;
    }

    @Override
    public Material material() {
        return material;
    }

    @Override
    public ComponentType componentType() { return ComponentTypes.CASING; }
}
