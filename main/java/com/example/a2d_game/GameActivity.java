package com.example.a2d_game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import static com.example.a2d_game.GameView.screenX;
import static com.example.a2d_game.GameView.wizzard;

public class GameActivity extends AppCompatActivity {


  private  GameView gameView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            View decorView = getWindow().getDecorView(); //Make gameActivity Full screen
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        MediaPlayer zombie_sounds = MediaPlayer.create(GameActivity.this,R.raw.zombie_sounds);
        zombie_sounds.start();






        Point point = new Point() ; // to point to screenX screen Y
        getWindowManager().getDefaultDisplay().getSize(point); //
        gameView = new GameView(this , point.x , point.y) ;
        setContentView(gameView); // show on scrren

        }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume () {
        super.onResume();
        gameView.resume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN :
                if (event.getX() < screenX / 2) { //check if the user is clicking on the left side of the screen
                wizzard.isGooingUp=  true ;}

                break;
                case MotionEvent.ACTION_UP: ///check if the user is clicking on the right side of the screen
                    wizzard.isGooingUp= false ;
                    if (event.getX() > screenX / 2 )
                        wizzard.toAttack ++ ;
                    break;



        }
        return true ;

    } // on touch even to realise when the user is clicking the screen


}
