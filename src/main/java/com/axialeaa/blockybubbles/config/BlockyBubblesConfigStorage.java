package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.caffeinemc.mods.sodium.api.config.StorageEventHandler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BlockyBubblesConfigStorage implements StorageEventHandler {

    private static final Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();

    @Expose public BubblesQuality quality = BubblesQuality.FAST;
    @Expose public boolean animations = true;
    @Expose public boolean opaqueFaces = false;
    @Expose public TopFaceCullingMethod topFaceCullingMethod = TopFaceCullingMethod.NON_AIR;

    private File file;

    public static BlockyBubblesConfigStorage loadFromFile() {
        File configFile = getConfigFile();
        BlockyBubblesConfigStorage config = parseOrCreate(configFile);

        config.file = configFile;
        config.afterSave();

        return config;
    }

    private static File getConfigFile() {
        return BlockyBubbles.LOADER.getConfigDir().resolve(BlockyBubbles.MOD_ID + ".json").toFile();
    }

    private static BlockyBubblesConfigStorage parseOrCreate(File configFile) {
        if (!configFile.exists())
            return new BlockyBubblesConfigStorage();

        try (FileReader reader = new FileReader(configFile)) {
            return GSON.fromJson(reader, BlockyBubblesConfigStorage.class);
        }
        catch (Exception e) {
            BlockyBubbles.LOGGER.warn("Falling back to defaults as the config could not be parsed.", e);
            return new BlockyBubblesConfigStorage();
        }
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

    public boolean isFancy() {
        return this.quality == BubblesQuality.FANCY;
    }

    @Override
    public void afterSave() {
        this.validateDirectory();
        this.tryWriteFile();
    }

}
