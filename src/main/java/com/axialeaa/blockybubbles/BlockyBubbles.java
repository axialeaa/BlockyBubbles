package com.axialeaa.blockybubbles;

import com.axialeaa.blockybubbles.sodium.SodiumConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
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

    public static boolean isSodiumLoaded = FabricLoader.getInstance().isModLoaded("sodium");
    private static SodiumConfig CONFIG;

    /**
     * @return the default values of a new config file in the fabric mod directory if one doesn't exist, otherwise the
     * configured settings.
     */
    public static SodiumConfig options() {
        if (CONFIG == null) {
            FabricLoader fabricLoader = FabricLoader.getInstance();
            CONFIG = SodiumConfig.loadFromFile(fabricLoader.getConfigDir().resolve(MOD_ID + ".json").toFile());
        }

        return CONFIG;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info(MOD_NAME + " initialized. Bubble trouble!");

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            //? if >1.17.1
            Text name = translate("resourcePack." + MOD_ID + ":32x_upscale.name");
            ResourceManagerHelper.registerBuiltinResourcePack(id("32x_upscale"), modContainer,
                //? if >1.17.1
                /*$ pack_name >>*/ name,
                ResourcePackActivationType.NORMAL);

            LOGGER.info("32x_upscale pack registered!");
        });

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BUBBLE_COLUMN, RenderLayer.getCutout());
    }

    public static Identifier id(String name) {
        return /*$ identifier*/ Identifier.of(MOD_ID, name);
    }

    public static Text translate(String key) {
        return /*$ translatable*/ Text.translatable(key);
    }

    public static Text getOptionText(String name) {
        return translate(MOD_ID + ".options." + name);
    }

    public static Text getOptionText(String name, boolean isTooltip) {
        return getOptionText(name + "." + (isTooltip ? "tooltip" : "name"));
    }

}