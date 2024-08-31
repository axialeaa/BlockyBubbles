package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.sodium.BlockyBubblesConfig;
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

//? if <=1.18.2
/*import net.minecraft.text.TranslatableText;*/

public class BlockyBubbles implements ClientModInitializer {

    public static final String MOD_ID = "blocky-bubbles";
    public static final String MOD_NAME = "Blocky Bubbles";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static final FabricLoader loader = FabricLoader.getInstance();
    public static boolean isSodiumLoaded = loader.isModLoaded("sodium");

    private static BlockyBubblesConfig CONFIG;

    /**
     * @return the default values of a new config file in the fabric mod directory if one doesn't exist, otherwise the
     * configured settings.
     */
    public static BlockyBubblesConfig options() {
        if (CONFIG == null)
            CONFIG = BlockyBubblesConfig.loadFromFile(loader.getConfigDir().resolve("%s.json".formatted(MOD_ID)).toFile());

        return CONFIG;
    }

    @Override
    public void onInitializeClient() {
        loader.getModContainer(MOD_ID).ifPresent(modContainer -> registerPack(modContainer, "32x_upscale"));
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
     * An alternative implementation of {@link BlockyBubbles#getOptionText(String)} used to distinguish between tooltips and option names. Saves repeating ".name" or ".tooltip".
     * @param id the id of the option.
     * @param isTooltip whether the text should be considered a tooltip.
     * @return a translated text component specific to a Blocky Bubbles config option.
     */
    public static Text getOptionText(String id, boolean isTooltip) {
        return getOptionText("%s.%s".formatted(id, isTooltip ? "tooltip" : "name"));
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

        LOGGER.info("{} pack registered!", name);
    }

}