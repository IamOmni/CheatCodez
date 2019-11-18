package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.mainKroyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = mainKroyGame.WIDTH;
		config.height = mainKroyGame.HEIGHT;
		config.title = "Kroy - TeamCheatCodez";
		new LwjglApplication(new mainKroyGame(), config);
	}
}
