package com.heaper.causality.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class StructureRotation {

    private StructureRotation() {}

    public static BlockPos rotate(BlockPos relative, Direction facing) {
        int x = relative.getX();
        int y = relative.getY();
        int z = relative.getZ();
        return switch (facing) {
            case NORTH -> new BlockPos(x, y, z);
            case EAST -> new BlockPos(-z, y, x);
            case SOUTH -> new BlockPos(-x, y, -z);
            case WEST -> new BlockPos(z, y, -x);
            default -> throw new IllegalArgumentException(
                    "multiblocks only face horizontally, got " + facing);
        };
    }

    public static BlockPos unrotate(BlockPos worldOffset, Direction facing) {
        int x = worldOffset.getX();
        int y = worldOffset.getY();
        int z = worldOffset.getZ();
        return switch (facing) {
            case NORTH -> new BlockPos(x, y, z);
            case EAST -> new BlockPos(z, y, -x);
            case SOUTH -> new BlockPos(-x, y, -z);
            case WEST -> new BlockPos(-z, y, x);
            default -> throw new IllegalArgumentException("non-horizontal facing: " + facing);
        };
    }
}
