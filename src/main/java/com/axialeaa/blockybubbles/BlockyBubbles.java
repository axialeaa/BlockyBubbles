package com.axialeaa.blockybubbles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.block.BlockState;
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
    public static final String MOD_NAME;
    public static final Logger LOGGER;

    public static final FabricLoader LOADER = FabricLoader.getInstance();

    /**
     * True if sodium is installed on the client. Do not call this from the mixin plugin; it will throw a preloading error.
     */
    public static boolean isSodiumLoaded = LOADER.isModLoaded("sodium");
    public static final Function<BlockState, Boolean> defaultCullingFunction = state -> !state.isAir();

    static {
        ModMetadata metadata = LOADER.getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();

        MOD_NAME = metadata.getName();
        LOGGER = LoggerFactory.getLogger(MOD_NAME);
    }

    @Override
    public void onInitializeClient() {
        LOADER.getModContainer(MOD_ID).ifPresent(modContainer -> registerPack(modContainer, "32x_upscale"));
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BUBBLE_COLUMN, RenderLayer.getCutout());
    }

    /**
     * @return an identifier native to Blocky Bubbles.
     */
    public static Identifier id(String name) {
        return /*$ identifier*/ Identifier.of(MOD_ID, name);
    }

    /**
     * @return a translated text component, referring to the client's set language file with {@code key}.
     */
    public static Text translate(String key) {
        return /*$ translatable*/ Text.translatable(key);
    }

    /**
     * @param id the id of the option.
     * @return a translated text component specific to a Blocky Bubbles config option.
     */
    public static Text getOptionText(String id) {
        return translate("%s.options.%s".formatted(MOD_ID, id));
    }

    /**
     * An alternative implementation of {@link BlockyBubbles#getOptionText(String)} used to add suffixes without repeating confusing formatting.
     * @param id the id of the option.
     * @param suffix the suffix to append to the option text.
     * @return a translated text component specific to a Blocky Bubbles config option.
     */
    public static Text getOptionTextWithSuffix(String id, String suffix) {
        return getOptionText("%s.%s".formatted(id, suffix));
    }

    /**
     * Registers a resource pack on the client and sends out an info message.
     * @param id the string identifier of the pack.
     */
    private static void registerPack(ModContainer modContainer, String id) {
        Text name = translate("resourcePack.%s:%s.name".formatted(MOD_ID, id));
        ResourceManagerHelper.registerBuiltinResourcePack(id(id), modContainer,
            //? if >1.17.1
            /*$ pack_name >>*/ name ,
            ResourcePackActivationType.NORMAL);

        LOGGER.info("{} pack registered!", id);
    }

}