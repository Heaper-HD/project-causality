package com.heaper.causality.multiblock;

import com.heaper.causality.core.recipe.ProcessType;
import org.jspecify.annotations.Nullable;

import java.util.function.IntFunction;

public interface MultiblockDefinition {

    String id();

    String displayName();

    int minSize();
    int maxSize();

    StructurePattern patternFor(int size);

    default @Nullable ProcessType processType() {
        return null;
    }

    default StructurePattern defaultPattern() {
        return patternFor(minSize());
    }

    default PortRequirements portRequirements() {
        return PortRequirements.NONE;
    }

    default String frontTexture() {
        return "controller/" + id();
    }

    static Builder builder (String id) {
        return new Builder(id);
    }

    final class Builder {
        private final String id;
        private String displayName;
        private int minSize = 1;
        private int maxSize = 1;
        private IntFunction<StructurePattern> pattern;
        private @Nullable ProcessType processType;
        private PortRequirements ports = PortRequirements.NONE;
        private @Nullable String frontTexture;

        private Builder(String id) {
            this.id = id;
        }

        public Builder displayerName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder size(int min, int max) {
            this.minSize = min;
            this.maxSize = max;
            return this;
        }

        public Builder pattern(IntFunction<StructurePattern> pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder pattern(StructurePattern fixed) {
            this.pattern = size -> fixed;
            return this;
        }

        public Builder process(ProcessType processType) {
            this.processType = processType;
            return this;
        }

        public Builder ports(PortRequirements ports) {
            this.ports = ports;
            return this;
        }

        public Builder frontTexture(String path) {
            this.frontTexture = path;
            return this;
        }

        public MultiblockDefinition build() {
            if (displayName == null)
                throw new IllegalStateException("multiblock " + id + " has no display name");
            if (pattern == null)
                throw new IllegalStateException("multiblock " + id + " has no pattern");
            if (minSize > maxSize)
                throw new IllegalStateException("multiblock " + id + " has minSize > maxSize");

            String front = frontTexture != null ? frontTexture : "controller/" + id;

            return MultiblockRegistry.register(new Built(
                    id, displayName, minSize, maxSize, pattern, processType, ports, front));
        }
    }

    record Built(
            String id,
            String displayName,
            int minSize,
            int maxSize,
            IntFunction<StructurePattern> patternFunction,
            @Nullable ProcessType processType,
            PortRequirements portRequirements,
            String frontTexture) implements MultiblockDefinition {

        @Override
        public StructurePattern patternFor(int size) {
            return patternFunction.apply(size);
        }
    }
}
