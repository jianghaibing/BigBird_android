package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by baby on 16/1/16.
 */
public class BigFish extends Actor {

    public static final int WIDTH = 210;
    public static final int HEIGHT = 30;
    private TextureRegion region;
    private Vector2 velocity;
    private float fishX;
    private float fishY;
    private Rectangle bounds;
    private float gravity;

    public BigFish() {
        region = new TextureRegion(Assets.bigFish);
        setHeight(HEIGHT);
        setWidth(WIDTH);
        randomGravity();
        velocity = new Vector2(-gravity,0);
        bounds = new Rectangle(0,0,WIDTH,HEIGHT);
    }

    private void randomGravity(){
        gravity = MathUtils.random(300, 500);
    }

    private void randomFishX(){
        fishX = MathUtils.random(BigBirdGame.WIDTH,BigBirdGame.WIDTH*2);
    }

    private void randomFishY(){
        fishY = MathUtils.random(0, 340);
    }

    protected void randomFishPosition(){
        randomFishX();
        randomFishY();
        randomGravity();
        setPosition(fishX, fishY);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        updatePosition(delta);
        updateBounds();
    }


    private void updateBounds() {
        bounds.x = getX();
        bounds.y = getY();
    }

    private void updatePosition(float delta) {
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        if (getX() <= -getWidth()){
            randomFishPosition();
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region, getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
