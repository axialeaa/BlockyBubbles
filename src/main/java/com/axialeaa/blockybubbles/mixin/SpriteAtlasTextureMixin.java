package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.util.RenderingUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin {

    @WrapWithCondition(method = "upload", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1))
    private boolean shouldAnimateTexture(List<Sprite.TickableAnimation> instance, Object object, @Local Sprite sprite) {
        if (RenderingUtils.shouldAnimate())
            return true;

        String namespace = sprite.getContents().getId().getNamespace();

        return !namespace.equals(BlockyBubbles.MOD_ID);
    }

}