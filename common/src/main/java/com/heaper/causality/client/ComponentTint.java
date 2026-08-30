package com.heaper.causality.client;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.client.appearance.AppearanceRegistry;
import com.heaper.causality.registry.ModBlocks;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.IdentityHashMap;
import java.util.Map;

public class ComponentTint implements BlockTintSource {

    private static final int UNTINTED = 0xFFFFFF;
    private static final Map<Block, Integer> COLORS = new IdentityHashMap<>();

    public static void bootstrap() {
        COLORS.clear();
        ModBlocks.all().forEach((key, holder) ->
                COLORS.put(holder.get(), AppearanceRegistry.colorOf(key.material())));
    }

    @Override
    public int color(BlockState blockState) {
        return COLORS.getOrDefault(blockState.getBlock(), UNTINTED);
    }

    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        return color(state);
    }
}
