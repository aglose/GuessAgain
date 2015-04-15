package com.andrew.softwaredesign.guessagain;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class DeckScreen extends Activity {
    private boolean[] selectedItems = {false, false, false, false, false, false};
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_screen);

        playButton = (Button) findViewById(R.id.beginGameButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = 0;
                for (int i = 0; i<selectedItems.length; i++){
                    if(selectedItems[i]){
                        selected++;
                    }
                }
                if(selected == 1){
                    int choice = 0;
                    for (int i = 0; i<selectedItems.length; i++){
                        if(selectedItems[i]){
                            choice = i;
                        }
                    }
                    Intent gamePlay = new Intent(getApplicationContext(), GameScreen.class);
                    gamePlay.putExtra("Gamechoice", choice);
                    startActivity(gamePlay);
                }else{
                    Toast.makeText(DeckScreen.this, "Please only select one Deck", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(getApplicationContext()));
        gridview.setNumColumns(3);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flipping_background);
                anim.setTarget(v);
                anim.setDuration(1000);
                anim.start();

                Resources res = getResources();

                Drawable backgroundsReg[] = new Drawable[2];
                backgroundsReg[0] = res.getDrawable(R.drawable.blue);
                backgroundsReg[1] = res.getDrawable(R.drawable.red);

                Drawable backgroundsReverse[] = new Drawable[2];
                backgroundsReverse[0] = res.getDrawable(R.drawable.red);
                backgroundsReverse[1] = res.getDrawable(R.drawable.blue);

                Drawable color =  v.findViewById(R.id.categoryImageView).getBackground();
                Log.d("Debug", String.valueOf(color instanceof TransitionDrawable));
                if (color instanceof TransitionDrawable){
                    ((TransitionDrawable) color).reverseTransition(1000);
                    Log.d("Debug", "secondPress.");

                    if(selectedItems[position] == true){
                        selectedItems[position] = false;
                    }else{
                        selectedItems[position] = true;
                    }
                }else{
                    selectedItems[position] = true;
                    Log.d("Debug", "firstPress");
                    TransitionDrawable crossfader = new TransitionDrawable(backgroundsReg);
                    v.findViewById(R.id.categoryImageView).setBackground(crossfader);
                    crossfader.startTransition(1000);
                }


                String randomSelection = "";
                switch (position){
                    case 0:
                        randomSelection = "You chose Celebrities";
                        break;
                    case 1:
                        randomSelection = "You chose Movies";
                        break;
                    case 2:
//                        randomSelection = celebrityIntent();
                        break;
                }

                Toast.makeText(DeckScreen.this, randomSelection, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
