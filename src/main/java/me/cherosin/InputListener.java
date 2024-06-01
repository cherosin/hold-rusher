package me.cherosin;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.EventListener;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.bind.key.GLFWKey;
import org.rusherhack.client.api.events.client.input.EventKeyboard;
import org.rusherhack.client.api.events.client.input.EventMouse;
import org.rusherhack.client.api.events.client.input.EventMouse.Key;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.bind.key.IKey;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.subscribe.Subscribe;

public class InputListener implements EventListener {

    @Subscribe(stage = Stage.ALL)
	private void onKeyboardInput(EventKeyboard event) {
		//rusher already toggles module on press, so we can just toggle on release

		if(event.getAction() != GLFW.GLFW_RELEASE || event.getStage() != Stage.ON) {
			return;
		}

		if(event.getKey() != GLFW.GLFW_KEY_F3 && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_F3)) {
			return;
		}

		RusherHackAPI.getBindManager().getBindRegistry().forEach((bindable, key) -> {
				if ((doesMatch(event, key)) && bindable instanceof ToggleableModule && (Boolean) ((ToggleableModule) bindable).getSetting("Hold").getValue()) {
					bindable.onKeybindEvent();
				}
		});
	}

	@Subscribe
	private void onMouseInput(EventMouse.Key event) {
		if (event.getAction() != GLFW.GLFW_RELEASE) {
			return;
		}

		RusherHackAPI.getBindManager().getBindRegistry().forEach((bindable, key) -> {
			if ((doesMatch(event, key)) && bindable instanceof ToggleableModule && (Boolean) ((ToggleableModule) bindable).getSetting("Hold").getValue()) {
				bindable.onKeybindEvent();
			}
		});
	}

	private boolean doesMatch(Key event, IKey key) {
		if(!(key instanceof GLFWKey)) return false;

		return event.getButton() == ((GLFWKey) key).getKeyCode();
	}

	private boolean doesMatch(EventKeyboard event, IKey key) {
		if(!(key instanceof GLFWKey)) return false;

		return event.getKey() == ((GLFWKey) key).getKeyCode();
    }
}
