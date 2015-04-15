package com.andrew.softwaredesign.guessagain;

/**
 * Created by Andrew on 4/9/2015.
 */
public class ScoreStatistics {
    private String userId = "";
    private String gameType = "";
    private int currentCorrect = 0;

    public ScoreStatistics(String choice){
        gameType = choice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getCurrentCorrect() {
        return currentCorrect;
    }

    public void increaseCurrentCorrect() {
        currentCorrect++;
    }
}
