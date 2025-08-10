package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.util.RenderingUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * This mixin fixes a bug wherein sprinting on upward bubble columns created dust particles with weird textures.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow protected abstract BlockState getLandingBlockState();

    @ModifyExpressionValue(method = "shouldSpawnSprintingParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"))
    private boolean shouldIgnoreSprintingBlock(boolean original) {
        if (!RenderingUtils.isFancy())
            original |= this.getLandingBlockState().isOf(Blocks.BUBBLE_COLUMN);

        return original;
    }

}
