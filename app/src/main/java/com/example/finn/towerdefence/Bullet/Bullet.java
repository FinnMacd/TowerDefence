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

    private int speed, damage, splashDamage;
    private double x, y, dx, dy, tx, ty, size;//location, change, target
    private boolean alive = true, detinate;
    private long deathTime = 0;
    private Rect bounds;

    private Wave currentWave;

    public Bullet(int iconID, int speed, int damage, int splashDamage, int x, int y, double tx, double ty){

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);

        size = LevelManager.tileSize/2;
        this.speed = speed;
        this.damage = damage;
        this.splashDamage = splashDamage;
        this.x = x-size/2;
        this.y = y-size/2;
        this.tx = tx;
        this.ty = ty;

        double dist = Math.sqrt((x-tx)*(x-tx) + (y-ty)*(y-ty));

        detinate = damage != 0;

        dx = -(x-tx)/dist;
        dy = -(y-ty)/dist;

        icon = Bitmap.createScaledBitmap(icon, (int)(size), (int)(size), true);

        bounds = new Rect(x, y, icon.getWidth() + x, icon.getHeight() + y);

    }

    public void update(){

        x+=dx*speed;
        y+=dy*speed;

        if(!LevelManager.levelBounds.contains((int)x, (int)y)){
            deathTime++;
        }

        if(deathTime > 120)alive = false;

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
