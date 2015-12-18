package com.andrew.softwaredesign.guessagain;

import com.andrew.softwaredesign.guessagain.Extras.CircularTimer;
import com.andrew.softwaredesign.guessagain.Extras.InitialCountdown;
import com.andrew.softwaredesign.guessagain.Fragments.FinishedGameFragment;
import com.andrew.softwaredesign.guessagain.Statistics.HistoryStatistics;
import com.andrew.softwaredesign.guessagain.Statistics.ScoreStatistics;
import com.andrew.softwaredesign.guessagain.util.ShakeEventListener;
import com.andrew.softwaredesign.guessagain.util.SystemUiHider;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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
    private String userName = "";
    private String historyJson;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private long lastTime = 0;
    private int highScoreValue;
    private int totalPasses = 0;
    private int currentCategoryHigh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_screen);

        /*SENSOR TO DETECT SHAKING*/
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        int choice = getIntent().getIntExtra("Gamechoice", 1);
        userName = getIntent().getStringExtra("User");
        historyJson = getIntent().getStringExtra("History");

        if(historyJson.length() > 0){
            Log.d("Debug history", historyJson);
            Gson gson = new Gson();
            HistoryStatistics history = gson.fromJson(historyJson, new TypeToken<HistoryStatistics>(){}.getType());
            historyStatistics = history;
        }else{
            historyStatistics = new HistoryStatistics(userName);
        }

        launcherGameWords(choice);
        initStatTrack();
    }

    public HistoryStatistics getHistoryStats(){
        return historyStatistics;
    }
    public ScoreStatistics getScoreStats(){
        return scoreStatistics;
    }

    public int getHighScore(){
        return highScoreValue;
    }

    public void setHighScore(int score){
        highScoreValue = score;
    }

    private void launcherGameWords(final int choice) {
        showChoices = (TextView) findViewById(R.id.fullScreenTextChoices);

        final String firstWord = makeDecisionForChoice(choice);

        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(600);
        donutProgress.setSuffixText("");
        donutProgress.setTextColor(Color.TRANSPARENT);
        donutProgress.setFinishedStrokeColor(Color.rgb(211,84,0));
        donutProgress.setFinishedStrokeWidth(20);
        donutProgress.setUnfinishedStrokeColor(Color.rgb(243,156,18));
        donutProgress.setUnfinishedStrokeWidth(10);

        if(currentCategoryHigh > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final Context c = this;
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    InitialCountdown myCaller = new InitialCountdown(c, showChoices, firstWord, choice);
                    myCaller.execute(choice);

                    CircularTimer circleTimer = new CircularTimer(c, donutProgress);
                    circleTimer.execute();
                }
            });
            builder.setMessage("Your best score for this category is "+String.valueOf(currentCategoryHigh)+"!")
                    .setTitle(gameChoice);

            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            InitialCountdown myCaller = new InitialCountdown(this, showChoices, firstWord, choice);
            myCaller.execute(choice);

            CircularTimer circleTimer = new CircularTimer(this, donutProgress);
            circleTimer.execute();
        }



    }

    public void setChangeChoiceListener(final int choice){
        showChoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreStatistics.increaseCurrentCorrect();
                Log.d("Debug score", String.valueOf(scoreStatistics.getCurrentCorrect()));
                String newWord = makeDecisionForChoice(choice);
                showChoices.setText(newWord);
            }
        });

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {

                long currentTime = System.nanoTime();
                long difference = (currentTime - lastTime) / 1000000000;
                Log.d("Debug time difference", ((currentTime - lastTime) / 1000000000) + "s");

                if(difference >= 2){
                    totalPasses++;
                    Toast.makeText(GameScreen.this, "PASS!", Toast.LENGTH_SHORT).show();
                    String newWord = makeDecisionForChoice(choice);
                    showChoices.setText(newWord);
                }
                lastTime = System.nanoTime();
            }
        });
    }
    public void makePopup(){
        FragmentManager fragManager = getSupportFragmentManager();
        FinishedGameFragment popup = new FinishedGameFragment();

        Log.d("Debug total passes", String.valueOf(totalPasses));
        saveStatistics();

        saveHistoryToAndroid();
        popup.show(fragManager, "popup");
    }

    private void saveStatistics() {
        historyStatistics.addScoreStatisticsToHistory(scoreStatistics);
        if(historyStatistics.getHighScore() < scoreStatistics.getCurrentCorrect()){
            setHighScore(historyStatistics.getHighScore());
            historyStatistics.setHighScore(scoreStatistics.getCurrentCorrect());
        }
        if(currentCategoryHigh < scoreStatistics.getCurrentCorrect()){
            currentCategoryHigh = scoreStatistics.getCurrentCorrect();
            switch (gameChoice){
                case "Celebrities":
                    historyStatistics.setHighScoreCelebrities(currentCategoryHigh);
                    break;
                case "Movies":
                    historyStatistics.setHighScoreMovies(currentCategoryHigh);
                    break;
                case "Countries":
                    historyStatistics.setHighScoreCountries(currentCategoryHigh);
                    break;
                case "Famous History Figures":
                    historyStatistics.setHighScoreHistoryFigures(currentCategoryHigh);
                    break;
                case "Endangered Animals":
                    historyStatistics.setHighScoreAnimals(currentCategoryHigh);
                    break;
                case "Books":
                    historyStatistics.setHighScoreBooks(currentCategoryHigh);
                    break;
            }
        }
    }

    private void saveHistoryToAndroid() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(historyStatistics, new TypeToken<HistoryStatistics>(){}.getType());
        prefsEditor.putString(historyStatistics.getUserId()+"SavedHistory", json);
        prefsEditor.putString("LastUser", historyStatistics.getUserId());
        prefsEditor.commit();
    }

    private String makeDecisionForChoice(int choice){
        showChoices.setTextSize(50);
        String deckOfChoice = "Error";
        switch (choice){
            case 0:
                gameChoice = "Celebrities";
                currentCategoryHigh = historyStatistics.getHighScoreCelebrities();
                deckOfChoice = makeList("celebritiesList.txt");
                break;
            case 1:
                gameChoice = "Movies";
                currentCategoryHigh = historyStatistics.getHighScoreMovies();
                deckOfChoice = makeList("moviesList.txt");
                break;
            case 2:
                gameChoice = "Countries";
                currentCategoryHigh = historyStatistics.getHighScoreCountries();
                deckOfChoice = makeList("countriesList.txt");
                break;
            case 3:
                gameChoice = "Famous History Figures";
                currentCategoryHigh = historyStatistics.getHighScoreHistoryFigures();
                deckOfChoice = makeList("famousPeopleList.txt");
                break;
            case 4:
                gameChoice = "Endangered Animals";
                currentCategoryHigh = historyStatistics.getHighScoreAnimals();
                deckOfChoice = makeList("endangeredAnimalsList.txt");
                break;
            case 5:
                showChoices.setTextSize(30);
                gameChoice = "Books";
                currentCategoryHigh = historyStatistics.getHighScoreBooks();
                deckOfChoice = makeListWithAuthor("booksList.txt");
                break;
        }
        return deckOfChoice;
    }

    private String makeListWithAuthor(String textFile) {
        BufferedReader reader;
        String line = "";
        ArrayList<String> rows = new ArrayList<>();
        int select = 0;
        String[] returnedArray = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(textFile)));

            int i = 0;
            String fullLine = "";
            while((line = reader.readLine()) != null) {
                if(i == 0){
                    fullLine = line+"\n";
                }
                if(i == 1){
                    fullLine += "By: "+line;
                }
                i++;
                if(i == 2){
                    rows.add(fullLine);
                    i = 0;
                }
            }
            returnedArray = (rows.toArray(new String[rows.size()]));
            Random random = new Random();

            select = random.nextInt(returnedArray.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnedArray[select];
    }

    private void initStatTrack() {
        scoreStatistics = new ScoreStatistics();
        scoreStatistics.setUserId(userName);
        scoreStatistics.setGameType(gameChoice);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}