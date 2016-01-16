package com.ibaby.bigbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by baby on 16/1/13.
 */
public class DataManager {

    private static final String KEY_PREFERENCES = "My Preferences";
    private static final String KEY_HIGHSCORE = "highScore";
    private static DataManager instance = null;
    private int highScore = 0;

    public static DataManager shareInstance(){
        if (instance == null){
            instance = new DataManager();
        }
        return instance;
    }


    public void save(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        prefs.putInteger(KEY_HIGHSCORE,highScore);
        prefs.flush();
    }

    public void load(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        highScore = prefs.getInteger(KEY_HIGHSCORE,0);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        if (score>highScore){
            highScore = score;
        }
    }
}
