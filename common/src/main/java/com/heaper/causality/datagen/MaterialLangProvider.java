package com.heaper.causality.datagen;

import com.heaper.causality.ProjectCausality;
import com.heaper.causality.core.material.MaterialForm;
import com.heaper.causality.core.material.MaterialStack;
import com.heaper.causality.material.MaterialItem;
import com.heaper.causality.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Locale;
import java.util.Map;

public class MaterialLangProvider extends LanguageProvider {

    private static final Map<MaterialForm, String> PATTERNS = Map.ofEntries(
            Map.entry(MaterialForm.ORE, "%s Ore"),
            Map.entry(MaterialForm.RAW, "%s Raw"),
            Map.entry(MaterialForm.CRUSHED, "Crushed %s"),
            Map.entry(MaterialForm.PURIFIED, "Purified %s"),
            Map.entry(MaterialForm.CENTRIFUGED, "Centrifuged %s"),

            Map.entry(MaterialForm.DUST, "%s Dust"),
            Map.entry(MaterialForm.SMALL_DUST, "Small %s Dust"),
            Map.entry(MaterialForm.TINY_DUST, "Tiny %s Dust"),

            Map.entry(MaterialForm.INGOT, "%s Ingot"),
            Map.entry(MaterialForm.HOT_INGOT, "Hot %s Ingot"),
            Map.entry(MaterialForm.PLATE, "%s Plate"),
            Map.entry(MaterialForm.DOUBLE_PLATE, "Double %s Plate"),
            Map.entry(MaterialForm.FOIL, "%s Foil"),
            Map.entry(MaterialForm.ROD, "%s Rod"),
            Map.entry(MaterialForm.LONG_ROD, "Long %s Rod"),
            Map.entry(MaterialForm.RING, "%s Ring"),
            Map.entry(MaterialForm.SCREW, "%s Screw"),
            Map.entry(MaterialForm.BOLT, "%s Bolt"),
            Map.entry(MaterialForm.GEAR, "%s Gear"),
            Map.entry(MaterialForm.NUGGET, "%s Nugget"),

            Map.entry(MaterialForm.CRYSTAL, "%s Crystal"),
            Map.entry(MaterialForm.WIRE_FINE, "Fine %s Wire")
    );

    public MaterialLangProvider(PackOutput output) {
        super(output, ProjectCausality.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + ProjectCausality.MODID + ".elements", "Project Causality Elements");

        for (Map.Entry<MaterialStack, DeferredItem<MaterialItem>> entry : ModItems.all().entrySet()) {
            MaterialStack stack = entry.getKey();

            String materialName = titleCase(stack.material().id());
            String pattern = PATTERNS.getOrDefault(stack.form(),
                    "%s " + titleCase(stack.form().name().toLowerCase(Locale.ROOT)));

            add(entry.getValue().get(), String.format(pattern, materialName));
        }
    }

    private static String titleCase(String snake) {
        StringBuilder sb = new StringBuilder(snake.length());
        boolean upper = true;
        for (char c : snake.toCharArray()) {
            if (c == '_') {
                sb.append(' ');
                upper = true;
            } else {
                sb.append(upper ? Character.toUpperCase(c) : c);
                upper = false;
            }
        }
        return sb.toString();
    }
}
