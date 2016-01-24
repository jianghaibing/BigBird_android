package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by baby on 16/1/12.
 */
public class Tornado extends Actor{
    public static final int WIDTH = 246;
    public static final int HEIGHT = 360;
    private TextureRegion region;
    private Vector2 velocity;
    protected enum State {alive,dead}
    private State state;
    private float tornadoY;
    public static final float TORNADO_X = 2400f;
    public static final float SPACING = 900f;
    public static final int NUMBER_OF_TORNADO = 3;
    private Rectangle bounds;
    private Circle circle;
    private float time;

    public Tornado() {
        region = new TextureRegion(Assets.tornado);
        setHeight(HEIGHT);
        setWidth(WIDTH);
        velocity = new Vector2(-500,0);
        state = State.alive;
        bounds = new Rectangle(WIDTH*0.4f,30f,WIDTH*0.2f,(HEIGHT-30)/7f*3f);
        circle = new Circle(WIDTH/3f+WIDTH/6f,10f+(HEIGHT-30)/7f*3f+(HEIGHT-30f)/7f*2,(HEIGHT-30f)/7f*2);
    }

    public void setFirstPosition(){
        tornadoY = MathUtils.random(140/640f*BigBirdGame.HEIGHT, 410/640f*BigBirdGame.HEIGHT);
        setPosition(TORNADO_X, tornadoY);
    }

    public void setSecondPosition(){
        tornadoY = MathUtils.random(140/640f*BigBirdGame.HEIGHT, 410/640f*BigBirdGame.HEIGHT);
        setPosition(TORNADO_X+SPACING, tornadoY);
    }

    public void setThirdPosition(){
        tornadoY = MathUtils.random(140/640f*BigBirdGame.HEIGHT, 410/640f*BigBirdGame.HEIGHT);
        setPosition(TORNADO_X+SPACING*2, tornadoY);
    }

    public void resetPosition(){
        tornadoY = MathUtils.random(140/640f*BigBirdGame.HEIGHT, 410/640f*BigBirdGame.HEIGHT);
        float totalOffset = SPACING*NUMBER_OF_TORNADO;
        setPosition(getX()+totalOffset,tornadoY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time+=delta;
        region = Assets.tornadoAnimation.getKeyFrame(time);
        switch (state){
            case alive:
                actAlive(delta);
                break;
            case dead:
                velocity = Vector2.Zero;
                break;
        }
        updateBounds();
    }


    private void updateBounds() {
        bounds.x = getX()+WIDTH*0.4f;
        bounds.y = getY()+30;
        circle.x = bounds.x+bounds.width/2;
        circle.y = bounds.y+bounds.height+circle.radius;
    }

    private void actAlive(float delta) {
        updatePosition(delta);
    }

    private void updatePosition(float delta) {
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        if (getX() <= -getWidth()){
            resetPosition();
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region, getX(), getY());
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
