package com.example.a2d_game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background { //Background Class

    int x= 0 , y =0;
    Bitmap background ;

    Background (int screenX , int screenY , Resources res ) // size of the screen on x and y
    {
        background = BitmapFactory.decodeResource(res ,R.drawable.back1) ; // initiate the Background image
        background= Bitmap.createScaledBitmap( background, screenX , screenY , false); //resize bitmap




    }
}
