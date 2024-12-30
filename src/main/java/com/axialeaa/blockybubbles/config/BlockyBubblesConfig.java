package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.util.BlockyBubblesUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.SodiumGameOptions;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.*;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.control.*;

/**
 * Mostly copied from <a href="https://github.com/FlashyReese/sodium-extra-fabric/blob/1.20.x/dev/src/main/java/me/flashyreese/mods/sodiumextra/client/gui/SodiumExtraGameOptions.java">Sodium Extra's game options class</a>, with a few exceptions.
 */
public class BlockyBubblesConfig {

    static {
        if (!BlockyBubblesUtils.isSodiumLoaded)
            throw new RuntimeException("BlockyBubblesConfig class loaded without sodium on the client!");
    }

    private static final String FILE_NAME = BlockyBubbles.MOD_ID + ".json";

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();

    private static final BlockyBubblesOptionStorage STORAGE = new BlockyBubblesOptionStorage();

    @Expose public SodiumGameOptions.GraphicsQuality bubblesQuality;
    @Expose public boolean enableAnimations;
    @Expose public CullingMode cullingMode;

    private File file;

    public BlockyBubblesConfig() {
        this.bubblesQuality = SodiumGameOptions.GraphicsQuality.FAST;
        this.enableAnimations = true;
        this.cullingMode = CullingMode.NON_AIR;
    }

    public static BlockyBubblesConfig getData() {
        return STORAGE.getData();
    }

    public static BlockyBubblesConfig loadFromFile() {
        File file = BlockyBubbles.LOADER.getConfigDir().resolve(FILE_NAME).toFile();
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

    void writeToFile() {
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

    public static Text getOptionText(String path) {
        return Text.translatable(BlockyBubbles.MOD_ID + ".options." + path);
    }

    public static Text getOptionText(String path, boolean tooltip) {
        return getOptionText(path + "." + (tooltip ? "tooltip" : "name"));
    }

    private static <T> OptionImpl<BlockyBubblesConfig, T> createOption(String name, Class<T> type, Function<OptionImpl<BlockyBubblesConfig, T>, Control<T>> controlFunction, BiConsumer<BlockyBubblesConfig, T> setter, Function<BlockyBubblesConfig, T> getter, OptionImpact impact, OptionFlag... optionFlags) {
        OptionImpl.Builder<BlockyBubblesConfig, T> builder = OptionImpl.createBuilder(type, BlockyBubblesConfig.STORAGE)
            .setName(getOptionText(name, false))
            .setTooltip(getOptionText(name, true))
            .setControl(controlFunction)
            .setBinding(setter, getter)
            .setImpact(impact);

        if (optionFlags.length > 0)
            builder.setFlags(optionFlags);

        return builder.build();
    }

    public static OptionGroup createOptionGroup() {
        OptionGroup.Builder builder = OptionGroup.createBuilder();

        builder.add(createOption(
            "quality",
            SodiumGameOptions.GraphicsQuality.class,
            option -> new CyclingControl<>(option, SodiumGameOptions.GraphicsQuality.class),
            (config, value) -> config.bubblesQuality = value, config -> config.bubblesQuality,
            OptionImpact.MEDIUM,
            OptionFlag.REQUIRES_RENDERER_RELOAD
        ));

        builder.add(createOption(
            "enable_animations",
            Boolean.class,
            TickBoxControl::new,
            (config, value) -> config.enableAnimations = value, config -> config.enableAnimations,
            OptionImpact.MEDIUM,
            OptionFlag.REQUIRES_ASSET_RELOAD
        ));

        builder.add(createOption(
            CullingMode.NAME,
            CullingMode.class,
            option -> new CyclingControl<>(option, CullingMode.class),
            (config, value) -> config.cullingMode = value, config -> config.cullingMode,
            OptionImpact.LOW,
            OptionFlag.REQUIRES_RENDERER_RELOAD
        ));

        return builder.build();
    }

}