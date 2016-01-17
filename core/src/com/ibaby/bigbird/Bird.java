package com.ibaby.bigbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.Align;


/**
 * Created by baby on 16/1/11.
 */
public class Bird extends Actor {
    public static final int WIDTH = 210;
    public static final int HEIGHT = 168;
    public static final float GRAVITY = -3600f;
    public float jumpVelocity = 1500f;
    public int energy = 100;

    private Vector2 velocity;
    private Vector2 accel;
    private TextureRegion region;
    private State state;
    protected enum State{ alive , stop}
    private Circle bounds;

    private float time;

    public Bird() {
        region = new TextureRegion(Assets.bird);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.stop;
        velocity = new Vector2(0,0);
        accel = new Vector2(0,GRAVITY);
        setOrigin(Align.center);
        bounds = new Circle(WIDTH/2,WIDTH/2,WIDTH/2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        time += delta;
        region = Assets.birdAnimation.getKeyFrame(time);

        switch (state){
            case alive:
                actAlive(delta);
                break;
            case stop:
                // 不做任何事情
                break;
        }
        updateBounds();

    }

    private void updateBounds() {
        bounds.x = getX()+WIDTH/2;
        bounds.y = getY()+WIDTH/2;
    }

    private void actAlive(float delta) {
        applyAccel(delta);
        updatePosition(delta);
        if (isBlowBottom()){
            setY(0);
            state = State.stop;

        }
        if (isAboveCeiling()){
            setY(BigBirdGame.HEIGHT-getHeight());

        }
        jump();
    }

    private void jump(){
        if (Gdx.input.justTouched()){
            jumpVelocity -= 30;
            energy -= 2;
            velocity.y = jumpVelocity;
            clearActions();
            if (energy < 50){
                playWarningSound();
            }else {
                playJumpSound();
            }

            RotateToAction rotateToAction1 = Actions.rotateTo(8f, 0.1f);
            DelayAction delayAction = Actions.delay(0.3f);
            RotateToAction rotateToAction2 = Actions.rotateTo(-5f,0.3f);

            addAction(Actions.sequence(rotateToAction1,delayAction,rotateToAction2));
        }
    }

    private void updatePosition(float delta) {
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);


    }

    private void applyAccel(float delta) {
        velocity.add(accel.x * delta, accel.y * delta);
    }

    private boolean isBlowBottom(){
        return (getY(Align.bottom) <= 0);
    }

    private boolean isAboveCeiling(){
        return (getY(Align.topLeft) >= BigBirdGame.HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void setBounds(Circle bounds) {
        this.bounds = bounds;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity(float jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }

    private void playWarningSound(){
        Assets.playWarningSound();
    }

    private void playJumpSound(){
        Assets.playJumpSound();
    }
}
