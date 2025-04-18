package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.SodiumGameOptions;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.storage.OptionStorage;

/**
 * Mostly copied from <a href="https://github.com/FlashyReese/sodium-extra-fabric/blob/1.20.x/dev/src/main/java/me/flashyreese/mods/sodiumextra/client/gui/SodiumExtraGameOptions.java">Sodium Extra's game options class</a>, with a few exceptions.
 */
public class SodiumConfig {

    private static final Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();

    static final Storage STORAGE = new Storage();
    private File file;

    @Expose public SodiumGameOptions.GraphicsQuality bubblesQuality;
    @Expose public boolean animations;
    @Expose public boolean opaqueFaces;
    @Expose public TopFaceCullingMethod topFaceCullingMethod;

    public SodiumConfig() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.FAST;
        this.animations = true;
        this.opaqueFaces = false;
        this.topFaceCullingMethod = TopFaceCullingMethod.NON_AIR;
    }

    public static SodiumConfig getData() {
        return STORAGE.getData();
    }

    protected static SodiumConfig loadFromFile() {
        File configFile = getConfigFile();
        SodiumConfig config = parseOrCreate(configFile);

        config.file = configFile;
        config.writeToFile();

        return config;
    }

    private static File getConfigFile() {
        return BlockyBubbles.LOADER.getConfigDir().resolve(BlockyBubbles.MOD_ID + ".json").toFile();
    }

    private static SodiumConfig parseOrCreate(File configFile) {
        if (!configFile.exists())
            return new SodiumConfig();

        try (FileReader reader = new FileReader(configFile)) {
            return GSON.fromJson(reader, SodiumConfig.class);
        }
        catch (Exception e) {
            BlockyBubbles.LOGGER.warn("Falling back to defaults as the config could not be parsed.", e);
            return new SodiumConfig();
        }
    }

    protected void writeToFile() {
        this.validateDirectory();
        this.tryWriteFile();
    }

    private void validateDirectory() {
        File directory = this.file.getParentFile();

        if (!directory.exists()) {
            tryMakeDirectories(directory);
            return;
        }

        if (!directory.isDirectory())
            throw new RuntimeException(directory + " must be a directory!");
    }

    private static void tryMakeDirectories(File parentFile) {
        if (!parentFile.mkdirs())
            throw new RuntimeException("Failed to create parent directories!");
    }

    private void tryWriteFile() {
        try (FileWriter fileWriter = new FileWriter(this.file)) {
            GSON.toJson(this, fileWriter);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to save configuration file!", e);
        }
    }

    public static class Storage implements OptionStorage<SodiumConfig> {

        private static SodiumConfig config;

        @Override
        public SodiumConfig getData() {
            return this.getConfig();
        }

        @Override
        public void save() {
            this.getConfig().writeToFile();
        }

        public SodiumConfig getConfig() {
            if (config == null)
                config = SodiumConfig.loadFromFile();

            return config;
        }

    }

}