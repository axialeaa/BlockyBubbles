package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockyBubbles implements ClientModInitializer {

    public static final String MOD_ID = "blocky-bubbles";
    public static final String MOD_NAME = "Blocky Bubbles";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final FabricLoader LOADER = FabricLoader.getInstance();

    private static BlockyBubblesConfig config = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("{} initialized! The bubbles were told \"be there or be square\" and they were not there...", MOD_NAME);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static BlockyBubblesConfig getConfig() {
        if (config == null)
            config = BlockyBubblesConfig.loadFromFile();

        return config;
    }

}