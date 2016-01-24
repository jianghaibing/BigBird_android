package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by baby on 16/1/15.
 */
public class Water extends Image {

    private int WIDTH = 1080;
    private int HEIGHT = 447;
    private TextureRegion region;
    private Vector2 velocity;
    public static final int NUMBER_OF_BACKGROUND = 2;
    protected enum WaterSequence{FIRST,SECOND}
    private WaterSequence sequence;

    public Water(WaterSequence sequence) {
        this.sequence = sequence;
        if(sequence == WaterSequence.FIRST) {
            region = new TextureRegion(Assets.water);
        }else {
            region = new TextureRegion(Assets.water2);
        }
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setZIndex(5);
        velocity = new Vector2(-250,0);
    }



    public void resetPosition(){
        float totalOffset = WIDTH*NUMBER_OF_BACKGROUND;
        setPosition(getX()+totalOffset,0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePosition(delta);
    }


    private void updatePosition(float delta) {
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);

        if (getX() <= -getWidth()) {
            resetPosition();
        }

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region, getX(), getY());
    }
}
