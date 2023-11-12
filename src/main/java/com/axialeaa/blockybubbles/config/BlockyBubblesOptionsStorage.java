package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public class BlockyBubblesOptionsStorage implements OptionStorage<BlockyBubblesGameOptions> {

    private final BlockyBubblesGameOptions options = BlockyBubbles.options();

    @Override
    public BlockyBubblesGameOptions getData() {
        return this.options;
    }

    @Override
    public void save() {
        this.options.writeChanges();
    }

}
