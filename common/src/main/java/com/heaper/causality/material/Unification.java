package com.heaper.causality.material;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.registry.ModItems;
import net.minecraft.world.item.Item;

import java.util.Optional;

public final class Unification {

    private Unification() {}
    
    public static Optional<Item> resolve(Material material, MaterialForm form) {
        return ModItems.get(material, form)
                .<Item>map(deferred -> deferred.get())
                .or(() -> VanillaOverlap.vanillaItem(material, form));
    }
}
