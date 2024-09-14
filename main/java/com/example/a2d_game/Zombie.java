package com.example.a2d_game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.a2d_game.GameView.screenRatioX;
import static com.example.a2d_game.GameView.screenRatioY;

public class Zombie {


    public int speed = 1 ;
    public boolean wasShot = true;
    int x = 0 ,y , width , height,  zombieCounter=1 ;
    Bitmap zombie1 , zombie2 , zombie3 , zombie4  ;

    Zombie(Resources res ) {

        zombie1 = BitmapFactory.decodeResource(res ,R.drawable.zombie1); // initialise zombie images
        zombie2 = BitmapFactory.decodeResource(res ,R.drawable.zombie2);
        zombie3 = BitmapFactory.decodeResource(res ,R.drawable.zombie3);
        zombie4 = BitmapFactory.decodeResource(res ,R.drawable.zombie4);



        width = zombie1.getWidth() ; // get width and height of images
        height = zombie1.getHeight();

        width /=10 ; // reduce the size of images
        height /=10;

        width *=(int)screenRatioX;
        height *=(int)screenRatioY;

        zombie1 = Bitmap.createScaledBitmap(zombie1 , width , height, false) ; //creat bitmaps
        zombie2 = Bitmap.createScaledBitmap(zombie2 , width , height, false) ;
        zombie3 = Bitmap.createScaledBitmap(zombie3 , width , height, false) ;
        zombie4 = Bitmap.createScaledBitmap(zombie4 , width , height, false) ;

        y = -height;
    }
    Bitmap  getZombie () {
        if (zombieCounter == 1 ) {
            zombieCounter ++ ;
            return zombie1 ;
        }
        if (zombieCounter == 2 ) {
            zombieCounter ++ ;
            return zombie2 ;
        }
        if (zombieCounter == 3 ) {
            zombieCounter ++ ;
            return zombie3 ;
        }

        zombieCounter = 1 ;

        return zombie4 ;

    } // creat the animation of the zombies
    Rect getHitBox() {  // the hit of box of zombies as a rectangle

        return new Rect(x , y , x + width , y + height) ;
    }


}
