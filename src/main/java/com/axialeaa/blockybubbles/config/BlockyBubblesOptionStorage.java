package com.axialeaa.blockybubbles.config;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.storage.OptionStorage;

public class BlockyBubblesOptionStorage implements OptionStorage<BlockyBubblesConfig> {

    private static BlockyBubblesConfig CONFIG;

    @Override
    public BlockyBubblesConfig getData() {
        return this.getConfig();
    }

    @Override
    public void save() {
        this.getConfig().writeToFile();
    }

    public BlockyBubblesConfig getConfig() {
        if (CONFIG == null)
            CONFIG = BlockyBubblesConfig.loadFromFile();

        return CONFIG;
    }

}