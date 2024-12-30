package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.util.Identifiers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockyBubbles implements ClientModInitializer {

    public static final String MOD_ID = /*$ mod_id */ "blocky-bubbles";
    public static final String MOD_NAME = /*$ mod_name */ "Blocky Bubbles";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final FabricLoader LOADER = FabricLoader.getInstance();
    private static final ModContainer CONTAINER = LOADER.getModContainer(MOD_ID).orElseThrow(RuntimeException::new);

    @Override
    public void onInitializeClient() {
        registerPack(Identifiers.UPSCALE_PACK);
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BUBBLE_COLUMN, RenderLayer.getCutout());
    }

    public static Identifier id(String path) {
        return /*$ identifier*/ Identifier.of(MOD_ID, path);
    }

    private static void registerPack(Identifier id) {
        Text translated = Text.translatable("resourcePack.%s.name".formatted(id));
        ResourceManagerHelper.registerBuiltinResourcePack(id, CONTAINER, translated, ResourcePackActivationType.NORMAL);

        LOGGER.info("{} pack registered!", id);
    }

}