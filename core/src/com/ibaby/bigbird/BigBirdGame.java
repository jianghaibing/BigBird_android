package com.ibaby.bigbird;

import com.badlogic.gdx.Game;

public class BigBirdGame extends Game{

	public static final int WIDTH = 1080;
	public static final int HEIGHT = 1920;
	public static final String TITLE = "风狂大鸟";
	protected AdHandler adHandler;

	public BigBirdGame(AdHandler adHandler) {
		this.adHandler = adHandler;
	}

	@Override
	public void create () {
		Assets.load();
		DataManager.shareInstance().load();
		setScreen(new GameMenuScreen(this,false));

	}

	@Override
	public void pause() {
		super.pause();
		DataManager.shareInstance().save();
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}

}
