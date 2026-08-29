package com.heaper.causality.material;

import com.heaper.causality.core.material.Elements;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.core.material.MaterialStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class VanillaOverlap {

    private static final Map<Item, MaterialStack> BY_ITEM = new HashMap<>();
    private static final Map<MaterialStack, Item> OVERLAPS = new LinkedHashMap<>();

    static {
        put(Elements.IRON, MaterialForm.INGOT, Items.IRON_INGOT);
        put(Elements.GOLD, MaterialForm.INGOT, Items.GOLD_INGOT);
        put(Elements.COPPER, MaterialForm.INGOT, Items.COPPER_INGOT);

        put(Elements.IRON, MaterialForm.NUGGET, Items.IRON_NUGGET);
        put(Elements.GOLD, MaterialForm.NUGGET, Items.GOLD_NUGGET);
        put(Elements.COPPER, MaterialForm.NUGGET, Items.COPPER_NUGGET);
    }

    private VanillaOverlap() {}

    private static void put(Material material, MaterialForm form, Item vanilla) {
        MaterialStack stack = new MaterialStack(material, form);
        OVERLAPS.put(stack, vanilla);
        BY_ITEM.put(vanilla, stack);
    }

    public static Optional<MaterialStack> materialOf(Item item) {
        return Optional.ofNullable(BY_ITEM.get(item));
    }

    public static boolean isProvidedByVanilla(Material material, MaterialForm form) {
        return OVERLAPS.containsKey(new MaterialStack(material, form));
    }

    public static Optional<Item> vanillaItem(Material material, MaterialForm form) {
        return Optional.ofNullable(OVERLAPS.get(new MaterialStack(material, form)));
    }

    public static Map<MaterialStack, Item> all() {
        return Map.copyOf(OVERLAPS);
    }
}
