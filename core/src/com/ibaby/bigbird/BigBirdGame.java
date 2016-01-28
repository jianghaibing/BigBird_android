package com.ibaby.bigbird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BigBirdGame extends Game{

	public static final int WIDTH = 1080;
	public static final int HEIGHT = 1920;
	public static final String TITLE = "Bird VS Wind";
	protected AdHandler adHandler;
	public PlayServices playServices;
	public ShareInterface shareInterface;

	public BigBirdGame(AdHandler adHandler,PlayServices playServices,ShareInterface shareInterface) {
		this.adHandler = adHandler;
		this.playServices = playServices;
		this.shareInterface = shareInterface;
	}

	@Override
	public void create () {

		// Create assets manager
		AssetManager assetManager = new AssetManager();
		// create a new sprite batch to render the graphics
		Art.load(assetManager);
		assetManager.finishLoading();
		Art.assignResource(assetManager);

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
