package com.heaper.causality.multiblock;

import com.heaper.causality.block.entity.CrusherControllerBE;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class StructureIndex {

    private static final Map<LevelAccessor, Long2LongOpenHashMap> BY_LEVEL = new WeakHashMap<>();

    private static final long NO_OWNER = Long.MIN_VALUE;

    private StructureIndex() {}

    private static Long2LongOpenHashMap mapFor(LevelAccessor level) {
        return BY_LEVEL.computeIfAbsent(level, l -> {
            Long2LongOpenHashMap map = new Long2LongOpenHashMap();
            map.defaultReturnValue(NO_OWNER);
            return map;
        });
    }

    public static void register(Level level, BlockPos controller, List<BlockPos> positions) {
        Long2LongOpenHashMap map = mapFor(level);
        long owner = controller.asLong();
        for (BlockPos pos : positions)
            map.put(pos.asLong(), owner);
    }

    public static void unregister(Level level, BlockPos controller) {
        Long2LongOpenHashMap map = mapFor(level);
        long owner = controller.asLong();
        map.values().removeIf(v -> v == owner);
    }

    public static BlockPos ownerOf(LevelAccessor level, BlockPos pos) {
        long owner = mapFor(level).get(pos.asLong());
        return owner == NO_OWNER ? null : BlockPos.of(owner);
    }

    public static void onBlockChanged(Level level, BlockPos pos) {
        BlockPos controllerPos = ownerOf(level, pos);
        if (controllerPos == null) return;

        if (level.getBlockEntity(controllerPos) instanceof CrusherControllerBE be)
            be.invalidateStructure();
        else

            mapFor(level).values().removeIf(v -> v == controllerPos.asLong());
    }

    public static int size(LevelAccessor level) {
        return mapFor(level).size();
    }
}
