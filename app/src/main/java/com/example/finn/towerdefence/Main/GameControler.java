package com.example.finn.towerdefence.Main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.finn.towerdefence.Level.LevelManager;

/**
 * Created by Finn on 2017-09-13.
 */

public class GameControler extends SurfaceView implements Runnable{

    public static volatile int SCREEN_WIDTH, SCREEN_HEIGHT, GAMESTATE;

    volatile static boolean running;

    private static Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int frames, ups;

    private LevelManager lm;

    public GameControler(int screenWidth, int screenHeight) {
        super(GameActivity.CONTEXT);

        SCREEN_HEIGHT = screenHeight;
        SCREEN_WIDTH = (int)(screenHeight*1644.0/800.0);

        surfaceHolder = getHolder();
        paint = new Paint();

        lm = new LevelManager(GameControler.GAMESTATE);

    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double ms = 1000000000.0/60.0;
        double delta = 0;
        int tups = 0, tframes = 0;
        while(running){

            long now = System.nanoTime();
            delta += (now-lastTime)/ms;
            lastTime = now;
            while(delta >= 1){
                update();
                tups++;

                delta--;
            }

            draw();
            tframes++;

            if(System.currentTimeMillis()-timer >= 1000){
                timer+=1000;
                ups = tups;
                frames = tframes;
                tups = 0;
                tframes = 0;
            }

        }

    }

    private void update(){

        lm.update();

    }

    private void draw(){

        if(surfaceHolder.getSurface().isValid()){

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);

            lm.draw(canvas);

            paint.setColor(Color.RED);
            paint.setTextSize(40.0f);

            canvas.drawText("UPS: " + ups + " | FPS: " + frames, SCREEN_WIDTH-400, 40, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void pause(){

        lm.setPaused(true);
        running = false;

    }

    public void resume(){

        running = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    public boolean onTouchEvent(MotionEvent e){

        Inputs.setMotionEvent(e);

        return true;
    }

}
