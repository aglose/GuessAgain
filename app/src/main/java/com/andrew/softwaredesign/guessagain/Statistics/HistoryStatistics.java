package com.andrew.softwaredesign.guessagain.Statistics;

import java.util.ArrayList;

/**
 * Created by Andrew on 4/9/2015.
 */
public class HistoryStatistics {
    private ArrayList<ScoreStatistics> gameHistoryList = new ArrayList<>();
    private String userId = "";
    private int totalGamesPlayed = 0;
    private int totalCelebrityPlayed = 0;
    private int totalMoviePlayed = 0;
    private int highScore = 0;
    private int highScoreCelebrities = 0;
    private int highScoreMovies = 0;
    private int highScoreCountries = 0;
    private int highScoreHistoryFigures = 0;
    private int highScoreAnimals = 0;

    public int getHighScoreBooks() {
        return highScoreBooks;
    }

    public void setHighScoreBooks(int highScoreBooks) {
        this.highScoreBooks = highScoreBooks;
    }

    public int getHighScoreCelebrities() {
        return highScoreCelebrities;
    }

    public void setHighScoreCelebrities(int highScoreCelebrities) {
        this.highScoreCelebrities = highScoreCelebrities;
    }

    public int getHighScoreMovies() {
        return highScoreMovies;
    }

    public void setHighScoreMovies(int highScoreMovies) {
        this.highScoreMovies = highScoreMovies;
    }

    public int getHighScoreCountries() {
        return highScoreCountries;
    }

    public void setHighScoreCountries(int highScoreCountries) {
        this.highScoreCountries = highScoreCountries;
    }

    public int getHighScoreHistoryFigures() {
        return highScoreHistoryFigures;
    }

    public void setHighScoreHistoryFigures(int highScoreHistoryFigures) {
        this.highScoreHistoryFigures = highScoreHistoryFigures;
    }

    public int getHighScoreAnimals() {
        return highScoreAnimals;
    }

    public void setHighScoreAnimals(int highScoreAnimals) {
        this.highScoreAnimals = highScoreAnimals;
    }

    private int highScoreBooks = 0;

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public HistoryStatistics(String userName){
        userId = userName;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalGamesPlayed() {
        return gameHistoryList.size();
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

    public void addScoreStatisticsToHistory(ScoreStatistics scoreStatistics){
        gameHistoryList.add(scoreStatistics);
    }
    public ArrayList<ScoreStatistics> getGameHistoryList() {
        return gameHistoryList;
    }
}
