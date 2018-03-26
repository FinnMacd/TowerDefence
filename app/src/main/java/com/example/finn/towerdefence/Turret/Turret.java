package com.example.finn.towerdefence.Turret;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.finn.towerdefence.Bullet.Bullet;
import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Enemy.Enemy;
import com.example.finn.towerdefence.Level.LevelManager;
import com.example.finn.towerdefence.Level.Wave;
import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.Main.Inputs;

/**
 * Created by Finn on 2017-09-14.
 */

public abstract class Turret {

    protected Bitmap icon;

    protected int x=0, y=0, cx=0, cy=0, ox, oy, size, id, bulletID;
    protected Rect bounds;
    protected double scale = 0.9;

    protected boolean active = false, setting = false, valid = true, selected = false, targeted = false;

    protected double cost, range, rate, damage, splashDamage;
    protected String name;
    private long timer = 0;

    protected Paint paint;

    protected BulletManager bulletManager;

    private Wave currentWave = null;

    private Enemy target;

    public Turret(int cx, int cy, int iconID, BulletManager bulletManager){

        this.cx = cx;
        this.cy = cy;
        this.x = cx - (int)(LevelManager.tileSize*scale)/2;
        this.y = cy - (int)(LevelManager.tileSize*scale)/2;
        id = iconID;
        this.size = (int)(LevelManager.tileSize*scale);
        bounds = new Rect(x,y,x + size,y + size);
        paint = new Paint();

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);
        adjustSize();

        this.bulletManager = bulletManager;

        initComps();
    }

    public Turret(int iconID, BulletManager bulletManager){

        setting = true;
        size = (int)(LevelManager.tileSize*scale);
        id = iconID;
        bounds = new Rect();
        paint = new Paint();

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);
        adjustSize();

        this.bulletManager = bulletManager;

        initComps();

    }

    public Turret(int iconID, int ox, int oy){
        x=y=cx=cy=-100;
        this.ox = ox;
        this.oy = oy;
        id = iconID;
        this.size = (int)(LevelManager.tileSize*1);

        icon = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), iconID);
        adjustSize();
        bounds = new Rect();

        initComps();

        active = false;

        paint = new Paint();

    }

    private void updateBounds(){
        bounds.set(x,y,x + size,y + size);
    }

    protected abstract void initComps();

    protected void adjustSize(){

        icon = Bitmap.createScaledBitmap(icon, size, size, true);

    }

    public void update(){

        if(setting){
            cx = (int)Inputs.x;
            cy = (int)Inputs.y;
            x = cx - size/2;
            y = cy - size/2;
            bounds.set(x,y,x + size,y + size);
            return;
        }

        if(bounds.contains((int)Inputs.x, (int)Inputs.y)) {
            if (active && Inputs.tapped) selected();

            if (!active && !setting && !Inputs.lastpressed && Inputs.pressed && !TurretManager.placing) {
                TurretManager.turretManager.addTurret(id);
            }
        }else if(selected && LevelManager.levelBounds.contains((int)Inputs.x, (int)Inputs.y)){
            disSelected();
        }

        if(targeted){
            double dist = Math.sqrt(Math.pow(target.getX()-x, 2) + Math.pow(target.getY() - y, 2));
            if(!target.isAlive() || dist > range*LevelManager.tileSize){
                targeted = false;
            }else{
                if(System.currentTimeMillis() - timer > rate){
                    bulletManager.addBullet(new Bullet(bulletID, 10, (int)damage, cx, cy, ((target.getX()-x)/dist),((target.getY() - y) / dist)));
                    timer = System.currentTimeMillis();
                }
            }
        }

        if(active && LevelManager.isRunning() && !targeted){
            for(int i = 0; i < currentWave.getEnemies().length; i++){
                if(currentWave.getEnemies()[i].isAlive()){
                    double dist = Math.sqrt(Math.pow(currentWave.getEnemies()[i].getX()-x, 2) + Math.pow(currentWave.getEnemies()[i].getY() - y, 2));
                    if(dist <= range * LevelManager.tileSize){

                        targeted = true;

                        target = currentWave.getEnemies()[i];
                    }
                }
            }
        }

    }

    public void updatePos(int x, int y){
        this.x = x + ox;
        this.y = y + oy;
        updateBounds();
    }

    public void draw(Canvas canvas){

        if(selected || setting){
            if(valid){
                paint.setARGB(100,0,255,0);
            }else{
                paint.setARGB(100,255,0,0);
            }

            canvas.drawCircle(cx,cy,(float)(range*LevelManager.tileSize), paint);

        }

        paint.setAlpha(255);

        canvas.drawBitmap(icon, x, y, paint);

    }

    protected void selected(){

        selected = true;
        TurretManager.turretManager.selectTurret(this);

    }

    protected void disSelected(){

        selected = false;
        TurretManager.turretManager.disSelectTurret();

    }

    public void setValid(boolean b){
        valid = b;
    }

    public Rect getRect(){
        return bounds;
    }

    public double getCost() {
        return cost;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public void setWave(Wave wave){
        currentWave = wave;
    }

    public double getRange() {
        return range;
    }

    public double getRate() {
        return rate;
    }

    public double getDamage() {
        return damage;
    }

    public double getSplashDamage() {
        return splashDamage;
    }

    public String getName() {
        return name;
    }
}
