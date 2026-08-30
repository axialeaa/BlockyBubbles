package com.axialeaa.blockybubbles.compat.sodium;

import com.axialeaa.blockybubbles.config.*;
import net.caffeinemc.mods.sodium.api.config.ConfigState;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.option.OptionImpact;
import net.caffeinemc.mods.sodium.api.config.structure.BooleanOptionBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.EnumOptionBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.axialeaa.blockybubbles.config.ConfigHelper.*;

public final class SodiumOptionBuilders {

	private SodiumOptionBuilders() {}

	static EnumOptionBuilder<Quality> quality(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<EnumOptionBuilder<Quality>> operator = optionBuilder -> optionBuilder
			.setFlags(FROZENLIB_LOADED ? OptionFlag.REQUIRES_ASSET_RELOAD : OptionFlag.REQUIRES_RENDERER_RELOAD)
			.setImpact(OptionImpact.MEDIUM)
			.setDefaultValue(Quality.FAST)
			.setTooltip(_ -> {
				String suffix = ".tooltip";

				if (FROZENLIB_LOADED)
					suffix += ".asset_reload";

				return optionText(QUALITY, suffix);
			});

		return enumBuilder(configBuilder, config, QUALITY, Quality.class, config::setQuality, config::getQuality, operator);
	}

	static BooleanOptionBuilder animations(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<BooleanOptionBuilder> operator = optionBuilder -> optionBuilder
			.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
			.setImpact(OptionImpact.MEDIUM)
			.setDefaultValue(false)
			.setEnabledProvider(SodiumOptionBuilders::isFast, QUALITY);

		return booleanBuilder(configBuilder, config, ANIMATIONS, config::setAnimations, config::hasAnimations, operator);
	}

	static BooleanOptionBuilder opaqueFaces(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<BooleanOptionBuilder> operator = optionBuilder -> optionBuilder
			.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
			.setImpact(OptionImpact.MEDIUM)
			.setDefaultValue(false)
			.setEnabledProvider(SodiumOptionBuilders::isFast, QUALITY);

		return booleanBuilder(configBuilder, config, OPAQUE_FACES, config::setOpaqueFaces, config::hasOpaqueFaces, operator);
	}

	static EnumOptionBuilder<CullfaceMethod> cullfaceMethod(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<EnumOptionBuilder<CullfaceMethod>> operator = optionBuilder -> optionBuilder
			.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
			.setImpact(OptionImpact.LOW)
			.setDefaultValue(CullfaceMethod.NON_AIR)
			.setEnabledProvider(SodiumOptionBuilders::isFast, QUALITY);

		return enumBuilder(configBuilder, config, CULLFACE_METHOD, CullfaceMethod.class, config::setCullfaceMethod, config::getCullfaceMethod, operator);
	}

	static BooleanOptionBuilder biomeColors(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<BooleanOptionBuilder> operator = optionBuilder -> optionBuilder
			.setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
			.setDefaultValue(false)
			.setEnabledProvider(SodiumOptionBuilders::isFast, QUALITY);

		return booleanBuilder(configBuilder, config, BIOME_COLORS, config::setBiomeColors, config::hasBiomeColors, operator);
	}

	static EnumOptionBuilder<ResourcePackStyle> resourcePackStyle(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<EnumOptionBuilder<ResourcePackStyle>> operator = optionBuilder -> optionBuilder
			.setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
			.setDefaultValue(ResourcePackStyle.VANILLA)
			.setEnabledProvider(SodiumOptionBuilders::isFast, QUALITY);

		return enumBuilder(configBuilder, config, RESOURCE_PACK_STYLE, ResourcePackStyle.class, config::setResourcePackStyle, config::getResourcePackStyle, operator);
	}

	static BooleanOptionBuilder frozenLibCompat(ConfigBuilder configBuilder, BlockyBubblesConfig config) {
		UnaryOperator<BooleanOptionBuilder> operator = optionBuilder -> optionBuilder
			.setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
			.setDefaultValue(false)
			.setEnabled(FROZENLIB_LOADED);

		return booleanBuilder(configBuilder, config, FROZENLIB_COMPAT, config::setFrozenLibCompat, config::hasFrozenLibCompat, operator);
	}

	private static boolean isFast(ConfigState configState) {
		return configState.readEnumOption(QUALITY, Quality.class) == Quality.FAST;
	}

	private static BooleanOptionBuilder booleanBuilder(ConfigBuilder configBuilder, BlockyBubblesConfig config, Identifier id, Consumer<Boolean> setter, Supplier<Boolean> getter, UnaryOperator<BooleanOptionBuilder> optionBuilder) {
		BooleanOptionBuilder booleanOption = configBuilder.createBooleanOption(id)
			.setName(optionText(id))
			.setTooltip(optionTooltip(id))
			.setStorageHandler(config::writeToFile)
			.setBinding(setter, getter);

		return optionBuilder.apply(booleanOption);
	}

	private static <T extends Enum<T> & StringRepresentable> EnumOptionBuilder<T> enumBuilder(ConfigBuilder configBuilder, BlockyBubblesConfig config, Identifier id, Class<T> enumClass, Consumer<T> setter, Supplier<T> getter, UnaryOperator<EnumOptionBuilder<T>> optionBuilder) {
		EnumOptionBuilder<T> enumOption = configBuilder.createEnumOption(id, enumClass)
			.setName(optionText(id))
			.setElementNameProvider(enumOptionNameProvider(id)::apply)
			.setTooltip(enumOptionTooltipProvider(id)::apply)
			.setStorageHandler(config::writeToFile)
			.setBinding(setter, getter);

		return optionBuilder.apply(enumOption);
	}

}