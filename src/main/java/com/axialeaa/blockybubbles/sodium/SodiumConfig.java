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
public class SodiumConfig {

    public SodiumGameOptions.GraphicsQuality bubblesQuality;
    public SodiumCompat.CullingAwareness cullingAwareness;

    private File file;
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .excludeFieldsWithModifiers(Modifier.PRIVATE)
        .create();

    public static final SodiumConfig.Storage blockyBubblesOptions = new SodiumConfig.Storage();

    public static SodiumConfig getOptionData() {
        return SodiumConfig.blockyBubblesOptions.getData();
    }

    public static SodiumConfig loadFromFile(File file) {
        SodiumConfig config;

        if (file.exists())
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, SodiumConfig.class);
            }
            catch (Exception exception) {
                BlockyBubbles.LOGGER.warn("Falling back to defaults as the config could not be parsed.");
                config = new SodiumConfig();
            }
        else config = new SodiumConfig();

        config.file = file;
        config.writeToFile();

        return config;
    }

    public void writeToFile() {
        File parentFile = this.file.getParentFile();

        if (!parentFile.exists())
            if (!parentFile.mkdirs())
                throw new RuntimeException("Oops! Could not create parent directories.");
        else if (!parentFile.isDirectory())
            throw new RuntimeException("Oops! " + parentFile + " is not a directory.");

        try (FileWriter fileWriter = new FileWriter(this.file)) {
            GSON.toJson(this, fileWriter);
        }
        catch (IOException exception) {
            throw new RuntimeException("Oops! Configuration file could not be saved.", exception);
        }
    }

    public SodiumConfig() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.DEFAULT;
        this.cullingAwareness = SodiumCompat.CullingAwareness.NON_AIR;
    }

    public static class Storage implements OptionStorage<SodiumConfig> {

        private final SodiumConfig options = BlockyBubbles.options();

        @Override
        public SodiumConfig getData() {
            return this.options;
        }

        @Override
        public void save() {
            this.options.writeToFile();
        }

    }

}