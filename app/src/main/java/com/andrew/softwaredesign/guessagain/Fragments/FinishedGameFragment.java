package com.andrew.softwaredesign.guessagain.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.andrew.softwaredesign.guessagain.DeckScreen;
import com.andrew.softwaredesign.guessagain.GameScreen;
import com.andrew.softwaredesign.guessagain.MainScreen;
import com.andrew.softwaredesign.guessagain.R;
import com.andrew.softwaredesign.guessagain.Statistics.HistoryStatistics;
import com.andrew.softwaredesign.guessagain.Statistics.ScoreStatistics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class FinishedGameFragment extends DialogFragment {
    private TextView correctText;
    private TextView correctTitle;
    private TextView highScore;
    private TextView highScoreText;
    private Context context;
    private ScoreStatistics scoreStatistics;
    private HistoryStatistics historyStatistics;
    private AlertDialog.Builder builder;

    private int highScoreValue = 0;

    public FinishedGameFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();
        scoreStatistics = ((GameScreen) context).getScoreStats();
        historyStatistics = ((GameScreen) context).getHistoryStats();
        highScoreValue = ((GameScreen) context).getHighScore();

        builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_finished_game, null);
        builder.setView(view);

        correctText = (TextView) view.findViewById(R.id.userCorrectAmount);
        highScore = (TextView) view.findViewById(R.id.highScore);
        correctTitle = (TextView) view.findViewById(R.id.correctTitle);
        highScoreText = (TextView) view.findViewById(R.id.highScoreText);

        correctTitle.setPaintFlags(correctText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        correctText.setText(String.valueOf(scoreStatistics.getCurrentCorrect()));

        if(highScoreValue < scoreStatistics.getCurrentCorrect()){
            highScore.setText(String.valueOf(scoreStatistics.getCurrentCorrect()));
            highScoreText.setText("New High Score!");
        }else{
            highScore.setText(String.valueOf(highScoreValue));
        }

        highScoreText.setPaintFlags(highScoreText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Gson gson = new Gson();
        final String history = gson.toJson(historyStatistics, new TypeToken<HistoryStatistics>(){}.getType());
        builder.setMessage(R.string.finishedGame)
                .setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent gamePlay = new Intent(getActivity(), DeckScreen.class);
                        Log.d("Debug dialogEnd", String.valueOf(scoreStatistics.getUserId()));
                        gamePlay.putExtra("User", String.valueOf(scoreStatistics.getUserId()));
                        gamePlay.putExtra("History", history);
                        startActivity(gamePlay);
                    }
                }).setNegativeButton(R.string.returnHome, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent menu = new Intent(getActivity(), MainScreen.class);
                menu.putExtra("User", scoreStatistics.getUserId());
                startActivity(menu);
            }
        });;

        return builder.create();
    }




}
