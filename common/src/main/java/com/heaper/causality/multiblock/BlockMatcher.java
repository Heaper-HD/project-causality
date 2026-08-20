package com.heaper.causality.multiblock;

import com.heaper.causality.block.ComponentBlock;
import com.heaper.causality.component.ComponentType;
import com.heaper.causality.core.material.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface BlockMatcher {

    boolean matches(BlockState state);

    default Optional<Material> materialOf(BlockState state) {
        return Optional.empty();
    }

    String describe();

    static BlockMatcher exactly(Supplier<? extends Block> block) {
        return new BlockMatcher() {
            @Override
            public boolean matches(BlockState state) {
                return state.is(block.get());
            }

            @Override
            public String describe() {
                return block.get().getName().toString();
            }
        };
    }

    static BlockMatcher component(ComponentType type) {
        return new BlockMatcher() {
            @Override
            public boolean matches(BlockState state) {
                return state.getBlock() instanceof ComponentBlock c
                        && c.componentType() == type;
            }

            @Override
            public Optional<Material> materialOf(BlockState state) {
                return state.getBlock() instanceof ComponentBlock c
                        ? Optional.of(c.material())
                        : Optional.empty();
            }

            @Override
            public String describe() {
                return "any " + type.id();
            }
        };
    }

    static BlockMatcher air() {
        return new BlockMatcher() {
            @Override
            public boolean matches(BlockState state) {
                return state.isAir();
            }

            @Override
            public String describe() {
                return "air";
            }
        };
    }

    static BlockMatcher any() {
        return new BlockMatcher() {
            @Override
            public boolean matches(BlockState state) {
                return true;
            }

            @Override
            public String describe() {
                return "anything";
            }
        };
    }

    static BlockMatcher anyOf(BlockMatcher... options) {
        return new BlockMatcher() {
            @Override
            public boolean matches(BlockState state) {
                for (BlockMatcher m : options) if (m.matches(state)) return true;
                return false;
            }

            @Override
            public Optional<Material> materialOf(BlockState state) {
                for (BlockMatcher m : options)
                    if (m.matches(state)) return m.materialOf(state);
                return Optional.empty();
            }

            @Override
            public String describe() {
                return Arrays.stream(options)
                        .map(BlockMatcher::describe)
                        .collect(Collectors.joining(" or "));
            }
        };
    }

    static BlockMatcher blockType(Class<?> blockClass, String description) {
        return new BlockMatcher() {
            @Override
            public boolean matches(BlockState state) {
                return blockClass.isInstance(state.getBlock());
            }

            @Override
            public String describe() {
                return description;
            }
        };
    }
}
