package com.ibaby.bigbird;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by baby on 16/1/13.
 */

public class Ground extends Image {

    private Rectangle bounds;

    public Ground() {
        bounds = new Rectangle(0,0,BigBirdGame.WIDTH,1);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
