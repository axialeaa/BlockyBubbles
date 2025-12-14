package com.axialeaa.blockybubbles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * This mixin fixes a bug wherein sprinting on upward bubble columns created dust particles with weird textures.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow protected abstract BlockState getBlockStateOnLegacy();

    @ModifyExpressionValue(method = "canSpawnSprintParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
    private boolean shouldIgnoreSprintingBlock(boolean original) {
        return original || this.getBlockStateOnLegacy().is(Blocks.BUBBLE_COLUMN);
    }

}
