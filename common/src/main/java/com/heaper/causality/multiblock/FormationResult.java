package com.heaper.causality.multiblock;

import com.heaper.causality.core.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public sealed interface FormationResult {

    String message();

    record Success(
            List<BlockPos> worldPositions,
            Map<Material, Integer> materialCounts,
            List<BlockPos> inputPorts,
            List<BlockPos> outputPorts
    ) implements FormationResult {
        @Override
        public String message() {
            String mats = materialCounts.entrySet().stream()
                    .map(e -> e.getValue() + "x " + e.getKey().id())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("no materials");

            return "Formed - " + mats
                    + " | ports: " + inputPorts.size() + " in, " + outputPorts.size() + " out";
        }

    }

    record Failure(
            BlockPos relative,
            BlockPos world,
            String expected,
            BlockState found
    ) implements FormationResult {
        @Override
        public String message() {
            return "Expected " + expected + " at " + relative.getX() + ", "
                    + relative.getY() + ", " + relative.getZ()
                    + " - found " + found.getBlock().getName().getString();
        }

    }

    record PortFailure(String reason) implements FormationResult {
        @Override public String message() { return reason; }
    }
}
