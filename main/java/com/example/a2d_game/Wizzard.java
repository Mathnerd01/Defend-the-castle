package com.example.a2d_game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.a2d_game.GameView.screenRatioX;
import static com.example.a2d_game.GameView.screenRatioY;

public class Wizzard {
    int toAttack = 0;
    boolean isGooingUp = false;
    GameView gameView;
    int x, y, width, height, counter = 0, attackCounter = 1;
    Bitmap wizzard1, wizzard2, attack1, attack2, attack3, attack4, attack5 , dead;

    Wizzard(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;

        wizzard1 = BitmapFactory.decodeResource(res, R.drawable.wizzard1);// initialise wizard 1
        wizzard2 = BitmapFactory.decodeResource(res, R.drawable.wizzard2);// initialise wizard 2


        width = wizzard1.getWidth();
        height = wizzard1.getHeight(); //get the height and height of images wizard1 ;

        wizzard1 = Bitmap.createScaledBitmap(wizzard1, width, height, false); // resize the bitmap of wizard1
        wizzard2 = Bitmap.createScaledBitmap(wizzard2, width, height, false); // resize the bitmap of wizard2


        attack1 = BitmapFactory.decodeResource(res, R.drawable.attack1); //initialise attack images
        attack2 = BitmapFactory.decodeResource(res, R.drawable.attack2);
        attack3 = BitmapFactory.decodeResource(res, R.drawable.attack3);
        attack4 = BitmapFactory.decodeResource(res, R.drawable.attack4);
        attack5 = BitmapFactory.decodeResource(res, R.drawable.attack5);
        dead = BitmapFactory.decodeResource(res , R.drawable.dead) ;


        attack1 = Bitmap.createScaledBitmap(attack1, width, height, false); // resize attack images
        attack2 = Bitmap.createScaledBitmap(attack2, width, height, false);
        attack3 = Bitmap.createScaledBitmap(attack3, width, height, false);
        attack4 = Bitmap.createScaledBitmap(attack4, width, height, false);
        attack5 = Bitmap.createScaledBitmap(attack5, width, height, false);
        dead =Bitmap.createScaledBitmap(dead ,width ,height  ,false) ;

        y = screenY;  //starting position  in y aches_ ;
        x = (int) (screenRatioX - 100); // starting position in x aches


        width /= 4;
        height /= 2.7; // cause the images are too big for our screen

        width *= (int) screenRatioX; //
        height *= (int) screenRatioY;




    }


    Bitmap getWizard() {
        if (toAttack != 0) {
            if (attackCounter == 1) {

                attackCounter++;
                return attack1;
            }
            if (attackCounter == 2) {

                attackCounter++;
                return attack2;
            }
            if (attackCounter == 3) {

                attackCounter++;
                return attack3;
            }
            if (attackCounter == 4) {

                attackCounter++;
                return attack4;
            }
            attackCounter = 1;
            toAttack--;
            gameView.newSpell();

        }


        if (counter == 0) {
            counter++;
            return wizzard1;
        }
        counter--;
        return wizzard2;


    } // chain to creat the animation of the wizard

    Rect getHitBox() {

        return new Rect(x , y , x + width , y + height ) ;
    } // creat a hit box of the wizard

    Bitmap getDead () {
        return  dead ;
    }
}

















