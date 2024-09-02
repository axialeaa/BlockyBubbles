package com.axialeaa.blockybubbles.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.mojang.datafixers.util.Function4;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.nio.file.Path;
import java.util.Locale;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.TextProvider;

/**
 * aaaaaaaaaaaaaaaaa
 * @author axia
 */
public class SodiumUtils {

    public static final String quality = "quality";
    public static final String enable_animations = "enable_animations";
    public static final String culling_mode = "culling_mode";

    private static BlockyBubblesConfig CONFIG;

    static {
        if (!BlockyBubbles.isSodiumLoaded)
            throw new RuntimeException("SodiumUtils class loaded without sodium on the client!");
    }

    public static boolean isFancy(GraphicsMode graphicsMode) {
        return BlockyBubblesConfig.getOptionData().bubblesQuality.isFancy(graphicsMode);
    }

    /**
     * @return the default values of a new config file in the fabric mod directory if one doesn't exist, otherwise the
     * configured settings.
     */
    public static BlockyBubblesConfig getOptions() {
        if (CONFIG == null) {
            Path configDir = BlockyBubbles.LOADER.getConfigDir();
            CONFIG = BlockyBubblesConfig.loadFromFile(configDir.resolve("%s.json".formatted(BlockyBubbles.MOD_ID)).toFile());
        }

        return CONFIG;
    }

    /**
     * Defines culling behaviour and provides an enumerator for the config to use.
     */
    @SuppressWarnings("unused")
    public enum CullingMode implements TextProvider {

        /**
         * Culls the bubble column face when the adjacent block is anything other than air, including water, glass and other bubble columns. This matches the behaviour of vanilla Bedrock Edition.
         */
        NON_AIR         ((world, pos, state, direction) -> BlockyBubbles.defaultCullingFunction.apply(state)),
        /**
         * Culls the bubble column face when the adjacent block is a bubble column or a full, solid, square side of an opaque block. This resembles how Java handles face culling on most other blocks.
         */
        BUBBLE_COLUMN   ((world, pos, state, direction) -> state.getBlock() instanceof BubbleColumnBlock || (state.isSideSolidFullSquare(world, pos, direction.getOpposite()) && state.isOpaque())),
        /**
         * Never culls the bubble column face under any circumstance.
         */
        NONE            ((world, pos, state, direction) -> false);

        private final Function4<ClientWorld, BlockPos, BlockState, Direction, Boolean> function;

        CullingMode(Function4<ClientWorld, BlockPos, BlockState, Direction, Boolean> function) {
            this.function = function;
        }

        @Override
        public Text getLocalizedName() {
            return BlockyBubbles.getOptionTextWithSuffix(culling_mode, "enum.%s".formatted(this.name().toLowerCase(Locale.ROOT)));
        }

        public boolean shouldCull(ClientWorld world, BlockPos pos, BlockState state, Direction direction) {
            return this.function.apply(world, pos, state, direction);
        }

    }

}