package com.axialeaa.deepslatedeco.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.DungeonFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DungeonFeature.class)
public class DungeonFeatureMixin {

	@Unique
	private Block replaceFrom(BlockPos pos, Block block) {
		return pos.getY() <= 0 ? Blocks.COBBLED_DEEPSLATE : block;
	}

	@Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;COBBLESTONE:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC))
	private Block replaceCobblestone(@Local(ordinal = 0)BlockPos blockPos) {
		return replaceFrom(blockPos, Blocks.COBBLESTONE);
	}

	@Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;MOSSY_COBBLESTONE:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC))
	private Block replaceMossyCobblestone(@Local(ordinal = 0)BlockPos blockPos) {
		return replaceFrom(blockPos, Blocks.MOSSY_COBBLESTONE);
	}

}