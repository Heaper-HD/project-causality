package com.heaper.causality.multiblock;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.port.MachinePort;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public final class StructurePattern {

    private final Map<BlockPos, BlockMatcher> positions;

    private StructurePattern(Map<BlockPos, BlockMatcher> positions) {
        this.positions = Map.copyOf(positions);
    }

    public int size() { return positions.size(); }

    public FormationResult validate(Level level, BlockPos controllerPos, Direction facing, PortRequirements ports) {
        List<BlockPos> found = new ArrayList<>(positions.size());
        Map<Material, Integer> counts = new HashMap<>();
        List<BlockPos> inputs = new ArrayList<>();
        List<BlockPos> outputs = new ArrayList<>();

        for (Map.Entry<BlockPos, BlockMatcher> entry : positions.entrySet()) {
            BlockPos relative = entry.getKey();
            BlockMatcher matcher = entry.getValue();

            BlockPos world = controllerPos.offset(StructureRotation.rotate(relative, facing));
            BlockState state = level.getBlockState(world);

            if (!matcher.matches(state))
                return new FormationResult.Failure(relative, world, matcher.describe(), state);

            matcher.materialOf(state).ifPresent(m -> counts.merge(m, 1, Integer::sum));
            found.add(world);

            if (level.getBlockEntity(world) instanceof MachinePort port) {
                switch (port.portDirection()) {
                    case INPUT -> inputs.add(world);
                    case OUTPUT -> outputs.add(world);
                }
            }
        }

        String portError = ports.checkOrNull(inputs.size(), outputs.size());
        if (portError != null) return new FormationResult.PortFailure(portError);

        return new FormationResult.Success(
                List.copyOf(found), Map.copyOf(counts),
                List.copyOf(inputs), List.copyOf(outputs));
    }

    public static Builder builder() { return new Builder(); }

    public static StructurePattern of(Map<BlockPos, BlockMatcher> positions) {
        return new StructurePattern(positions);
    }

    public static final class Builder {

        private final List<String[]> layers = new ArrayList<>();
        private final Map<Character, BlockMatcher> matchers = new HashMap<>();

        private Builder() {}

        public Builder layer(String... rows) {
            layers.add(rows);
            return this;
        }

        public Builder where(char symbol, BlockMatcher matcher) {
            matchers.put(symbol, matcher);
            return this;
        }

        public StructurePattern build() {
            Map<BlockPos, BlockMatcher> raw = new HashMap<>();
            BlockPos controller = null;

            for (int y = 0; y < layers.size(); y++) {
                String[] rows = layers.get(y);
                for (int z = 0; z < rows.length; z++) {
                    String row = rows[z];
                    for (int x = 0; x < row.length(); x++) {
                        char c = row.charAt(x);
                        if (c == ' ') continue;

                        BlockPos pos = new BlockPos(x, y, z);

                        if (c == '@') {
                            if (controller != null)
                                throw new IllegalStateException("pattern has two controllers");
                            controller = pos;
                            continue;
                        }

                        BlockMatcher matcher = matchers.get(c);
                        if (matcher == null)
                            throw new IllegalStateException(
                                    "no matcher registered for '" + c + "'");
                        raw.put(pos, matcher);
                    }
                }
            }

            if (controller == null)
                throw new IllegalArgumentException("pattern nas no '@' controller marked");

            Map<BlockPos, BlockMatcher> relative = new HashMap<>(raw.size());
            for (Map.Entry<BlockPos, BlockMatcher> e : raw.entrySet())
                relative.put(e.getKey().subtract(controller), e.getValue());

            return new StructurePattern(relative);
        }
    }
}
