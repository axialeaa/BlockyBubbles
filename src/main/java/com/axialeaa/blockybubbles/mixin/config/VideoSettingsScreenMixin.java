package com.axialeaa.blockybubbles.mixin.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.config.Quality;
import com.axialeaa.blockybubbles.config.CullfaceMethod;
import com.axialeaa.blockybubbles.config.duck.QualityButtonHolder;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static com.axialeaa.blockybubbles.config.ConfigHelper.*;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends OptionsSubScreen implements QualityButtonHolder {

    @Unique private CycleButton<Quality> quality;
    @Unique private CycleButton<Boolean> animations;
    @Unique private CycleButton<Boolean> opaqueFaces;
    @Unique private CycleButton<CullfaceMethod> topFaceCullingMethod;

    public VideoSettingsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @ModifyExpressionValue(method = "addOptions", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/options/VideoSettingsScreen;PREFERENCES_HEADER:Lnet/minecraft/network/chat/Component;", opcode = Opcodes.GETSTATIC))
    private Component addBlockyBubblesOptions(Component original) {
        if (this.list == null)
            return original;

        BlockyBubblesConfig config = BlockyBubbles.getConfig();

        this.quality = createCyclingEnum(QUALITY, Quality.values(), false, config::setQuality, config::getQuality, (button, value) -> {
            this.minecraft.levelRenderer.allChanged();
            this.animations.active = value == Quality.FAST;
            this.opaqueFaces.active = value == Quality.FAST;
            this.topFaceCullingMethod.active = value == Quality.FAST;

            if (this.options instanceof OptionsAccessor accessor)
                accessor.invokeSetGraphicsPresetToCustom();
        });
        this.animations = createCyclingBoolean(ANIMATIONS, config::setAnimations, config::hasAnimations, reloadAssets());
        this.opaqueFaces = createCyclingBoolean(OPAQUE_FACES, config::setOpaqueFaces, config::hasOpaqueFaces, reloadRenderer());
        this.topFaceCullingMethod = createCyclingEnum(CULLFACE_METHOD, CullfaceMethod.values(), true, config::setCullfaceMethod, config::getCullfaceMethod, reloadRenderer());

        this.list.addHeader(OPTION_PAGE_TEXT);
        this.list.addSmall(List.of(
            this.quality,
            this.animations,
            this.opaqueFaces,
            this.topFaceCullingMethod
        ));

        return original;
    }

    @Override
    public CycleButton<Quality> blocky_bubbles$get() {
        return this.quality;
    }

}
