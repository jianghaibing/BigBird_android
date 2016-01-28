package com.ibaby.bigbird;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Lightning {
	boolean isComplete();
	void update();
	void draw(SpriteBatch batch);
}
