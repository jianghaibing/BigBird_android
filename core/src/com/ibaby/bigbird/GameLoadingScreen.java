package com.ibaby.bigbird;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;



/**
 * Created by baby on 16/1/28.
 */
public class GameLoadingScreen extends ScreenAdapter {
    protected BigBirdGame bigBirdGame;
    private OrthographicCamera camera;
    private Stage stage;
    Image loadingView;

    public GameLoadingScreen(final BigBirdGame bigBirdGame){
        this.bigBirdGame = bigBirdGame;
        camera = new OrthographicCamera(BigBirdGame.WIDTH,BigBirdGame.HEIGHT);
        stage = new Stage(new StretchViewport(BigBirdGame.WIDTH,BigBirdGame.HEIGHT));
        loadingView = new Image(Assets.loadingView);
        stage.addActor(loadingView);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                bigBirdGame.setScreen(new GameMenuScreen(bigBirdGame,false));
            }
        }, 2f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        stage.getViewport().update(width, height);
    }
}
