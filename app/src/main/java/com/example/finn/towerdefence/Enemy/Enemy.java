package com.example.finn.towerdefence.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.finn.towerdefence.Level.LevelManager;
import com.example.finn.towerdefence.Main.GameActivity;

/**
 * Created by Finn on 2017-09-15.
 */

public abstract class Enemy {

    private boolean alive = true, start = false;

    private int speed, health, x, y, size, dx, dy;

    private Rect bounds = null;

    protected Bitmap icon;

    public Enemy(int speed, int health, int x, int y, int dx, int dy, int iconID){

        this.speed = speed;
        this.health = health;
        this.x = x;
        this.y = y;
        size = (int)(LevelManager.tileSize * 1.0);

        this.dy = dy*speed;
        this.dx = dx*speed;

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);

        bounds = new Rect();

        adjustSize();

    }

    protected void adjustSize(){

        icon = Bitmap.createScaledBitmap(icon, size, size, true);

    }

    public void update(){

        if(health <= 0)alive = false;

        if(alive && start){
            int tx = (int)((x+speed)/LevelManager.tileSize-1), ty = (int)((y+speed)/LevelManager.tileSize-1),
                tx2 = (int)((x + size - speed)/LevelManager.tileSize-1), ty2 = (int)((y+size-speed)/LevelManager.tileSize-1);

            if(tx>=LevelManager.mapWidth||ty>=LevelManager.mapHeight){
                alive = false;
                LevelManager.hurt();
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

        if(alive && start)canvas.drawBitmap(icon, x, y, new Paint());

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

    public Rect getBounds(){
        return bounds;
    }

}
