package com.axialeaa.blockybubbles.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class ReloadCallback {

	private final Minecraft minecraft;
	@Nullable private ReloadType reloadType = null;

	public ReloadCallback(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	public void run() {
		switch (this.reloadType) {
			case RENDERER -> Options.operateOnLevelExtractor(LevelExtractor::allChanged);
			case ASSETS -> this.minecraft.reloadResourcePacks();
			case null -> {}
		}
	}

	public <T> CycleButton.OnValueChange<T> assets() {
		return (_, _) -> this.reloadType = ReloadType.ASSETS;
	}

	public <T> CycleButton.OnValueChange<T> renderer() {
		return (_, _) -> {
			if (!Objects.equals(this.reloadType, ReloadType.ASSETS))
				this.reloadType = ReloadType.RENDERER; // reloading assets also does a renderer reload
		};
	}

	public void onQualityChange() {
		if (ConfigHelper.FROZENLIB_LOADED)
			this.reloadType = ReloadType.ASSETS;
		else if (!Objects.equals(this.reloadType, ReloadType.ASSETS))
			this.reloadType = ReloadType.RENDERER;
	}

	private enum ReloadType {
		ASSETS, RENDERER
	}

}