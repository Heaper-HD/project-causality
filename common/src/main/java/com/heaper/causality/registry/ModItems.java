package com.heaper.causality.registry;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.core.material.MaterialRegistry;
import com.heaper.causality.core.material.MaterialStack;
import com.heaper.causality.material.MaterialItem;
import com.heaper.causality.material.VanillaOverlap;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectCausality.MODID);
    private static final Map<MaterialStack, DeferredItem<MaterialItem>> MATERIAL_ITEMS = new LinkedHashMap<>();
    private static final Map<MaterialStack, Supplier<Item>> DISPLAY = new LinkedHashMap<>();

    private ModItems() {}

    public static void register(IEventBus modEventBus) {
        if(!MaterialRegistry.isFrozen())
            throw new IllegalStateException(
                    "Materials.init() must run before ModItems.register()");

        int count = 0;
        for (Material material : MaterialRegistry.all()) {
            for (MaterialForm form : material.forms()) {
                if (form == MaterialForm.FLUID || form == MaterialForm.GAS) continue;

                Optional<Item> vanilla = VanillaOverlap.vanillaItem(material, form);
                if (vanilla.isPresent()) {
                    DISPLAY.put(new MaterialStack(material, form), vanilla::get);
                    continue;
                }

                String name = material.id() + "_" + form.name().toLowerCase(Locale.ROOT);
                DeferredItem<MaterialItem> item = ITEMS.registerItem(
                        name,
                        props -> new MaterialItem(material, form, props));

                MATERIAL_ITEMS.put(new MaterialStack(material, form), item);
                DISPLAY.put(new MaterialStack(material, form), item::get);
                count++;
            }
        }

        ProjectCausality.LOGGER.info("Registered {} material items for {} materials",
                count, MaterialRegistry.all().size());

        ITEMS.register(modEventBus);
    }

    public static Optional<DeferredItem<MaterialItem>> get(Material material, MaterialForm form) {
        return Optional.ofNullable(MATERIAL_ITEMS.get(new MaterialStack(material, form)));
    }

    public static Map<MaterialStack, DeferredItem<MaterialItem>> all() {
        return Collections.unmodifiableMap(MATERIAL_ITEMS);
    }

    public static Map<MaterialStack, Supplier<Item>> display() {
        return Collections.unmodifiableMap(DISPLAY);
    }
}
