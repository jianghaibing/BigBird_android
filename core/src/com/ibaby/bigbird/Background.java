package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by baby on 16/1/14.
 */
public class Background extends Image{

    private TextureRegion region;
    private Vector2 velocity;
    public static final int NUMBER_OF_BACKGROUND = 2;
    protected enum BackgroundSequence{FIRST,SECOND}
    private BackgroundSequence sequence;

    public Background(BackgroundSequence sequence) {
        this.sequence = sequence;
        if(sequence == BackgroundSequence.FIRST) {
            region = new TextureRegion(Assets.background);
        }else {
            region = new TextureRegion(Assets.background2);
        }
        setHeight(BigBirdGame.HEIGHT);
        setWidth(BigBirdGame.WIDTH);
        velocity = new Vector2(-250,0);
    }



    public void resetPosition(){
        float totalOffset = BigBirdGame.WIDTH*NUMBER_OF_BACKGROUND;
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
