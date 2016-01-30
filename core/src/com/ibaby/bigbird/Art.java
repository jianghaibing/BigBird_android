package com.ibaby.bigbird;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
	public static TextureRegion LightningSegment, HalfCircle, HalfCircle2, Pixel;
	public static void load(AssetManager assetManager){
		assetManager.load("HalfCircle.png", Texture.class);
		assetManager.load("LightningSegment.png", Texture.class);
		assetManager.load("Pixel.png", Texture.class);
	}
	public static void assignResource(AssetManager assetManager){
		LightningSegment = new TextureRegion(assetManager.get("LightningSegment.png", Texture.class));
		HalfCircle =  new TextureRegion(assetManager.get("HalfCircle.png", Texture.class));
		Pixel =  new TextureRegion(assetManager.get("Pixel.png", Texture.class));
		HalfCircle2 =  new TextureRegion(assetManager.get("HalfCircle.png", Texture.class));
		HalfCircle2.flip(true, false);
	}

}
