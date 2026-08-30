package com.axialeaa.blockybubbles.mixin.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.*;
import com.axialeaa.blockybubbles.duck.QualityButtonHolder;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends OptionsSubScreen implements QualityButtonHolder {

	@Unique private final ReloadCallback reloadCallback = new ReloadCallback(this.minecraft);

    @Unique @Nullable private CycleButton<Quality> quality = null;
    @Unique @Nullable private CycleButton<Boolean> animations = null;
    @Unique @Nullable private CycleButton<Boolean> opaqueFaces = null;
    @Unique @Nullable private CycleButton<CullfaceMethod> topFaceCullingMethod = null;
	@Unique @Nullable private CycleButton<Boolean> biomeColors = null;
	@Unique @Nullable private CycleButton<ResourcePackStyle> resourcePackStyle = null;
	@Unique @Nullable private CycleButton<Boolean> frozenLibCompat = null;

    public VideoSettingsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @ModifyExpressionValue(method = "addOptions", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/options/VideoSettingsScreen;PREFERENCES_HEADER:Lnet/minecraft/network/chat/Component;", opcode = Opcodes.GETSTATIC))
    private Component addBlockyBubblesOptions(Component original) {
		if (this.list != null) {
			this.blocky_bubbles$applyButtonWidgets();
			this.blocky_bubbles$populateList();
		}

		return original;
	}

	@Inject(method = "onClose", at = @At("TAIL"))
	private void runCallback(CallbackInfo ci) {
		this.reloadCallback.run();
	}

	@Unique
	private void blocky_bubbles$applyButtonWidgets() {
		assert this.list != null;
		this.list.addHeader(Component.translatable("blocky-bubbles.mod_name"));

		BlockyBubblesConfig config = BlockyBubbles.getConfig();

		this.quality = ConfigHelper.createCyclingEnum(ConfigHelper.QUALITY, Quality.values(), false, config::setQuality, config::getQuality, (_, value) -> {
			this.reloadCallback.onQualityChange();

			Objects.requireNonNull(this.animations).active = value == Quality.FAST;
			Objects.requireNonNull(this.opaqueFaces).active = value == Quality.FAST;
			Objects.requireNonNull(this.topFaceCullingMethod).active = value == Quality.FAST;
			Objects.requireNonNull(this.biomeColors).active = value == Quality.FAST;
			Objects.requireNonNull(this.resourcePackStyle).active = value == Quality.FAST;

			this.options.setGraphicsPresetToCustom();
		});
		this.animations = ConfigHelper.createCyclingBoolean(ConfigHelper.ANIMATIONS, config::setAnimations, config::hasAnimations, this.reloadCallback.assets());
		this.opaqueFaces = ConfigHelper.createCyclingBoolean(ConfigHelper.OPAQUE_FACES, config::setOpaqueFaces, config::hasOpaqueFaces, this.reloadCallback.renderer());
		this.topFaceCullingMethod = ConfigHelper.createCyclingEnum(ConfigHelper.CULLFACE_METHOD, CullfaceMethod.values(), true, config::setCullfaceMethod, config::getCullfaceMethod, this.reloadCallback.renderer());
		this.biomeColors = ConfigHelper.createCyclingBoolean(ConfigHelper.BIOME_COLORS, config::setBiomeColors, config::hasBiomeColors, this.reloadCallback.assets());
		this.resourcePackStyle = ConfigHelper.createCyclingEnum(ConfigHelper.RESOURCE_PACK_STYLE, ResourcePackStyle.values(), true, config::setResourcePackStyle, config::getResourcePackStyle, this.reloadCallback.assets());

		this.frozenLibCompat = ConfigHelper.createCyclingBoolean(ConfigHelper.FROZENLIB_COMPAT, config::setFrozenLibCompat, config::hasFrozenLibCompat, this.reloadCallback.assets());
		this.frozenLibCompat.active = ConfigHelper.FROZENLIB_LOADED;
	}

	@Unique
	private void blocky_bubbles$populateList() {
		assert this.list != null;

		this.list.addBig(Objects.requireNonNull(this.quality));
		this.list.addSmall(List.of(
			Objects.requireNonNull(this.animations),
			Objects.requireNonNull(this.opaqueFaces),
			Objects.requireNonNull(this.topFaceCullingMethod),
			Objects.requireNonNull(this.biomeColors)
		));
		this.list.addSmall(Objects.requireNonNull(this.resourcePackStyle), this.frozenLibCompat);
	}

    @Override
    public @Nullable CycleButton<Quality> blocky_bubbles$get() {
        return this.quality;
    }

}
