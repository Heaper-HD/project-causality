package com.heaper.causality.datagen;

import com.heaper.causality.ProjectCausality;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.Arrays;
import java.util.Optional;

public final class ModModelTemplates {

    private static final TextureSlot[] LAYERS = {
            TextureSlot.LAYER0,
            TextureSlot.LAYER1,
            TextureSlot.LAYER2,
            TextureSlot.create("layer3"),
    };

    public static final ModelTemplate[] LAYERED = new ModelTemplate[LAYERS.length];

    static {
        for (int n = 1; n <= LAYERS.length; n++) {
            LAYERED[n - 1] = new ModelTemplate(
                    Optional.of(Identifier.withDefaultNamespace("item/generated")),
                    Optional.empty(),
                    Arrays.copyOf(LAYERS, n)
            );
        }
    }

    public static TextureSlot layerSlot(int index) {
        return LAYERS[index];
    }

    public static ModelTemplate layeredItem(int count) {
        if (count < 1 || count > LAYERS.length)
            throw new IllegalStateException("layer count out of range: " + count);
        return LAYERED[count - 1];
    }

    public static final TextureSlot CASING = TextureSlot.create("casing");
    public static final TextureSlot FRONT = TextureSlot.create("front");

    public static final ModelTemplate MACHINE_FACE = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(
                    ProjectCausality.MODID, "block/machine_face")),
            Optional.empty(),
            CASING, FRONT);

    public static final ModelTemplate TINTED_CUBE_ALL = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(
                    ProjectCausality.MODID, "block/tinted_cube_all")),
            Optional.empty(),
            TextureSlot.ALL
    );

    private ModModelTemplates() {}
}
