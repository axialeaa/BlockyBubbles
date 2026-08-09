package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.tint.BubbleColumnTintSource;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

	public static final boolean FROZENLIB_LOADED = LOADER.isModLoaded("frozenlib");

	@Nullable private static BlockyBubblesConfig config = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("{} initialized! The bubbles were told \"be there or be square\" and they were not there...", MOD_NAME);
		BlockColorRegistry.register(List.of(new BubbleColumnTintSource()), Blocks.BUBBLE_COLUMN);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static BlockyBubblesConfig getConfig() {
        if (config == null)
            config = BlockyBubblesConfig.loadFromFile();

        return config;
    }

	private static void registerPack(String path) {
		MutableComponent name = Component.translatable("resourcePack.blocky_bubbles.%s.name".formatted(path));
		ResourceLoader.registerBuiltinPack(id(path), LOADER.getModContainer(MOD_ID).orElseThrow(), name, PackActivationType.NORMAL);
	}

}