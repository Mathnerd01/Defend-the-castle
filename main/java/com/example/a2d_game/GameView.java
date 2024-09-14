package com.example.a2d_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//*SurfaceView* takes care of placing the surface at the correct location on the screen.
//* Runnable interface should be implemented by any class whose instances are intended to be executed by a thread

public class GameView extends SurfaceView implements  Runnable {

    private Thread thread;
    private boolean isplaying , isGameOver = false;
    public static int screenX, screenY , score = 0;
    public  static   float screenRatioX , screenRatioY ;
    private Paint paint;
    private Random random ;
    private int sound ;
    private SoundPool soundPool ;
    private SharedPreferences prefs ;
    private List<Spell> spells ; // add a list of spells using list
    private Zombie[] zombies ; // array of zombies as items
    public  static   Wizzard wizzard ;
    private  GameActivity activity ;
    private Background background1, background2; // we need 2 instances to make background move


    public GameView(GameActivity activity, int screenX, int screenY) {

        super(activity);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  {


            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                                                           .setUsage(AudioAttributes.USAGE_GAME)
                                                                            .build() ;
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build() ;

        }else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0) ;


        sound=soundPool.load(activity ,R.raw.fireball , 1) ;

        this.screenX = screenX;
        this.screenY = screenY;
        this.activity = activity;
        screenRatioX = 2960f / screenX ;
        screenRatioY = 1440f /screenY ;

        prefs = activity.getSharedPreferences("game" , Context.MODE_PRIVATE) ; // hide the score for each new device

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        wizzard = new Wizzard(this,screenY , getResources()) ;
        spells =  new ArrayList<>() ;

        background2.x = screenX;
        paint = new Paint(); // the highscore counter
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        zombies = new Zombie[4] ; // 4 zombies at the screen at the timees

        for ( int i = 0 ; i < 4 ; i++){
            Zombie zombie = new Zombie(getResources());
            zombies[i] = zombie ;
        }
        random = new Random() ;
    }

    @Override
    public void run() {
        while (isplaying) {
            update () ;//update the position
            draw ();  //draw
            sleep (); // wait for 17 mili seconds

        }
    } // Run method

    private void update (){

        background1.x -= 10 * screenRatioX; // everytime method x is called background will movie by 10 pixels
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {// creat a loop so when our background1 is off the screen it will start again
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {// creat a loop so when our background2 is off the screen it will start again
            background2.x = screenX;
        }




        if (wizzard.isGooingUp) {
             wizzard.y -= 30 * screenRatioY ;
        }   // go up
        else {
            wizzard.y += 30 * screenRatioY ;
        }  // go down


        if (wizzard.y < 0 ) {
            wizzard.y = 0 ;
        }   //so the wizard doesn't not go off screen from the top
        if (wizzard.y >= screenY - wizzard.height ) {
            wizzard.y = screenY - wizzard.height ;
        } ///so the wizard doesn't not go off screen from the bottom

        List<Spell> trash = new ArrayList<>();
        for (Spell spell : spells ) {
            if(spell.x > screenX)
                trash.add(spell);
            spell.x += 50 *screenRatioX;
            for (Zombie zombie : zombies) {

                if (Rect.intersects( zombie.getHitBox() , spell.getHitBox())) {
                    score++ ; //reword the player with 1 point
                    zombie.x = -500 ; //dead zombies will stay out of the screen
                    spell.x =screenX +500 ; // used spells will stay out of the screen
                    zombie.wasShot = true ;
                }
            }

        } //// remove the spells if they are out of the screen and add them to trash list

        for (Spell spell : trash ) {

            spells.remove(spell);
        } //////remove spells in trash

        for (Zombie zombie :zombies) {
            zombie.x -= zombie.speed ;

            if (zombie.x + zombie.width < 0) { // if zombies are out of the screen
               if (!zombie.wasShot) {

                    isGameOver = true ;
                    return;
                } // // game is over when  a none shot zombie reaches the end of the screen


               int bound = (int) (30 * screenRatioX);
                zombie.speed = random.nextInt(bound) ; //randomize the speed of the zombies

                if (zombie.speed > (  3 * screenRatioX) ) // minimum speed of zombies
                    zombie.speed = (int) (2 * screenRatioX); //set the zombies speed to 2 if its higher than 3
                if (zombie.speed <  1)  //set the zombies speed to 2 if its lower than 1
                    zombie.speed = (int) (2 * screenRatioX);




                zombie.x = screenX ; // place the Zombies  at the right side  of the screen
                zombie.y =random.nextInt(screenY-zombie.height) ; //randomize the y-achse of zombies
                zombie.wasShot = false ;
            }

            if (Rect.intersects(wizzard.getHitBox() , zombie.getHitBox())) {
                isGameOver = true;;
                return;
            } //// if the zombies touche the wizard Game is over



        } ////update the zombies on the screen


    }

    private void draw(){

        if (getHolder().getSurface().isValid()) {             /// get the Canvas that is displayed on the screen




            Canvas canvas = getHolder().lockCanvas(); //lock canvas


            canvas.drawBitmap(background1.background, background1.x, background1.y, paint); //draw background1
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint); //draw background2
            canvas.drawBitmap(wizzard.getWizard(), wizzard.x, wizzard.y, paint); // draw wizard
             canvas.drawText(score + "", screenX/2f , 164, paint) ; // draw score

             for (Zombie zombie :zombies) {
                 canvas.drawBitmap(zombie.getZombie() , zombie.x , zombie.y , paint);
             } // draw zombies


            if (isGameOver) {

                isplaying = false;
                canvas.drawBitmap(wizzard.getDead(), wizzard.x, wizzard.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore() ;
                waitBeforeExiting();
                return;
            }  // draw death image / check new score


            for (Spell spell:spells) { // draw spells
                canvas.drawBitmap(spell.spell, spell.x, spell.y, paint);
            }


            getHolder().unlockCanvasAndPost(canvas);//unlock canvas
        }


    } //draw All objects on the screen

    private void waitBeforeExiting() {

        try {
            thread.sleep(3000);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {

        if (prefs.getInt("Highscore" , 0 ) < score) {

            SharedPreferences.Editor editor = prefs.edit() ;
            editor.putInt("highscore" + "", score) ;
            editor.apply();

        }
    } // save the score if its the highest one yet

    private void sleep (){

        try {
            thread.sleep(17); // 1000 milis / 60 milis = 17 milis
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void resume() {
        isplaying = true; //isplaying true when the game resumes
        thread = new Thread(this);
        thread.start();
    }  //Resume GAme when this methode is called

    public void pause() {
        isplaying = false;
        try {
            thread.join(); //stop the thread
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }  //Pause GAme when this methode is called

    public void newSpell() {

        Spell spell = new Spell(getResources()) ;

                 if (!prefs.getBoolean("isMute" , false))
                     soundPool.play(sound, 1 , 1 , 0 , 0 , 1 ) ;


                spell.x=wizzard.x + wizzard.width ;  //place the spell near the wizard
                spell.y=wizzard.y + (wizzard.height / 2);
                spells.add(spell) ; //add this spell to our list

    } // play the spell sound effect
}
