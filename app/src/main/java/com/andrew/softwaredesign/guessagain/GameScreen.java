package com.andrew.softwaredesign.guessagain;

import com.andrew.softwaredesign.guessagain.util.SystemUiHider;
import com.github.lzyzsd.circleprogress.DonutProgress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends FragmentActivity {
    private DonutProgress donutProgress;
    private TextView showChoices;
    private ScoreStatistics scoreStatistics;
    private HistoryStatistics historyStatistics;
    private String gameChoice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_screen);

        int choice = getIntent().getIntExtra("Gamechoice", 1);

        launcherGameWords(choice);
        initStatTrack();
    }

    public ScoreStatistics getScoreStats(){
        return scoreStatistics;
    }

    private void launcherGameWords(final int choice) {
        showChoices = (TextView) findViewById(R.id.fullScreenTextChoices);

        String firstWord = makeDecisionForChoice(choice);
        InitialCountdown myCaller = new InitialCountdown(showChoices, firstWord);
        myCaller.execute(choice);

        showChoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreStatistics.increaseCurrentCorrect();
                Log.d("Debug score", String.valueOf(scoreStatistics.getCurrentCorrect()));
                String newWord = makeDecisionForChoice(choice);
                showChoices.setText(newWord);
            }
        });

        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(600);
        donutProgress.setSuffixText("");
        donutProgress.setTextColor(Color.TRANSPARENT);
        donutProgress.setFinishedStrokeColor(Color.rgb(211,84,0));
        donutProgress.setFinishedStrokeWidth(20);
        donutProgress.setUnfinishedStrokeColor(Color.rgb(243,156,18));
        donutProgress.setUnfinishedStrokeWidth(10);

        CircularTimer circleTimer = new CircularTimer(this, donutProgress);
        circleTimer.execute();
    }

    public void makePopup(){
        FragmentManager fragManager = getSupportFragmentManager();
        FinishedGameFragment popup = new FinishedGameFragment();

        popup.show(fragManager, "popup");
    }


    private String makeDecisionForChoice(int choice){
        String deckOfChoice = "Error";
        switch (choice){
            case 0:
                gameChoice = "Celebrity";
                deckOfChoice = makeList("celebritiesList.txt");
                break;
            case 1:
                gameChoice = "Movie";
                deckOfChoice = makeList("moviesList.txt");
                break;
            case 2:
                gameChoice = "Countries";
                deckOfChoice = makeList("countriesList.txt");
                break;
            case 3:
                gameChoice = "Famous History Figures";
                deckOfChoice = makeList("famousPeopleList.txt");
                break;
            case 4:
                gameChoice = "Endangered Animals";
                deckOfChoice = makeList("endangeredAnimalsList.txt");
                break;
        }
        return deckOfChoice;
    }

    private void initStatTrack() {
        scoreStatistics = new ScoreStatistics(gameChoice);
        historyStatistics = new HistoryStatistics();
        historyStatistics.increaseTotalGamesPlayed();
    }

    private String makeList(String textFile){
        BufferedReader reader;
        String line = "";
        ArrayList<String> rows = new ArrayList<>();
        int select = 0;
        String[] returnedArray = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(textFile)));

            while((line = reader.readLine()) != null){
                rows.add(line);
            }
            returnedArray = (rows.toArray(new String[rows.size()]));
            Random random = new Random();

            select = random.nextInt(returnedArray.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnedArray[select];
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}