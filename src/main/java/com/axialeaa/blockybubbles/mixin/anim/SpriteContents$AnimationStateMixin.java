package com.axialeaa.blockybubbles.mixin.anim;

import com.axialeaa.blockybubbles.duck.ConditionalAnimatedTexture;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpriteContents.AnimationState.class)
public class SpriteContents$AnimationStateMixin {

	@Shadow @Final private SpriteContents.AnimatedTexture animationInfo;

	@WrapMethod(method = "tick")
	private void tickIfAnimatable(Operation<Void> original) {
		//noinspection CastToIncompatibleInterface
		if (((ConditionalAnimatedTexture) this.animationInfo).blockyBubbles$canAnimate())
			original.call();
	}

}