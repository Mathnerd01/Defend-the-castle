package com.example.a2d_game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.file.attribute.UserPrincipalLookupService;

import static com.example.a2d_game.R.id.*;

public class MainActivity extends AppCompatActivity {

  private  boolean isMute;
        ImageView volumeCtrl;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer music = MediaPlayer.create(MainActivity.this,R.raw.music);

        music.start(); // play music





        View decorView = getWindow().getDecorView() ; //make main activity Full Screen Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);




            // Push play to start the game

            findViewById(play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,GameActivity.class));


                }
            });

            TextView highScoreTxt = findViewById(Highscore) ;
            SharedPreferences prefs = getSharedPreferences("game" , MODE_PRIVATE) ;
            highScoreTxt.setText("Hightscore" + prefs.getInt(":" , 0 ));


        }
    }
}