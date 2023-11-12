package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.config.BlockyBubblesGameOptions;
import com.axialeaa.blockybubbles.config.BlockyBubblesOptionsStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;

import java.util.logging.Logger;

public class BlockyBubbles implements ClientModInitializer {

    private static BlockyBubblesGameOptions CONFIG;
    private static Logger LOGGER;
    public static final BlockyBubblesOptionsStorage blockyBubblesOpts = new BlockyBubblesOptionsStorage();
    public static final String MOD_ID = "blocky-bubbles";
    public static final String MOD_NAME = "Blocky Bubbles";

    /**
     * @return the default values of a new config file in the fabric mod directory if one doesn't exist, otherwise the configured settings.
     */
    public static BlockyBubblesGameOptions options() {
        if (CONFIG == null) {
            FabricLoader fabricLoader = FabricLoader.getInstance();
            CONFIG = BlockyBubblesGameOptions.load(fabricLoader.getConfigDir().resolve(MOD_ID + ".json").toFile());
        }
        return CONFIG;
    }

    public static Logger logger() {
        if (LOGGER == null) {
            LOGGER = Logger.getLogger(MOD_NAME);
        }

        return LOGGER;
    }

    /**
     * Defines the bubble column render layer, allowing it to show up when the "Bubble Columns" setting is set to fast.
     */
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BUBBLE_COLUMN, RenderLayer.getCutoutMipped());
    }

}
