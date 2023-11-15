package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class BlockyBubblesGameOptions {

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .excludeFieldsWithModifiers(Modifier.PRIVATE)
        .create();
    private File file;

    public static BlockyBubblesGameOptions load(File file) {
        BlockyBubblesGameOptions config;

        if (file.exists())
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, BlockyBubblesGameOptions.class);
            }
            catch (Exception e) {
                BlockyBubbles.LOGGER.warning("Falling back to defaults as the config could not be parsed.");
                config = new BlockyBubblesGameOptions();
            }
        else config = new BlockyBubblesGameOptions();

        config.file = file;
        config.writeChanges();

        return config;
    }

    public void writeChanges() {
        File dir = this.file.getParentFile();

        if (!dir.exists())
            if (!dir.mkdirs())
                throw new RuntimeException("Could not create parent directories.");
        else if (!dir.isDirectory())
            throw new RuntimeException(dir + " is not a directory.");

        try (FileWriter writer = new FileWriter(this.file)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Configuration file could not be saved.", e);
        }
    }

    public SodiumGameOptions.GraphicsQuality bubblesQuality;

    public BlockyBubblesGameOptions() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.DEFAULT;
    }

}