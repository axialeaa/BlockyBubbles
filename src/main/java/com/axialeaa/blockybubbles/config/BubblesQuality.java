package com.axialeaa.blockybubbles.config;

import net.minecraft.client.option.GraphicsMode;

public enum BubblesQuality implements EnumOptionTextProvider {

    DEFAULT, FANCY, FAST;

    static final String PATH = "quality";

    public boolean isFancy(GraphicsMode graphicsMode) {
        return this == FANCY || this == DEFAULT && graphicsMode != GraphicsMode.FAST;
    }

    @Override
    public String getPath() {
        return PATH;
    }

}