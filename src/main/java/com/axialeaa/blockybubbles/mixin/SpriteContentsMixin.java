package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpriteContents.class)
public abstract class SpriteContentsMixin {

    @Shadow public abstract Identifier name();

    @WrapMethod(method = "createAnimatedTexture")
    private SpriteContents.AnimatedTexture shouldAnimateTexture(FrameSize frameSize, int i, int j, AnimationMetadataSection animationMetadataSection, Operation<SpriteContents.AnimatedTexture> original) {
        if (this.name().getNamespace().equals(BlockyBubbles.MOD_ID) && !BlockyBubbles.getConfig().hasAnimations())
            return null;

        return original.call(frameSize, i, j, animationMetadataSection);
    }

}