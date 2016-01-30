package com.ibaby.bigbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by baby on 16/1/13.
 */
public class Rain extends Actor {

    ParticleEffect effect;

    public Rain() {
        effect = Assets.effect;
        effect.setPosition(0, BigBirdGame.HEIGHT - 350);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        effect.draw(batch);
    }


}
