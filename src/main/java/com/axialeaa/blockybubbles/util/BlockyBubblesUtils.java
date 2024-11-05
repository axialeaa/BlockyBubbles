package com.axialeaa.blockybubbles.util;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.config.CullingMode;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Predicate;

public class BlockyBubblesUtils {

    /**
     * True if config is installed on the client.
     * Do not call this from the mixin plugin; it will throw a preloading error.
     */
    public static boolean isSodiumLoaded = BlockyBubbles.LOADER.isModLoaded("sodium");

    public static final Predicate<BlockState> DEFAULT_CULLING_PREDICATE = state -> !state.isAir();

    /**
     * @return true if the "Bubble Columns" setting is fancy or higher when sodium is installed, otherwise true if the global graphics setting is fancy or higher.
     */
    public static boolean isFancy() {
        if (!isSodiumLoaded)
            return MinecraftClient.isFancyGraphicsOrBetter();

        MinecraftClient client = MinecraftClient.getInstance();
        GraphicsMode graphicsMode = client.options /*$ graphics_mode >>*/ .getGraphicsMode().getValue() ;

        return BlockyBubblesConfig.getData().bubblesQuality.isFancy(graphicsMode);
    }

    /**
     * @return true if the "Enable Animations" setting is enabled or sodium is not installed.
     */
    public static boolean shouldAnimate() {
        if (!isSodiumLoaded)
            return true;

        return BlockyBubblesConfig.getData().enableAnimations;
    }

    /**
     * @param state The block next to the bubble column.
     * @param direction The facing direction of the block face to cull.
     * @return true if the "Culling" setting allows culling when sodium is installed, otherwise true if the block next to the bubble column in question is not air.
     * @see CullingMode
     */
    public static boolean shouldCull(BlockState state, Direction direction) {
        if (!isSodiumLoaded)
            return DEFAULT_CULLING_PREDICATE.test(state);

        MinecraftClient client = MinecraftClient.getInstance();

        ClientWorld world = client.world;
        BlockPos blockPos = BlockPos.ORIGIN;

        return BlockyBubblesConfig.getData().cullingMode.shouldCull(world, blockPos, state, direction);
    }

}
