package com.axialeaa.blockybubbles.config;

import net.caffeinemc.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.text.Text;

import java.util.Locale;

public interface EnumOptionTextProvider extends TextProvider {

    String getPath();

    @Override
    default Text getLocalizedName() {
        return Options.getOptionText(this.getPath() + '.' + ((Enum<?>) this).name().toLowerCase(Locale.ROOT));
    }

}
