package com.ibaby.bigbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * Created by baby on 16/1/11.
 */
public class GamePlayScreen extends ScreenAdapter {


    protected BigBirdGame bigBirdGame;
    protected OrthographicCamera camera;
    private Stage gamePlayStage;
    private Bird bird;
    private Background background;
    private Background background2;
    private Water water;
    private Water water2;
    private Tap tap;
    private Image start;
    private Array<Tornado> tornados;
    private enum State{PLAYING, STATIC}
    private State state = State.STATIC;
    private Ground ground;
//    private ShapeRenderer renderer;
    private Label currentScoreLabel;
    private Label highestScoreLabel;
    private Label progressNumberLabel;
    private Image progressBackground;
    private Image currentProgress;

    private int currentScore = 0;
    private int highestScore = 0;
    private float time;
    private Shark shark;

    private SmallFish smallFish;
    private Array<SmallFish> smallFishs;

    private BigFish bigFish;
    private Array<BigFish> bigFishs;


    public GamePlayScreen(BigBirdGame bigBirdGame) {
        this.bigBirdGame = bigBirdGame;
        camera = new OrthographicCamera(BigBirdGame.WIDTH,BigBirdGame.HEIGHT);
        gamePlayStage = new Stage(new StretchViewport(BigBirdGame.WIDTH,BigBirdGame.HEIGHT));

        background = new Background(Background.BackgroundSequence.FIRST);
        background2 = new Background(Background.BackgroundSequence.SECOND);
        background2.setPosition(BigBirdGame.WIDTH, 0);
        gamePlayStage.addActor(background);
        gamePlayStage.addActor(background2);

        ground = new Ground();
        gamePlayStage.addActor(ground);
        bird = new Bird();
        bird.setPosition(BigBirdGame.WIDTH * 0.25f, BigBirdGame.HEIGHT * 0.5f, Align.center);
        gamePlayStage.addActor(bird);
        currentScoreLabel = new Label("当前:"+currentScore+"M",new Label.LabelStyle(Assets.bitmapFont,Color.valueOf("F5A623")));
        currentScoreLabel.setPosition(20, BigBirdGame.HEIGHT * .97f);
        gamePlayStage.addActor(currentScoreLabel);

        highestScore = DataManager.shareInstance().getHighScore();
        highestScoreLabel = new Label("最高:"+ highestScore +"M",new Label.LabelStyle(Assets.bitmapFont,Color.valueOf("5ECA8A")));
        highestScoreLabel.setPosition(BigBirdGame.WIDTH - 20, BigBirdGame.HEIGHT * 0.97f, Align.bottomRight);
        gamePlayStage.addActor(highestScoreLabel);

        progressBackground = new Image(Assets.progressBackground);
        progressBackground.setPosition(BigBirdGame.WIDTH * .3f, BigBirdGame.HEIGHT * 0.97f - 10);
        progressBackground.setHeight(70);
        progressBackground.setWidth(BigBirdGame.WIDTH * .4f);
        gamePlayStage.addActor(progressBackground);

        currentProgress = new Image(Assets.progressGreen);
        currentProgress.setPosition(BigBirdGame.WIDTH * .3f, BigBirdGame.HEIGHT * 0.97f - 10);
        currentProgress.setHeight(70);
        currentProgress.setWidth(BigBirdGame.WIDTH * .4f);
        gamePlayStage.addActor(currentProgress);

        progressNumberLabel = new Label(String.valueOf(bird.energy) + "/100",new Label.LabelStyle(Assets.bitmapFont,Color.WHITE));
        progressNumberLabel.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT * 0.97f + progressNumberLabel.getHeight() / 2, Align.center);
        gamePlayStage.addActor(progressNumberLabel);

        tap = new Tap();
        tap.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2, Align.center);
        gamePlayStage.addActor(tap);
        start = new Image(Assets.start);
        start.setPosition(BigBirdGame.WIDTH / 2, BigBirdGame.HEIGHT / 2 + 350f, Align.center);
        gamePlayStage.addActor(start);

        tornados = new Array<Tornado>();
        addTornadoToStage(gamePlayStage);

        smallFishs = new Array<SmallFish>();
        bigFishs = new Array<BigFish>();
        for (int i = 0; i <4 ; i++) {
            addFish(gamePlayStage);
        }

        shark = new Shark();
        shark.setPosition(BigBirdGame.WIDTH * 0.15f, -shark.getHeight() * 4);
        gamePlayStage.addActor(shark);

        water = new Water(Water.WaterSequence.FIRST);
        water2 = new Water(Water.WaterSequence.SECOND);
        water2.setPosition(BigBirdGame.WIDTH, 0);
        gamePlayStage.addActor(water);
        gamePlayStage.addActor(water2);
