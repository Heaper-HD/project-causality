package com.heaper.causality.multiblock;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class MultiblockRegistry {

    private static final Map<String, MultiblockDefinition> BY_ID = new LinkedHashMap<>();
    private static boolean frozen = false;

    private MultiblockRegistry() {}

    public static MultiblockDefinition register(MultiblockDefinition definition) {
        if (frozen)
            throw new IllegalStateException("multiblock registry frozen" + definition.id());
        if (BY_ID.putIfAbsent(definition.id(), definition) != null)
            throw new IllegalStateException("duplicate multiblock id: " + definition.id());
        return definition;
    }

    public static Optional<MultiblockDefinition> find(String id) {
        return Optional.ofNullable(BY_ID.get(id));
    }

    public static Iterable<MultiblockDefinition> all() {
        return Collections.unmodifiableCollection(BY_ID.values());
    }

    public static int size() { return BY_ID.size(); }

    public static boolean isFrozen() { return frozen; }

    public static void freeze() {
        if (frozen) return;

        for (MultiblockDefinition d : BY_ID.values()) {
            if (!d.id().matches("[a-z0-9_.-]+"))
                throw new IllegalStateException("invalid multiblock id: " + d.id());

            d.defaultPattern();
        }

        frozen = true;
    }
}
