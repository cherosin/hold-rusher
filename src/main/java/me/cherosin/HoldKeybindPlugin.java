package me.cherosin;

import java.util.EventListener;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.plugin.Plugin;
import org.rusherhack.core.setting.BooleanSetting;

public class HoldKeybindPlugin extends Plugin {
	private final EventListener inputListener = new InputListener();
	
	@Override
	public void onLoad() {
		this.getLogger().info("Hold keybind plugin loaded!");
		RusherHackAPI.getModuleManager().getFeatures().forEach(module -> {
			if (module instanceof ToggleableModule && module.getCategory() != ModuleCategory.CLIENT) {
				module.getSettings().add(new BooleanSetting("Hold", "Hold keybind", false));
			}
		});
		RusherHackAPI.getEventBus().subscribe(inputListener);
	}
	
	@Override
	public void onUnload() {
		RusherHackAPI.getEventBus().unsubscribe(inputListener);
		this.getLogger().info("Hold keybind plugin unloaded!");
	}
	
}