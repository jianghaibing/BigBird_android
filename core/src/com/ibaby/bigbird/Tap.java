package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by baby on 16/1/16.
 */
public class Tap extends Actor {

    private static final int WIDTH = 135;
    private static final int HEIGHT = 207;
    private TextureRegion region;
    private float time;

    public Tap() {
        region = new TextureRegion(Assets.tap1);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        region = Assets.tapAnimation.getKeyFrame(time);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region,getX(),getY());
    }
}
