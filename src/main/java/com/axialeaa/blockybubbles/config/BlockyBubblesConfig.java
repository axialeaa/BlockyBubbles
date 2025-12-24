package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class BlockyBubblesConfig {

    private static final Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();

    @Expose @Nullable private Quality quality = Quality.FAST;
    @Expose @Nullable private Boolean animations = true;
    @Expose @Nullable private Boolean opaqueFaces = false;
    @Expose @Nullable private CullfaceMethod cullfaceMethod = CullfaceMethod.NON_AIR;

    @Nullable private File file;

    public static BlockyBubblesConfig loadFromFile() {
        File configFile = createFile();
        BlockyBubblesConfig config = parseOrCreate(configFile);

        config.file = configFile;
        config.writeToFile();

        return config;
    }

    private static File createFile() {
        return BlockyBubbles.LOADER.getConfigDir().resolve(BlockyBubbles.MOD_ID + ".json").toFile();
    }

    private static BlockyBubblesConfig parseOrCreate(File configFile) {
        if (!configFile.exists())
            return new BlockyBubblesConfig();

        try (FileReader reader = new FileReader(configFile)) {
            return Objects.requireNonNullElse(GSON.fromJson(reader, BlockyBubblesConfig.class), new BlockyBubblesConfig());
        }
        catch (Exception e) {
            BlockyBubbles.LOGGER.warn("Falling back to defaults as the config could not be parsed.", e);
            return new BlockyBubblesConfig();
        }
    }

    public void writeToFile() {
        this.validateDirectory();
        this.tryWriteFile();
    }

    private void validateDirectory() {
        if (this.file == null)
            throw new RuntimeException("Config file not found!");

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
        assert this.file != null; // checked in validateDirectories

        try (FileWriter fileWriter = new FileWriter(this.file)) {
            GSON.toJson(this, fileWriter);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to save configuration file!", e);
        }
    }

    public boolean isFancy() {
        return this.getQuality() == Quality.FANCY;
    }

    public void setAnimations(boolean animations) {
        this.animations = animations;
    }

    public void setOpaqueFaces(boolean opaqueFaces) {
        this.opaqueFaces = opaqueFaces;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public void setCullfaceMethod(CullfaceMethod cullfaceMethod) {
        this.cullfaceMethod = cullfaceMethod;
    }

    public boolean hasAnimations() {
        return Objects.requireNonNullElse(this.animations, true);
    }

    public boolean hasOpaqueFaces() {
        return Objects.requireNonNullElse(this.opaqueFaces, false);
    }

    public Quality getQuality() {
        return Objects.requireNonNullElse(this.quality, Quality.FAST);
    }

    public CullfaceMethod getCullfaceMethod() {
        return Objects.requireNonNullElse(this.cullfaceMethod, CullfaceMethod.NON_AIR);
    }

}