//        renderer = new ShapeRenderer();
        gamePlayStage.addListener(new ActorGestureListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (state == State.STATIC){
                    bird.setState(Bird.State.alive);
                    tap.remove();
                    start.remove();
                    state = State.PLAYING;
                }
            }
        });

        Gdx.input.setInputProcessor(gamePlayStage);
    }

    private void addFish(Stage gamePlayStage){
        smallFish = new SmallFish();
        smallFish.randomFishPosition();
        gamePlayStage.addActor(smallFish);
        smallFishs.add(smallFish);

        bigFish = new BigFish();
        bigFish.randomFishPosition();
        gamePlayStage.addActor(bigFish);
        bigFishs.add(bigFish);

    }

    private void addTornadoToStage(Stage gamePlayStage){
        Tornado tornado1 = new Tornado();
        tornado1.setFirstPosition();
        gamePlayStage.addActor(tornado1);
        tornados.add(tornado1);

        Tornado tornado2 = new Tornado();
        tornado2.setSecondPosition();
        gamePlayStage.addActor(tornado2);
        tornados.add(tornado2);

        Tornado tornado3 = new Tornado();
        tornado3.setThirdPosition();
        gamePlayStage.addActor(tornado3);
        tornados.add(tornado3);
    }


    @Override
    public void render(float delta) {

        switch (state){
            case PLAYING:
                gamePlayStage.act();
                checkCollisions();
                time+=delta;
                currentScore = (int) (time * 2.5f);
                currentScoreLabel.setText("当前:" + currentScore + "M");
                progressNumberLabel.setText(String.valueOf(bird.energy) + "/100");
                currentProgress.setWidth(BigBirdGame.WIDTH*.4f*bird.energy/100);
                if (bird.energy>= 75){
                    currentProgress.setDrawable(new TextureRegionDrawable(Assets.progressGreen));
                }else if (bird.energy >= 50){
                    currentProgress.setDrawable(new TextureRegionDrawable(Assets.progressOrange));
                }else {
                    currentProgress.setDrawable(new TextureRegionDrawable(Assets.progressRed));
                }

                if (currentScore> highestScore){
                    highestScore = currentScore;
                    highestScoreLabel.setText("最高:" + highestScore + "M");
                    highestScoreLabel.setAlignment(Align.bottomRight);
                    DataManager.shareInstance().setHighScore(highestScore);
                }
                gamePlayStage.draw();
                break;
            case STATIC:
                tap.act(delta);
                background.act(delta);
                background2.act(delta);
                water.act(delta);
                water2.act(delta);
                bird.act(delta);
                gamePlayStage.draw();
                break;
        }


//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(Color.RED);
//        renderer.circle(bird.getBounds().x, bird.getBounds().y, bird.getBounds().radius);
//        for (Tornado tornado:tornados) {
//            renderer.rect(tornado.getBounds().getX(),tornado.getBounds().getY(),tornado.getBounds().getWidth(),tornado.getBounds().getHeight());
//            renderer.circle(tornado.getCircle().x,tornado.getCircle().y,tornado.getCircle().radius);
//
//        }
//        renderer.end();
    }

    private void checkCollisions() {
        for (Tornado tornado : tornados) {
            if (Intersector.overlaps(bird.getBounds(),tornado.getBounds())
                    || Intersector.overlaps(bird.getBounds(),tornado.getCircle()) ){
                playWindSound();
                state = State.STATIC;
                if (bigBirdGame.adHandler != null) {
                    bigBirdGame.adHandler.showAds(true);
                }
                GameMenuScreen menuScreen = new GameMenuScreen(bigBirdGame,true);
                menuScreen.setScore(currentScore,highestScore);
                bigBirdGame.setScreen(menuScreen);
            }

            if (Intersector.overlaps(bird.getBounds(),ground.getBounds())
                    || Intersector.overlaps(bird.getBounds(),shark.getBounds())){
                playOowhSound();
                state = State.STATIC;
                if (bigBirdGame.adHandler != null) {
                    bigBirdGame.adHandler.showAds(true);
                }
                GameMenuScreen menuScreen = new GameMenuScreen(bigBirdGame,true);
                menuScreen.setScore(currentScore,highestScore);
                bigBirdGame.setScreen(menuScreen);
            }
        }



        for (SmallFish smallFish : smallFishs){
            if (Intersector.overlaps(bird.getBounds(),smallFish.getBounds())){
                playEatFishSound();
                smallFish.randomFishPosition();
                bird.jumpVelocity += 150;
                bird.energy += 10;
                if (bird.getJumpVelocity() > 1500f){
                    bird.setJumpVelocity(1500f);
                }
                if (bird.energy > 100){
                    bird.energy = 100;
                }
            }
        }

        for (BigFish bigFish : bigFishs){
            if (Intersector.overlaps(bird.getBounds(),bigFish.getBounds())){
                playEatFishSound();
                bigFish.randomFishPosition();
                bird.jumpVelocity += 225;
                bird.energy += 15;
                if (bird.getJumpVelocity() > 1500f){
                    bird.setJumpVelocity(1500f);
                }
                if (bird.energy > 100){
                    bird.energy = 100;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        gamePlayStage.getViewport().update(width, height, true);
    }


    private void playWindSound(){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/wind.mp3"));
        sound.play();
    }

    private void playOowhSound(){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/oowh.mp3"));
        sound.play();
    }



    private void playEatFishSound(){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/eatFish.mp3"));
        sound.play();
    }

}
