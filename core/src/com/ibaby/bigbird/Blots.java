package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Vector;

/**
 * Created by baby on 16/1/26.
 */
public class Blots extends Actor{
    private Vector<LightningBolt> bolts;

    public Blots(Vector<LightningBolt> bolts) {
        this.bolts = bolts;
    }

    private int time;

    @Override
    public void act(float delta) {
        super.act(delta);
        time++;
        
        if(time % 300 == 0 || time % 500 == 0) {
            playLightningSound();
            float x1 = MathUtils.random(400f,BigBirdGame.WIDTH-400f);
            float x2 = MathUtils.random(300f, BigBirdGame.WIDTH-300f);
            float y1 = MathUtils.random(BigBirdGame.HEIGHT - 300f, BigBirdGame.HEIGHT - 400f);
            float y2 = MathUtils.random(BigBirdGame.HEIGHT - 600f, BigBirdGame.HEIGHT - 700f);
            bolts.add(new LightningBolt(new Vector2( x1, y1),
                    new Vector2(x2,y2)));
        }


        // draw the Scene
        updateBolts();

        removeCompleted();
    }

    private void updateBolts(){
        for(int i=0; i<bolts.size(); i++){
            bolts.get(i).update();
        }
    }
    private void removeCompleted() {
        int count = bolts.size();
        for(int i=0; i<count; i++){
            if (bolts.get(i).isComplete()){
                bolts.remove(i);
                count--;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(int i=0; i<bolts.size(); i++){
            bolts.get(i).draw((SpriteBatch) batch);
        }
    }

    private void playLightningSound(){
        Assets.playLightningSound();
    }
}
