package com.andrew.softwaredesign.guessagain.Extras;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.andrew.softwaredesign.guessagain.GameScreen;
import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * Created by Andrew on 3/25/2015.
 */
public class CircularTimer extends AsyncTask<Void, Integer, Void>
{
    Context context;
    DonutProgress donutProgress;
    public CircularTimer(Context context, DonutProgress donutProgress){
        this.context = context;
        this.donutProgress = donutProgress;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... params) {
        try
        {
            synchronized (this)
            {
                int counter = 600;
                while(counter > 0)
                {
                    publishProgress(counter);
                    Thread.sleep(1);
                    counter--;
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        donutProgress.setProgress(donutProgress.getProgress()+1);
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        ((GameScreen) context).makePopup();
    }

}
