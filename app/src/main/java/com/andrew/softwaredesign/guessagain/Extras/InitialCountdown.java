package com.andrew.softwaredesign.guessagain.Extras;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.softwaredesign.guessagain.GameScreen;

/**
 * Created by Andrew on 3/25/2015.
 */
public class InitialCountdown extends AsyncTask<Integer, Integer, Integer>
{
    TextView showChoices;
    String firstWord;
    Context context;
    int choice;

    public InitialCountdown(Context context, TextView showChoices, String firstWord, int choice){
        this.context = context;
        this.showChoices = showChoices;
        this.firstWord = firstWord;
        this.choice = choice;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Integer doInBackground(Integer... params) {
        try
        {
            synchronized (this)
            {

                int counter = 3;
                while(counter > 0)
                {
                    publishProgress(counter);
                    Log.d("Debug", "counter = " + counter);
                    this.wait(1000);
                    counter--;
                }
                publishProgress(counter);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return params[0];
    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        showChoices.setText(""
                + progress[0]);

    }
    @Override
    protected void onPostExecute(Integer choice) {
        super.onPostExecute(choice);
        showChoices.setText(firstWord);
        ((GameScreen) context).setChangeChoiceListener(choice);
    }

}