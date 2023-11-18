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

/**
 * Mostly copied from <a href="https://github.com/FlashyReese/sodium-extra-fabric/blob/1.20.x/dev/src/main/java/me/flashyreese/mods/sodiumextra/client/gui/SodiumExtraGameOptions.java">Sodium Extra's game options class</a>, with a few exceptions.
 */
public class BlockyBubblesGameOptions {

    private File file;
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .excludeFieldsWithModifiers(Modifier.PRIVATE)
        .create();

    public static BlockyBubblesGameOptions load(File file) {
        BlockyBubblesGameOptions config;

        if (file.exists())
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, BlockyBubblesGameOptions.class);
            }
            catch (Exception exception) {
                BlockyBubbles.LOGGER.warning("Falling back to defaults as the config could not be parsed.");
                config = new BlockyBubblesGameOptions();
            }
        else config = new BlockyBubblesGameOptions();

        config.file = file;
        config.writeChanges();

        return config;
    }

    public void writeChanges() {
        File parentFile = this.file.getParentFile();

        if (!parentFile.exists())
            if (!parentFile.mkdirs())
                throw new RuntimeException("Oops! Could not create parent directories.");
        else if (!parentFile.isDirectory())
            throw new RuntimeException("Oops! " + parentFile + " is not a directory.");

        try (FileWriter fileWriter = new FileWriter(this.file)) {
            GSON.toJson(this, fileWriter);
        } catch (IOException exception) {
            throw new RuntimeException("Oops! Configuration file could not be saved.", exception);
        }
    }

    public SodiumGameOptions.GraphicsQuality bubblesQuality;

    public BlockyBubblesGameOptions() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.DEFAULT;
    }

}