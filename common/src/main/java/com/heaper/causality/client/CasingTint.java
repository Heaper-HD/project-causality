package com.heaper.causality.client;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.block.entity.ItemPortBE;
import com.heaper.causality.block.entity.MultiblockControllerBE;
import com.heaper.causality.client.appearance.AppearanceRegistry;
import com.heaper.causality.core.material.Material;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class CasingTint implements BlockTintSource {

    private static final int UNTINTED = 0xFFFFFF;

    @Override
    public int color(BlockState state) {
        return UNTINTED;
    }

    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);

        Material material = switch (be) {
            case ItemPortBE port -> port.formedMaterial();
            case MultiblockControllerBE controller -> controller.casingMaterial();
            case null, default -> null;
        };

        return material != null ? AppearanceRegistry.colorOf(material) : UNTINTED;
    }
}
