package com.ibaby.bigbird;

/**
 * Created by baby on 16/1/17.
 */
public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(String achievementID);
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
