package com.mack.brasilbois.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mack.brasilbois.BrBoisMain;

public class DesktopLauncherEnemy {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= BrBoisMain.WIDTH;
		config.height= BrBoisMain.HEIGHT;
		config.title = BrBoisMain.TITLE;
		new LwjglApplication(new BrBoisMain(), config);
	}
}
