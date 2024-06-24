package com.axialeaa.blockybubbles.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;

/**
 * Mostly copied from <a href="https://github.com/FlashyReese/sodium-extra-fabric/blob/1.20.x/dev/src/main/java/me/flashyreese/mods/sodiumextra/client/gui/SodiumExtraGameOptions.java">Sodium Extra's game options class</a>, with a few exceptions.
 */
public class BlockyBubblesConfig {

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .excludeFieldsWithModifiers(Modifier.PRIVATE)
        .create();

    public static final BlockyBubblesConfig.Storage STORAGE = new BlockyBubblesConfig.Storage();

    public SodiumGameOptions.GraphicsQuality bubblesQuality;
    public boolean enableAnimations;
    public SodiumUtils.CullingAwareness cullingAwareness;

    private File file;

    public static BlockyBubblesConfig getOptionData() {
        return STORAGE.getData();
    }

    public static BlockyBubblesConfig loadFromFile(File file) {
        BlockyBubblesConfig config;

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, BlockyBubblesConfig.class);
                if (config == null)
                    throw new Exception();
            }
            catch (Exception exception) {
                BlockyBubbles.LOGGER.warn("Falling back to defaults as the config could not be parsed.");
                config = new BlockyBubblesConfig();
            }
        }
        else config = new BlockyBubblesConfig();

        config.file = file;
        config.writeToFile();

        return config;
    }

    private void writeToFile() {
        File parentFile = this.file.getParentFile();

        if (!parentFile.exists()) {
            if (!parentFile.mkdirs())
                throw new RuntimeException("Failed to create parent directories.");
        }
        else if (!parentFile.isDirectory())
            throw new RuntimeException(parentFile + " must be a directory.");

        try (FileWriter fileWriter = new FileWriter(this.file)) {
            GSON.toJson(this, fileWriter);
        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to save configuration file.", exception);
        }
    }

    public BlockyBubblesConfig() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.DEFAULT;
        this.enableAnimations = true;
        this.cullingAwareness = SodiumUtils.CullingAwareness.NON_AIR;
    }

    public static class Storage implements OptionStorage<BlockyBubblesConfig> {

        private final BlockyBubblesConfig options = BlockyBubbles.options();

        @Override
        public BlockyBubblesConfig getData() {
            return this.options;
        }

        @Override
        public void save() {
            this.options.writeToFile();
        }

    }

}