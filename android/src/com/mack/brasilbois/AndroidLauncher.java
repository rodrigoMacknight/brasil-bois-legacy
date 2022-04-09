package com.mack.brasilbois;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;


public class AndroidLauncher extends AndroidApplication {

	private Viewport viewport;
	private Camera camera;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		config.width= BrBoisMain.WIDTH;
//		config.height= BrBoisMain.HEIGHT;
//		config.title = BrBoisMain.TITLE;

		initialize(new BrBoisMain(), config);
	}
}