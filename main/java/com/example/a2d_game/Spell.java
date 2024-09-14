package com.example.a2d_game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.a2d_game.GameView.screenRatioX;
import static com.example.a2d_game.GameView.screenRatioY;

public class Spell { // spell class

    int x , y  , width , height;
    Bitmap spell ;




      Spell (Resources res ) {

          spell = BitmapFactory.decodeResource(res , R.drawable.spell) ;//constructor of spell image

             width = spell.getWidth() ;    //get the height and width of the image spell
             height = spell.getHeight() ;


              width /= 10; // reduce the width and height of spell.png
              height /=10 ;

              width *=(int)screenRatioX ;
              height *=(int)screenRatioY ;

              spell = Bitmap.createScaledBitmap(spell , width , height, false) ; //resize bitmap
      }

    Rect getHitBox() {

        return new Rect(x , y , x + width , y + height) ;
    } //// the hit of box of spells
}
