package com.axialeaa.blockybubbles.mixin.anim;

import com.axialeaa.blockybubbles.duck.ConditionalAnimatedTexture;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SpriteContents.AnimatedTexture.class)
public class SpriteContents$AnimatedTextureMixin implements ConditionalAnimatedTexture {

	@Unique private boolean blockyBubbles$canAnimate = true;

	@Override
	public boolean blockyBubbles$canAnimate() {
		return this.blockyBubbles$canAnimate;
	}

	@Override
	public void blockyBubbles$setCanAnimate(boolean canAnimate) {
		this.blockyBubbles$canAnimate = canAnimate;
	}

}