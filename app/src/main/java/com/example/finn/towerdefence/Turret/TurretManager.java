package com.example.finn.towerdefence.Turret;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Level.LevelManager;
import com.example.finn.towerdefence.Level.Wave;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

import java.util.ArrayList;

/**
 * Created by Finn on 2017-09-14.
 */

public class TurretManager {

    public static boolean placing = false;

    public static TurretManager turretManager;

    private BulletManager bulletManager = null;

    private Turret[] selectionTurrets = new Turret[]{
            new TurretA(GameControler.SCREEN_WIDTH - 350, 400, bulletManager),
            new TurretB(GameControler.SCREEN_WIDTH - 150, 400, bulletManager),
            new TurretC(GameControler.SCREEN_WIDTH - 350, 650, bulletManager),
            new TurretD(GameControler.SCREEN_WIDTH - 150, 650, bulletManager)};

    private ArrayList<Turret> turrets;

    private LevelManager lm;

    private Paint paint;

    private Wave currentWave;

    private Turret selection;
    private boolean hasSelection;

    public TurretManager(LevelManager lm, BulletManager bulletManager){

        turrets = new ArrayList<Turret>();

        turretManager = this;

        this.lm = lm;

        paint = new Paint();
        paint.setTextSize(80);

        this.bulletManager = bulletManager;

    }

    public void update(){

        for(Turret t:selectionTurrets)t.update();
        for(Turret t:turrets)t.update();

        if(placing){
            checkValid();
        }

    }

    public void draw(Canvas canvas){

        for(Turret t:turrets){

            //canvas.drawRect(t.getRect(), new Paint());
            t.draw(canvas);
        }

    }

    public void drawHUD(Canvas canvas){

        if(!placing && !hasSelection){
            for(Turret t:selectionTurrets){
                t.draw(canvas);
                canvas.drawText("$" + (int)t.getCost(), t.cx - 40, t.cy + t.size, paint);
            }

        }

    }

    private void checkValid(){

        boolean valid = true;

        Turret t = turrets.get(turrets.size()-1);

        for(int i = 0; i < turrets.size()-1; i++){
            if(Rect.intersects(turrets.get(i).getRect(),(t.getRect())))valid = false;
        }

        int x = (int)(t.x/LevelManager.tileSize)-1, y = (int)(t.y/LevelManager.tileSize)-1,
                xl = (int)((t.x+t.size)/LevelManager.tileSize)-1,yl = (int)((t.y+t.size)/LevelManager.tileSize)-1;

        if(x >=0 && x < lm.mapWidth - 1 && y >= 0 && y < lm.mapHeight - 1){

            if(lm.checkMap(x,y) != 0 ||lm.checkMap(xl,y) != 0||lm.checkMap(xl,yl) != 0||lm.checkMap(x,yl) != 0)valid = false;

        }else valid = false;

        if(lm.getMoney() - t.getCost() < 0)valid = false;

        t.setValid(valid);

    }

    public void addTurret(int id){

        switch(id){
            case R.drawable.turreta:
                turrets.add(new TurretA(bulletManager));
                break;
            case R.drawable.turretb:
                turrets.add(new TurretB(bulletManager));
                break;
            case R.drawable.turretc:
                turrets.add(new TurretC(bulletManager));
                break;
            case R.drawable.turretd:
                turrets.add(new TurretD(bulletManager));
                break;
        }
        placing = true;

    }

    public void addTurret(Turret t, boolean active){
        lm.spent((int)t.getCost());
        t.setActive(active);
        t.setWave(currentWave);
        turrets.add(t);
    }

    public void removeTurret(Turret t){
        turrets.remove(t);
    }

    public void setWave(Wave wave){
        this.currentWave = wave;

        for(Turret t: turrets)t.setWave(currentWave);

    }

    public void selectTurret(Turret t){

        selection = t;
        hasSelection = true;

    }

    public void disSelectTurret(){
        hasSelection = false;
    }

}
