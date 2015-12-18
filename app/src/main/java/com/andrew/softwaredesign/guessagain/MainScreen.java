package com.andrew.softwaredesign.guessagain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.andrew.softwaredesign.guessagain.Fragments.AboutFragment;
import com.andrew.softwaredesign.guessagain.Fragments.FinishedGameFragment;
import com.andrew.softwaredesign.guessagain.Fragments.UserFragment;


public class MainScreen extends FragmentActivity implements UserFragment.NoticeDialogListener{
    private ProgressDialog progressDialog;

    Button playButton;
    Button aboutButton;
    Button exitButton;
    TextView popUpText;
    TextView loggedInUser;
    TextView loginTitle;
    String lastUser;

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
        loggedInUser = (TextView) findViewById(R.id.userId);


        boolean firstTime = checkForLastUser();

        if(firstTime){
            loggedInUser.setVisibility(View.INVISIBLE);
        }else{
            loggedInUser.setVisibility(View.VISIBLE);
        }

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popup dialog describing game
                FragmentManager fragManager = getSupportFragmentManager();
                AboutFragment popup = new AboutFragment();
                popup.show(fragManager, "about");
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean checkForLastUser() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.clear().commit();
        lastUser = appSharedPrefs.getString("LastUser", "");

        final String finalUser = lastUser;

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalUser.length() == 0){
                    FragmentManager fragManager = getSupportFragmentManager();
                    UserFragment popup = new UserFragment();
                    popup.show(fragManager, "signIn");
                }else{
                    createGameScreen();
                }
            }
        });

        if(lastUser.length() == 0){
            return true;
        }
        loggedInUser.append(lastUser+"!");
        return false;
    }

    private void createGameScreen(){
        //intent to next GameScreen
        Intent intentDeck = new Intent(getApplicationContext(), DeckScreen.class);
        intentDeck.putExtra("User", lastUser);
        startActivity(intentDeck);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Dialog d = dialog.getDialog();
        EditText user = (EditText) d.findViewById(R.id.username);
        loggedInUser.setText(user.getText().toString());
        lastUser = user.getText().toString();
        createGameScreen();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

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
