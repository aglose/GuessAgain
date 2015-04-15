package com.andrew.softwaredesign.guessagain;

/**
 * Created by Andrew on 4/9/2015.
 */
public class HistoryStatistics {
    private String userId = "";
    private int totalGamesPlayed = 0;
    private int totalCelebrityPlayed = 0;
    private int totalMoviePlayed = 0;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void increaseTotalGamesPlayed() {
        totalGamesPlayed++;
    }

    public int getTotalCelebrityPlayed() {
        return totalCelebrityPlayed;
    }

    public void setTotalCelebrityPlayed(int totalCelebrityPlayed) {
        this.totalCelebrityPlayed = totalCelebrityPlayed;
    }

    public int getTotalMoviePlayed() {
        return totalMoviePlayed;
    }

    public void setTotalMoviePlayed(int totalMoviePlayed) {
        this.totalMoviePlayed = totalMoviePlayed;
    }
}
