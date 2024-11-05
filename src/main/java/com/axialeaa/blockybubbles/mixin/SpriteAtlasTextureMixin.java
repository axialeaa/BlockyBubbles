package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.util.BlockyBubblesUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

//? if <=1.19.2
/*import net.minecraft.client.texture.TextureTickListener;*/

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin {

    @WrapWithCondition(method = "upload", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z" /*? if >=1.19.3 >>*/ ,ordinal=1 ))
    private boolean shouldAnimateTexture(List< /*$ list_type >>*/ Sprite.TickableAnimation > instance, Object object, @Local Sprite sprite) {
        if (BlockyBubblesUtils.shouldAnimate())
            return true;

        String namespace = sprite /*? if >=1.19.3 >>*/ .getContents() .getId().getNamespace();

        return !namespace.equals(BlockyBubbles.MOD_ID);
    }

}