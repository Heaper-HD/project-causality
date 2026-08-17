package com.heaper.causality.datagen;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.client.appearance.Appearance;
import com.heaper.causality.client.appearance.AppearanceRegistry;
import com.heaper.causality.core.material.MaterialStack;
import com.heaper.causality.material.MaterialItem;
import com.heaper.causality.registry.ModItems;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredItem;
import java.util.Map;

public class MaterialModelProvider extends ModelProvider {

    public MaterialModelProvider(PackOutput output) {
        super(output, ProjectCausality.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Map.Entry<MaterialStack, DeferredItem<MaterialItem>> entry : ModItems.all().entrySet()) {
            MaterialStack stack = entry.getKey();
            MaterialItem item = entry.getValue().get();
            Appearance appearance = AppearanceRegistry.get(stack.material());

            Identifier texture = Identifier.fromNamespaceAndPath(
                    ProjectCausality.MODID,
                    "item/" + appearance.texturePath(stack.form()));

            Identifier model = ModelLocationUtils.getModelLocation(item);

            ModelTemplates.FLAT_ITEM.create(
                    model,
                    TextureMapping.layer0(
                            new Material(texture)
                    ),
                    itemModels.modelOutput);

            if (appearance.isExclusive(stack.form())) {
                itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(model));
            } else {
                itemModels.itemModelOutput.accept(item,
                        ItemModelUtils.tintedModel(model, new Constant(appearance.color())));
            }
        }
    }
}
