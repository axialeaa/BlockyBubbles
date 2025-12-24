package com.axialeaa.blockybubbles.config;

import net.minecraft.util.StringRepresentable;

public enum Quality implements StringRepresentable {

    FANCY("fancy"),
    FAST("fast");

    private final String path;

    Quality(String path) {
        this.path = path;
    }

    @Override
    public String getSerializedName() {
        return this.path;
    }

}