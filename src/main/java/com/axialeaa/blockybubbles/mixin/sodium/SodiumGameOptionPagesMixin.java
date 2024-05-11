package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.sodium.SodiumCompat;
import com.axialeaa.blockybubbles.sodium.SodiumConfig;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
/*? if >=1.19.2 { */
import net.minecraft.text.Text;
/*? } else { *//*
import net.minecraft.text.TranslatableText;
*//*? } */
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin {

    @Unique private static final String qualityName = "blocky-bubbles.options.quality.name";
    @Unique private static final String qualityTooltip = "blocky-bubbles.options.quality.tooltip";

    @Unique private static final String cullingAwarenessName = "blocky-bubbles.options.culling_awareness.name";
    @Unique private static final String cullingAwarenessTooltip = "blocky-bubbles.options.culling_awareness.tooltip";

    /**
     * Adds the "Bubble Columns" option to Sodium's video settings screen.
     */
    @ModifyVariable(method = "quality", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;copyOf(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableList;"), remap = false)
    private static List<OptionGroup> addOption(List<OptionGroup> groups) {
        groups.add(OptionGroup.createBuilder()
            .add(OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, SodiumConfig.blockyBubblesOptions)
                /*? if >=1.19.2 { */
                .setName(Text.translatable(qualityName))
                .setTooltip(Text.translatable(qualityTooltip))
                /*? } else { *//*
                .setName(new TranslatableText(qualityName))
                .setTooltip(new TranslatableText(qualityTooltip))
                *//*? } */

                .setControl(option -> new CyclingControl<>(option, SodiumGameOptions.GraphicsQuality.class))
                .setBinding((opts, value) -> opts.bubblesQuality = value, opts -> opts.bubblesQuality)
                .setImpact(OptionImpact.MEDIUM)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            )
            .add(OptionImpl.createBuilder(SodiumCompat.CullingAwareness.class, SodiumConfig.blockyBubblesOptions)
                /*? if >=1.19.2 { */
                .setName(Text.translatable(cullingAwarenessName))
                .setTooltip(Text.translatable(cullingAwarenessTooltip))
                /*? } else { *//*
                .setName(new TranslatableText(cullingAwarenessName))
                .setTooltip(new TranslatableText(cullingAwarenessTooltip))
                *//*? } */

                .setControl(option -> new CyclingControl<>(option, SodiumCompat.CullingAwareness.class))
                .setBinding((opts, value) -> opts.cullingAwareness = value, opts -> opts.cullingAwareness)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            ).build()
        );

        return groups;
    }

}
