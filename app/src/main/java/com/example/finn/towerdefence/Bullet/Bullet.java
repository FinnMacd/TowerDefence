package com.example.finn.towerdefence.Bullet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.finn.towerdefence.Enemy.Enemy;
import com.example.finn.towerdefence.Level.LevelManager;
import com.example.finn.towerdefence.Level.Wave;
import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-18.
 */

public class Bullet {

    private Bitmap icon;

    private int speed, damage;
    private double x, y, dx, dy, size;
    private boolean alive = true;
    private long deathTime = 0;
    private Rect bounds;

    private Wave currentWave;

    public Bullet(int iconID, int speed, int damage, int x, int y, double dx, double dy){

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);

        size = LevelManager.tileSize/2;
        this.speed = speed;
        this.damage = damage;
        this.x = x-size/2;
        this.y = y-size/2;
        this.dx = dx;
        this.dy = dy;
        System.out.println(dx);

        icon = Bitmap.createScaledBitmap(icon, (int)(size), (int)(size), true);

        bounds = new Rect(x, y, icon.getWidth() + x, icon.getHeight() + y);

    }

    public void update(){

        x+=dx*speed;
        y+=dy*speed;

        if(deathTime == 0 && !LevelManager.levelBounds.contains((int)x, (int)y)){
            deathTime = System.currentTimeMillis();
        }

        if(deathTime != 0 && System.currentTimeMillis() - deathTime > 2000)alive = false;

        bounds.set((int)x, (int)y, icon.getWidth() + (int)x, icon.getHeight() + (int)y);

        if(LevelManager.isRunning()){
            for(Enemy e: currentWave.getEnemies()){
                if(e.isAlive()&& e.isStart() && Rect.intersects(e.getBounds(), bounds)){
                    e.hurt(damage);
                    alive = false;
                    break;
                }
            }
        }

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(icon, (int)x, (int)y, new Paint());
    }

    public boolean isAlive(){
        return alive;
    }

    public void setWave(Wave wave){
        currentWave = wave;
    }

}
