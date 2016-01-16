package com.ibaby.bigbird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ibaby.bigbird.BigBirdGame;



public class DesktopLauncher{


	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BigBirdGame.WIDTH;
		config.height = BigBirdGame.HEIGHT;
		config.title = BigBirdGame.TITLE;
		new LwjglApplication(new BigBirdGame(null), config);
	}


}
