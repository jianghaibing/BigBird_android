package com.ibaby.bigbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by baby on 16/1/11.
 */
public class Assets {
    //dispoisable
    public static TextureAtlas atlas;
    public static SpriteBatch batch;
    public static BitmapFont bitmapFont;

    public static Sound jump;
    public static Sound wind;
    public static Sound oowh;
    public static Sound buttonSound;
    public static Sound handClap;
    public static Sound warning;
    public static Sound eatFish;

    //undisposable
    public static TextureRegion bird;
    public static TextureRegion bird2;
    public static TextureRegion bird3;
    public static Animation birdAnimation;
    public static Animation tornadoAnimation;
    public static Animation tapAnimation;
    public static TextureRegion background;
    public static TextureRegion background2;
    public static TextureRegion tornado;
    public static TextureRegion tornado2;
    public static TextureRegion startButton;
    public static TextureRegion startButton_down;
    public static TextureRegion leaderBoardButton;
    public static TextureRegion leaderBoardButton_down;
    public static TextureRegion achievementButton;
    public static TextureRegion achievementButton_down;
    public static TextureRegion shareButton;
    public static TextureRegion shareButton_down;
    public static TextureRegion restartButton;
    public static TextureRegion restartButton_down;
    public static TextureRegion water;
    public static TextureRegion water2;
    public static TextureRegion bg_wide;
    public static TextureRegion shark;
    public static TextureRegion smallFish;
    public static TextureRegion bigFish;
    public static TextureRegion progressBackground;
    public static TextureRegion progressRed;
    public static TextureRegion progressOrange;
    public static TextureRegion progressGreen;
    public static TextureRegion scoreBoard;
    public static TextureRegion gameOver;
    public static TextureRegion start;
    public static TextureRegion tap1;
    public static TextureRegion tap2;

    public static void load(){
        atlas = new TextureAtlas("pack.txt");
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont(Gdx.files.internal("font/font.fnt"),Gdx.files.internal("font/font.png"),false);

        bird = atlas.findRegion("bird1");
        bird2 = atlas.findRegion("bird2");
        bird3 = atlas.findRegion("bird3");
        birdAnimation = new Animation(0.2f,bird,bird2,bird3);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        background = atlas.findRegion("background");
        background2 = atlas.findRegion("background2");
        bg_wide = atlas.findRegion("bg_wide");
        water = atlas.findRegion("water");
        water2 = atlas.findRegion("water2");

        tornado = atlas.findRegion("tornado1");
        tornado2 = atlas.findRegion("tornado2");
        tornadoAnimation = new Animation(0.2f,tornado,tornado2);
        tornadoAnimation.setPlayMode(Animation.PlayMode.LOOP);

        startButton = atlas.findRegion("startGame");
        startButton_down = atlas.findRegion("startGame_down");
        leaderBoardButton = atlas.findRegion("leaderBoard");
        leaderBoardButton_down = atlas.findRegion("leaderBoard_down");
        achievementButton = atlas.findRegion("achievement");
        achievementButton_down = atlas.findRegion("achievement_down");
        shareButton = atlas.findRegion("share");
        shareButton_down = atlas.findRegion("share_down");
        restartButton = atlas.findRegion("restartGame");
        restartButton_down = atlas.findRegion("restartGame_down");

        shark = atlas.findRegion("shark");
        smallFish = atlas.findRegion("fish1");
        bigFish = atlas.findRegion("fish2");

        progressBackground = atlas.findRegion("progressBackground");
        progressRed = atlas.findRegion("progressRed");
        progressOrange = atlas.findRegion("progressOrange");
        progressGreen = atlas.findRegion("progressGreen");

        scoreBoard = atlas.findRegion("scoreBoard");
        gameOver = atlas.findRegion("gameOver");

        start = atlas.findRegion("start");
        tap1 = atlas.findRegion("tap1");
        tap2 = atlas.findRegion("tap2");
        tapAnimation = new Animation(0.5f,tap1,tap2);
        tapAnimation.setPlayMode(Animation.PlayMode.LOOP);


        jump = Gdx.audio.newSound(Gdx.files.internal("sound/jump.mp3"));
        wind = Gdx.audio.newSound(Gdx.files.internal("sound/wind.mp3"));
        oowh = Gdx.audio.newSound(Gdx.files.internal("sound/oowh.mp3"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonSound.mp3"));
        handClap = Gdx.audio.newSound(Gdx.files.internal("sound/handclap.wav"));
        warning = Gdx.audio.newSound(Gdx.files.internal("sound/warning.wav"));
        eatFish = Gdx.audio.newSound(Gdx.files.internal("sound/eatFish.mp3"));

    }

    public static void dispose(){
        if (atlas != null){
            atlas.dispose();
        }
        if (batch !=null){
            batch.dispose();
        }
        if (bitmapFont != null){
            bitmapFont.dispose();
        }
        if (jump != null){
            jump.dispose();
        }
        if (wind != null){
            wind.dispose();
        }
        if (oowh != null){
            oowh.dispose();
        }
        if (buttonSound != null){
            buttonSound.dispose();
        }
        if (handClap != null){
            handClap.dispose();
        }
        if (warning != null){
            warning.dispose();
        }
        if (eatFish != null){
            eatFish.dispose();
        }
    }

    public static void playJumpSound(){
        jump.play(1f);
    }


    public static void playWindSound(){
        wind.play(1f);
    }


    public static void playOowhSound(){
        oowh.play(1f);
    }


    public static void playButtonSound(){
        buttonSound.play(1f);
    }


    public static void playHnadClapSound(){
        handClap.play(1f);
    }


    public static void playWarningSound(){
        warning.play(1f);
    }


    public static void playEatFishSound(){
        eatFish.play(1f);
    }
}
