package com.axialeaa.blockybubbles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

//? if <=1.18.2
/*import net.minecraft.text.TranslatableText;*/

public class BlockyBubbles implements ClientModInitializer {

    public static final String MOD_ID = "blocky-bubbles";

    public static final FabricLoader LOADER = FabricLoader.getInstance();

    private static final ModContainer CONTAINER = LOADER.getModContainer(MOD_ID).orElseThrow(RuntimeException::new);
    private static final ModMetadata METADATA = CONTAINER.getMetadata();

    private static final String MOD_NAME = METADATA.getName();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final Function<String, Text> TRANSLATE_FUNCTION = /*$ translatable >>*/ Text::translatable ;

    @Override
    public void onInitializeClient() {
        registerPack("32x_upscale");
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BUBBLE_COLUMN, RenderLayer.getCutout());
    }

    /**
     * @return an identifier native to Blocky Bubbles.
     */
    private static Identifier id(String name) {
        return /*$ identifier*/ Identifier.of(MOD_ID, name);
    }

    /**
     * Registers a resource pack on the client and sends out an info message.
     * @param name the string identifier of the pack.
     */
    private static void registerPack(String name) {
        Identifier id = id(name);

        //? if >1.17.1
        Text translated = TRANSLATE_FUNCTION.apply("resourcePack.%s.name".formatted(id));

        ResourceManagerHelper.registerBuiltinResourcePack(id, CONTAINER,
            //? if >1.17.1
            /*$ pack_name >>*/ translated ,
            ResourcePackActivationType.NORMAL);

        LOGGER.info("{} pack registered!", id);
    }

}