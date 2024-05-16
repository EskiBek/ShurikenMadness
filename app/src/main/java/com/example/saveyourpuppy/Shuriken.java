package com.example.saveyourpuppy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Shuriken {
    Bitmap[] shurikens = new Bitmap[4];
    int shurikenFrame = 0;
    int shurikenX, shurikenY, shurikenVelocity;
    Random random;

    public Shuriken(Context context) {
        shurikens[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.shuriken0);
        shurikens[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.shuriken1);
        shurikens[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.shuriken2);
        shurikens[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.shuriken3);
        random = new Random();
        resetPosition();
    }

    public Bitmap getShuriken(int shurikenFrame){
        return shurikens[shurikenFrame];
    }

    public int getShurikenWidth(){
        return shurikens[0].getWidth();
    }

    public int getShurikenHeight(){
        return shurikens[0].getHeight();
    }

    public void resetPosition(){
        shurikenX = random.nextInt(GameView.dWidth - getShurikenWidth());
        shurikenY = -200 + random.nextInt(600)* -1;
        shurikenVelocity = 35 + random.nextInt(16);
    }
}
