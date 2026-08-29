package com.heaper.causality.datagen;

import com.heaper.causality.ProjectCausality;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public final class ModModelTemplates {

    public static final TextureSlot CASING = TextureSlot.create("casing");
    public static final TextureSlot FRONT = TextureSlot.create("front");

    public static final ModelTemplate MACHINE_FACE = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(
                    ProjectCausality.MODID, "block/machine_face")),
            Optional.empty(),
            CASING, FRONT);

    private ModModelTemplates() {}
}
