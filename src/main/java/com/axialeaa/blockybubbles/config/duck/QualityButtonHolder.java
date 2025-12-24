package com.axialeaa.blockybubbles.config.duck;

import com.axialeaa.blockybubbles.config.Quality;
import net.minecraft.client.gui.components.CycleButton;
import org.jspecify.annotations.Nullable;

public interface QualityButtonHolder {

    @Nullable
    CycleButton<Quality> blocky_bubbles$get();

}