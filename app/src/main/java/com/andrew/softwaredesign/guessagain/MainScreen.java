package com.andrew.softwaredesign.guessagain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;


public class MainScreen extends Activity {
    private ProgressDialog progressDialog;

    Button playButton;
    Button aboutButton;
    Button exitButton;
    Button popupButton;
    TextView popUpText;
    PopupWindow popupMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        new LoadBackgroundTask().execute();

        setContentView(R.layout.activity_main_screen);

        playButton = (Button) findViewById(R.id.playButton);
        aboutButton = (Button) findViewById(R.id.aboutButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        popUpText = (TextView) findViewById(R.id.popUpAboutText);
        popupButton = (Button) findViewById(R.id.popUpAboutButton);




        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to next Activity
                Intent intentDeck = new Intent(getApplicationContext(), DeckScreen.class);
                startActivity(intentDeck);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popup dialog describing game

                Toast.makeText(getApplicationContext(), "Pressed About", Toast.LENGTH_LONG).show();
//                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = inflater.inflate(R.layout.popup_about_layout, null);
//                popupMessage = new PopupWindow(getApplicationContext());
//                popupMessage.setContentView(view);
//
//                popupButton.setText("OK");
//                popUpText.setText("This is Popup Window.press OK to dismiss         it.");
//                popUpText.setPadding(0, 0, 0, 20);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class LoadBackgroundTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected void onPreExecute()
        {
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                synchronized (this)
                {
                    int counter = 0;
                    while(counter <= 4)
                    {
                        this.wait(50);
                        counter++;
                        publishProgress(counter*25);
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
        protected void onProgressUpdate(Integer... values)
        {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dismissProgressDialog();
        }
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainScreen.this);
            progressDialog.setMessage("Loading. Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

}
