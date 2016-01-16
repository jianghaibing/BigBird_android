package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by baby on 16/1/16.
 */
public class Shark extends Actor {

    private static final int WIDTH = 264;
    private static final int HEIGHT = 285;
    private static final float GRAVITY = 300;

    private Vector2 volocity;
    private TextureRegion region;
    private Rectangle bounds;

    public Shark() {
        region = new TextureRegion(Assets.shark);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        volocity = new Vector2(0,GRAVITY);
        bounds = new Rectangle(0,0,WIDTH,HEIGHT);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() + volocity.y * delta);

        if (getY() >= -10 && getY() < 0 && volocity.y == GRAVITY){
            volocity.y = 4f;
        }else if (getY() >= 0){
            volocity.y = - GRAVITY;
        }else if (getY() <= -4*HEIGHT){
            volocity.y = GRAVITY;
        }
        updateBoundsPosition();

    }

    private void updateBoundsPosition() {
        bounds.y = getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region,getX(),getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
