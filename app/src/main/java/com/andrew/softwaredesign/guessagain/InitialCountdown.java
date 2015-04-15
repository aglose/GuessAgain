package com.andrew.softwaredesign.guessagain;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Andrew on 3/25/2015.
 */
public class InitialCountdown extends AsyncTask<Integer, Integer, Integer>
{
    TextView showChoices;
    String firstWord;
    public InitialCountdown(TextView showChoices, String firstWord){
        this.showChoices = showChoices;
        this.firstWord = firstWord;
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
    }

}