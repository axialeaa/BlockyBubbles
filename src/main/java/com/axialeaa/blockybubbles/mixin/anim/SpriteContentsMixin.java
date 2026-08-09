package com.axialeaa.blockybubbles.mixin.anim;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.duck.ConditionalAnimatedTexture;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpriteContents.class)
public abstract class SpriteContentsMixin {

    @Shadow public abstract Identifier name();

    @ModifyExpressionValue(method = "createAnimatedTexture", at = @At(value = "NEW", target = "(Lnet/minecraft/client/renderer/texture/SpriteContents;Ljava/util/List;IZ)Lnet/minecraft/client/renderer/texture/SpriteContents$AnimatedTexture;"))
    private SpriteContents.AnimatedTexture markAnimatable(SpriteContents.AnimatedTexture original) {
		//noinspection CastToIncompatibleInterface
		((ConditionalAnimatedTexture) original).blockyBubbles$setCanAnimate(!this.name().getNamespace().equals(BlockyBubbles.MOD_ID) || BlockyBubbles.getConfig().hasAnimations());
        return original;
    }

}