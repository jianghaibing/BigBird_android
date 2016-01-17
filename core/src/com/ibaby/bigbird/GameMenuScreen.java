package com.ibaby.bigbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * Created by baby on 16/1/13.
 */
public class GameMenuScreen extends ScreenAdapter {

    protected BigBirdGame bigBirdGame;
    private Stage stage;
    private OrthographicCamera camera;
    private ImageButton startButton;
    private ImageButton leaderBoardButton;
    private ImageButton achievementButton;
    private ImageButton shareButton;
    private ImageButton restartButton;
    private Image background;
    private Image scoreBoard;
    private Image gameOver;

    private Label currentScoreLabel;
    private Label highestScoreLabel;
    private int currentScore = 0;
    private int highestScore = 0;

    private Bird bird;
    private static float BACKGROUNDMOVEMENT = 100f;
    private static float BUTTON_DISTANCE = 60F;

    protected boolean isGameover = false;


    public GameMenuScreen(final BigBirdGame bigBirdGame,boolean isGameover) {
        this.bigBirdGame = bigBirdGame;
        this.isGameover = isGameover;
        stage = new Stage(new StretchViewport(BigBirdGame.WIDTH,BigBirdGame.HEIGHT));
        camera = new OrthographicCamera(BigBirdGame.WIDTH,BigBirdGame.HEIGHT);

        background = new Image(Assets.bg_wide);
        background.setPosition(BigBirdGame.WIDTH/2,BigBirdGame.HEIGHT/2,Align.center);
        MoveByAction moveRight = Actions.moveBy(BACKGROUNDMOVEMENT, 0, 5f);
        MoveByAction moveLeft = Actions.moveBy(-BACKGROUNDMOVEMENT,0,5);
        background.addAction(Actions.forever(Actions.sequence(moveRight, moveLeft)));
        stage.addActor(background);

        if (!isGameover) {
            bird = new Bird();
            bird.setState(Bird.State.stop);
            bird.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT * 0.75f, Align.center);
            stage.addActor(bird);

            startButton = new ImageButton(new TextureRegionDrawable(Assets.startButton), new TextureRegionDrawable(Assets.startButton_down));
            startButton.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2, Align.center);
            stage.addActor(startButton);
            startButton.addListener(new ActorGestureListener() {
                @Override
                public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    playButtonSound();
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if (bigBirdGame.adHandler != null) {
                        bigBirdGame.adHandler.showAds(false);
                    }
                    bigBirdGame.setScreen(new GamePlayScreen(bigBirdGame));
                    stage.dispose();
                }
            });
        }else {

            restartButton = new ImageButton(new TextureRegionDrawable(Assets.restartButton), new TextureRegionDrawable(Assets.restartButton_down));
            restartButton.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2, Align.center);
            stage.addActor(restartButton);
            restartButton.addListener(new ActorGestureListener() {
                @Override
                public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    playButtonSound();
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if (bigBirdGame.adHandler != null) {
                        bigBirdGame.adHandler.showAds(false);
                    }
                    bigBirdGame.setScreen(new GamePlayScreen(bigBirdGame));
                    stage.dispose();
                }
            });

            scoreBoard = new Image(Assets.scoreBoard);
            scoreBoard.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2 + BUTTON_DISTANCE + scoreBoard.getHeight() / 2 + restartButton.getHeight() / 2, Align.center);
            stage.addActor(scoreBoard);

            currentScoreLabel = new Label("当前飞行: "+currentScore + "M",new Label.LabelStyle(Assets.bitmapFont, Color.WHITE));
            currentScoreLabel.setPosition(bigBirdGame.WIDTH/2-currentScoreLabel.getWidth()/2, scoreBoard.getY()+ scoreBoard.getHeight()*0.7f,Align.center);
            currentScoreLabel.setFontScale(2f);
            stage.addActor(currentScoreLabel);

            highestScoreLabel = new Label("最高飞行: "+highestScore + "M",new Label.LabelStyle(Assets.bitmapFont, Color.WHITE));
            highestScoreLabel.setPosition(bigBirdGame.WIDTH/2-currentScoreLabel.getWidth()/2, scoreBoard.getY()+ scoreBoard.getHeight()*0.3f,Align.center);
            highestScoreLabel.setFontScale(2f);
            stage.addActor(highestScoreLabel);

            gameOver = new Image(Assets.gameOver);
            gameOver.setPosition(BigBirdGame.WIDTH/2,scoreBoard.getY()+scoreBoard.getHeight() + BUTTON_DISTANCE+gameOver.getHeight()/2,Align.center);
            stage.addActor(gameOver);


        }

        leaderBoardButton = new ImageButton(new TextureRegionDrawable(Assets.leaderBoardButton),new TextureRegionDrawable(Assets.leaderBoardButton_down));
        leaderBoardButton.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2 - leaderBoardButton.getHeight() - BUTTON_DISTANCE, Align.center);
        stage.addActor(leaderBoardButton);
        leaderBoardButton.addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                playButtonSound();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                bigBirdGame.playServices.showScore();
            }
        });

        achievementButton = new ImageButton(new TextureRegionDrawable(Assets.achievementButton),new TextureRegionDrawable(Assets.achievementButton_down));
        achievementButton.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2 - achievementButton.getHeight() * 2f - BUTTON_DISTANCE * 2f, Align.center);
        stage.addActor(achievementButton);
        achievementButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                playButtonSound();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        shareButton = new ImageButton(new TextureRegionDrawable(Assets.shareButton),new TextureRegionDrawable(Assets.shareButton_down));
        shareButton.setPosition(BigBirdGame.WIDTH/2,BigBirdGame.HEIGHT/2 - shareButton.getHeight()*3f - BUTTON_DISTANCE*3f,Align.center);
        stage.addActor(shareButton);
        shareButton.addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                playButtonSound();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });



        Gdx.input.setInputProcessor(stage);

    }

    private void playButtonSound(){
        Assets.playButtonSound();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        stage.getViewport().update(width,height);
    }


    public void setScore(int currentScore,int highestScore){
        currentScoreLabel.setText("当前飞行: " + currentScore + "M");
        highestScoreLabel.setText("最高飞行: " + highestScore + "M");
    }
}
