package com.axialeaa.blockybubbles.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.SodiumGameOptions;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.storage.OptionStorage;

/**
 * Mostly copied from <a href="https://github.com/FlashyReese/sodium-extra-fabric/blob/1.20.x/dev/src/main/java/me/flashyreese/mods/sodiumextra/client/gui/SodiumExtraGameOptions.java">Sodium Extra's game options class</a>, with a few exceptions.
 */
public class BlockyBubblesConfig {

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();

    @Expose public SodiumGameOptions.GraphicsQuality bubblesQuality;
    @Expose public boolean enableAnimations;
    @Expose public SodiumUtils.CullingAwareness cullingAwareness;

    public static final Storage STORAGE = new Storage();

    private File file;

    public static BlockyBubblesConfig getOptionData() {
        return STORAGE.getData();
    }

    public static BlockyBubblesConfig loadFromFile(File file) {
        BlockyBubblesConfig config;

        if (!file.exists())
            config = new BlockyBubblesConfig();
        else try (FileReader reader = new FileReader(file)) {
            config = GSON.fromJson(reader, BlockyBubblesConfig.class);

            if (config == null)
                throw new NullPointerException();
        }
        catch (Exception e) {
            BlockyBubbles.LOGGER.warn("Falling back to defaults as the config could not be parsed.", e);
            config = new BlockyBubblesConfig();
        }

        config.file = file;
        config.writeToFile();

        return config;
    }

    private void writeToFile() {
        File parentFile = this.file.getParentFile();

        if (!parentFile.exists()) {
            if (!parentFile.mkdirs())
                throw new RuntimeException("Failed to create parent directories!");
        }
        else if (!parentFile.isDirectory())
            throw new RuntimeException("%s must be a directory!".formatted(parentFile));

        try (FileWriter fileWriter = new FileWriter(this.file)) {
            GSON.toJson(this, fileWriter);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to save configuration file!", e);
        }
    }

    public BlockyBubblesConfig() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.DEFAULT;
        this.enableAnimations = true;
        this.cullingAwareness = SodiumUtils.CullingAwareness.NON_AIR;
    }

    public static class Storage implements OptionStorage<BlockyBubblesConfig> {

        private final BlockyBubblesConfig options = BlockyBubbles.getOptions();

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