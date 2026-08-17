package com.heaper.causality.material;

import com.heaper.causality.core.material.Material;
import com.heaper.causality.core.material.MaterialForm;
import net.minecraft.world.item.Item;

public class MaterialItem extends Item {
    private final Material material;
    private final MaterialForm form;

    public MaterialItem(Material material, MaterialForm form, Properties properties) {
        super(properties);
        this.material = material;
        this.form = form;
    }

    public Material material() {
        return this.material;
    }

    public MaterialForm form() {
        return this.form;
    }
}
