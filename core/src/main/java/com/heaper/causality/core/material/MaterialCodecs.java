package com.heaper.causality.core.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.Locale;

public class MaterialCodecs {

    private MaterialCodecs() {}

    public static final Codec<Material> MATERIAL = Codec.STRING.comapFlatMap(
            id -> MaterialRegistry.find(id)
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "unknown material: " + id)),
            Material::id);

    public static final Codec<MaterialForm> FORM = Codec.STRING.comapFlatMap(
            name -> {
                try {
                    return DataResult.success(
                            MaterialForm.valueOf(name.toUpperCase(Locale.ROOT)));
                } catch (IllegalArgumentException e) {
                    return DataResult.error(() -> "unknown material form: " + name);
                }
            },
            form -> form.name().toLowerCase(Locale.ROOT));
}
