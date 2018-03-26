package com.example.finn.towerdefence.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.finn.towerdefence.Level.LevelManager;
import com.example.finn.towerdefence.Level.Wave;
import com.example.finn.towerdefence.Main.GameActivity;

/**
 * Created by Finn on 2017-09-15.
 */

public abstract class Enemy {

    private Wave wave;

    private boolean alive = true, start = false;

    private int speed, health, maxHealth, x, y, size, dx, dy, value;

    private Rect bounds = null;

    protected Bitmap icon;

    public Enemy(int speed, int health, int x, int y, int dx, int dy, int iconID, Wave wave){

        this.speed = speed;
        this.health = health;
        maxHealth = health;
        this.x = x;
        this.y = y;
        size = (int)(LevelManager.tileSize * 1.0);

        this.dy = dy*speed;
        this.dx = dx*speed;

        this.wave = wave;

        value = 1;

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);

        bounds = new Rect();

        adjustSize();

    }

    protected void adjustSize(){

        icon = Bitmap.createScaledBitmap(icon, size, size, true);

    }

    public void update(){

        if(alive && start){

            if(health <= 0){
                wave.death(value);
                alive = false;
            }
            int tx = (int)((x+speed)/LevelManager.tileSize-1), ty = (int)((y+speed)/LevelManager.tileSize-1),
                tx2 = (int)((x + size - speed)/LevelManager.tileSize-1), ty2 = (int)((y+size-speed)/LevelManager.tileSize-1);

            if(tx>=LevelManager.mapWidth||ty>=LevelManager.mapHeight){
                alive = false;
                LevelManager.hurt();
                wave.death(0);
                return;
            }

            if(tx == tx2 && ty == ty2 && tx>=0&&ty>=0){
                switch(LevelManager.checkMap(tx,ty)){
                    case 1:
                        dx = speed;
                        dy = 0;
                        break;
                    case 2:
                        dx = 0;
                        dy = speed;
                        break;
                    case 3:
                        dx = -speed;
                        dy = 0;
                        break;
                    case 4:
                        dx = 0;
                        dy = -speed;
                        break;
                }
            }
            x+=dx;
            y+=dy;

            bounds.set(x,y,x+size, y+size);

        }

    }

    public void draw(Canvas canvas){

        if(alive && start){
            Paint p = new Paint();
            p.setColor(Color.RED);
            canvas.drawRect(x + (int)(size*0.1), y - (int)(size*0.2), x + (int)(size*0.9), y - (int)(size*0.1), p);
            p.setColor(Color.GREEN);
            canvas.drawRect(x + (int)(size*0.1), y - (int)(size*0.2), x + (int)(size*0.1) + (int)(size*0.8*health/maxHealth), y - (int)(size*0.1), p);
            canvas.drawBitmap(icon, x, y, new Paint());
        }

    }

    public void start(){
        start = true;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isStart(){
        return start;
    }

    public void hurt(int h){
        health -= h;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getValue(){return value;}

    public Rect getBounds(){
        return bounds;
    }

}
