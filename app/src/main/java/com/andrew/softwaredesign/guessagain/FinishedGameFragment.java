package com.andrew.softwaredesign.guessagain;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class FinishedGameFragment extends DialogFragment {
    private TextView correctText;
    private TextView correctTitle;
    private Context context;
    private ScoreStatistics scoreStatistics;
    private AlertDialog.Builder builder;
    public FinishedGameFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();
        scoreStatistics = ((GameScreen) context).getScoreStats();
        builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_finished_game, null);
        builder.setView(view);

        correctText = (TextView) view.findViewById(R.id.userCorrectAmount);
        correctText.setText(String.valueOf(scoreStatistics.getCurrentCorrect()));

        correctTitle = (TextView) view.findViewById(R.id.correctTitle);
        correctTitle.setPaintFlags(correctText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        builder.setMessage(R.string.finishedGame)
                .setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent gamePlay = new Intent(getActivity(), DeckScreen.class);
                        startActivity(gamePlay);
                    }
                }).setNegativeButton(R.string.returnHome, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent menu = new Intent(getActivity(), MainScreen.class);
                startActivity(menu);
            }
        });;

        return builder.create();
    }




}
