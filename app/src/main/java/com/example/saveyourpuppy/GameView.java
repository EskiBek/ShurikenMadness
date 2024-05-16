package com.example.saveyourpuppy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {

    Bitmap background, ground, puppy;
    Rect rectBG, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthBar = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float puppyX, puppyY;
    float oldX;
    float oldPuppyX;
    ArrayList<Shuriken> shurikens;
    ArrayList<Explosion> explosions;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        puppy = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBG = new Rect(0, 0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthBar.setColor(Color.GREEN);
        random = new Random();
        puppyX = dWidth / 2 - puppy.getWidth() / 2;
        puppyY = dHeight - ground.getHeight() - puppy.getHeight();
        shurikens = new ArrayList<>();
        explosions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Shuriken shuriken = new Shuriken(context);
            shurikens.add(shuriken);
        }


    }

    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(background,null, rectBG,null);
        canvas.drawBitmap(ground,null,rectGround,null);
        canvas.drawBitmap(puppy,puppyX,puppyY,null);
        for (int i = 0; i < shurikens.size(); i++) {
            canvas.drawBitmap(shurikens.get(i).getShuriken(shurikens.get(i).shurikenFrame),shurikens.get(i).shurikenX, shurikens.get(i).shurikenY, null);
            shurikens.get(i).shurikenFrame++;
            if (shurikens.get(i).shurikenFrame > 2){
                shurikens.get(i).shurikenFrame = 0;
            }
            shurikens.get(i).shurikenY += shurikens.get(i).shurikenVelocity;
            if (shurikens.get(i).shurikenY + shurikens.get(i).getShurikenHeight() >= dHeight - ground.getHeight()){
                points+=10;
                @SuppressLint("DrawAllocation") Explosion exp = new Explosion(context);
                exp.explosionX = shurikens.get(i).shurikenX;
                exp.explosionY = shurikens.get(i).shurikenY;
                explosions.add(exp);
                shurikens.get(i).resetPosition();
            }
        }

        for (int i = 0; i < shurikens.size(); i++) {
            if (shurikens.get(i).shurikenX + shurikens.get(i).getShurikenWidth() >= puppyX && shurikens.get(i).shurikenX <= puppyX + puppy.getWidth() && shurikens.get(i).shurikenY + shurikens.get(i).getShurikenWidth() >= puppyY && shurikens.get(i).shurikenY + shurikens.get(i).getShurikenWidth() <= puppyY + puppy.getHeight()){
                life--;
                shurikens.get(i).resetPosition();
                if (life == 0){
                    @SuppressLint("DrawAllocation") Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("points",points);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame),explosions.get(i).explosionX, explosions.get(i).explosionY, null);
            explosions.get(i).explosionFrame++;
            if (explosions.get(i).explosionFrame > 3) {
                explosions.remove(i);
            }
        }

        if(life == 2){
            healthBar.setColor(Color.rgb(250,250,0));
        } else if (life == 1) {
            healthBar.setColor(Color.rgb(255,0,0));
        }
        canvas.drawRect(dWidth-200,30,dWidth-200+60*life, 80,healthBar);
        canvas.drawText(""+points, 20,TEXT_SIZE,textPaint);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= puppyY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldPuppyX = puppyX;
            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newPuppyX = oldPuppyX - shift;
                if (newPuppyX <= 0){
                    puppyX = 0;
                } else if (newPuppyX >= dWidth - puppy.getWidth()) {
                    puppyX = dWidth - puppy.getWidth();
                }
                else{
                    puppyX = newPuppyX;
                }
            }
        }
        return true;
    }

}
