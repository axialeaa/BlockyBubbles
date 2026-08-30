package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.config.resource_condition.BiomeColorsResourceCondition;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.config.resource_condition.ResourcePackStyleCondition;
import com.axialeaa.blockybubbles.tint.BubbleColumnTintSource;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlockyBubbles implements ClientModInitializer {

    public static final String MOD_ID = "blocky-bubbles";
    public static final String MOD_NAME = "Blocky Bubbles";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final FabricLoader LOADER = FabricLoader.getInstance();

	@Nullable private static BlockyBubblesConfig config = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("{} initialized! The bubbles were told \"be there or be square\" and they were not there...", MOD_NAME);

		registerResourceConditions();
		BlockColorRegistry.register(List.of(new BubbleColumnTintSource()), Blocks.BUBBLE_COLUMN);
	}

	private static void registerResourceConditions() {
		ResourceConditions.register(BiomeColorsResourceCondition.TYPE);
		ResourceConditions.register(ResourcePackStyleCondition.TYPE);
	}

    public static BlockyBubblesConfig getConfig() {
        if (config == null)
            config = BlockyBubblesConfig.loadFromFile();

        return config;
    }

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}