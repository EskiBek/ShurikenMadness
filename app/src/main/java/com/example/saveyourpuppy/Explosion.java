package com.example.saveyourpuppy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {
    Bitmap explosions[] = new Bitmap[4];
    int explosionFrame = 0;
    int explosionX, explosionY;

    public Explosion(Context context){
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion0);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion3);
    }

    public Bitmap getExplosion(int explosionFrame){
        return explosions[explosionFrame];
    }
}
