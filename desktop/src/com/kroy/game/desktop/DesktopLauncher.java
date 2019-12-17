package com.kroy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.kroyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = kroyGame.WIDTH;
		config.height = kroyGame.HEIGHT;
		config.fullscreen = true;
		config.title = "Kroy - TeamCheatCodez";
		new LwjglApplication(new kroyGame(), config);
	}
}
