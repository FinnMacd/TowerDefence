package com.example.finn.towerdefence.Level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.finn.towerdefence.Main.Button;
import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;
import com.example.finn.towerdefence.Turret.Turret;
import com.example.finn.towerdefence.Turret.TurretA;
import com.example.finn.towerdefence.Turret.TurretB;
import com.example.finn.towerdefence.Turret.TurretC;
import com.example.finn.towerdefence.Turret.TurretD;
import com.example.finn.towerdefence.Turret.TurretManager;

/**
 * Created by Finn on 2017-09-14.
 */

public class HUD {

    private Bitmap hud;

    private Paint paint;

    private LevelManager lm;

    private Button advanceWave, pause, menu, turrets;

    private boolean turretView = false;

    private double turretMenuY, turretMenuSpeed = 0;

    private Turret[] selectionTurrets = new Turret[]{
            new TurretA((int)(LevelManager.tileSize*0.5), (int)(LevelManager.tileSize*0.5)),
            new TurretB((int)(LevelManager.tileSize*0.5 + LevelManager.tileSize*15.0/4.0), (int)(LevelManager.tileSize*0.5)),
            new TurretC((int)(LevelManager.tileSize*0.5 + LevelManager.tileSize*15.0*2.0/4.0), (int)(LevelManager.tileSize*0.5)),
            new TurretD((int)(LevelManager.tileSize*0.5 + LevelManager.tileSize*15.0*3.0/4.0), (int)(LevelManager.tileSize*0.5))};

    public HUD(LevelManager lm){

        hud = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.hud);

        hud = Bitmap.createScaledBitmap(hud, GameControler.SCREEN_WIDTH, GameControler.SCREEN_HEIGHT, true);

        paint = new Paint();
        paint.setTextSize(80);

        double tileSize = LevelManager.tileSize;

        advanceWave = new Button((int)(GameControler.SCREEN_WIDTH - tileSize * 3),(int) (tileSize * 4.2), (int)(tileSize * 2.5), (int)(tileSize * 1.25 / 2));
        advanceWave.setBitmap(BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.next));

        turrets = new Button((int)(GameControler.SCREEN_WIDTH - tileSize * 3),(int) (tileSize * 5.2), (int)(tileSize * 2.5), (int)(tileSize * 1.25 / 2));
        turrets.setBitmap(BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.turrets));

        pause = new Button((int)(GameControler.SCREEN_WIDTH - tileSize * 3),(int) (tileSize * 6.2), (int)(tileSize * 2.5), (int)(tileSize * 1.25 / 2));
        pause.setBitmap(BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.pause));

        menu = new Button((int)(GameControler.SCREEN_WIDTH - tileSize * 3),(int) (tileSize * 7.2), (int)(tileSize * 2.5), (int)(tileSize * 1.25 / 2));
        menu.setBitmap(BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.menu));

        turretMenuY = -3*LevelManager.tileSize;

        this.lm = lm;

    }

    public void update(){

        advanceWave.update();
        turrets.update();
        pause.update();
        menu.update();

        if(advanceWave.isReleased() && !lm.isPaused()){
            if(lm.advanceWave()){
                advanceWave.setDraw(false);
            }
        }

        if(turrets.isReleased()){
            turretView = !turretView;
        }

        if(turrets.isPressed()){
            turretMenuSpeed = 0;
        }

        if(pause.isReleased()){
            lm.setPaused(!lm.isPaused());
        }

        if(turretView && turretMenuY < 0 && turretMenuY + turretMenuSpeed < 0){
            turretMenuSpeed += 0.8;
            turretMenuY += turretMenuSpeed;
        }else  if(turretView){
            turretMenuSpeed = 0;
            turretMenuY = 0;
        }

        if(!turretView && turretMenuY > -LevelManager.tileSize*3){
            turretMenuSpeed -= 0.8;
            turretMenuY += turretMenuSpeed;
        }else  if(!turretView){
            turretMenuSpeed = 0;
            turretMenuY = -LevelManager.tileSize*3;
        }

        for(Turret t: selectionTurrets){
            t.updatePos((int)LevelManager.tileSize, (int)turretMenuY);
            t.update();
        }

        if(TurretManager.placing){
            turretView = false;
            lm.setPaused(false);
        }

    }

    public void endWave(){
        advanceWave.setDraw(true);
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(hud, 0, 0, paint);

        canvas.drawText("Wave " + (lm.getWave()+1) + " / " + lm.getNumWaves(), (int)(GameControler.SCREEN_WIDTH - LevelManager.tileSize * 3.1), (int)(LevelManager.tileSize) + 80, paint);

        canvas.drawText("Health: "+lm.getHealth(), (int)(GameControler.SCREEN_WIDTH - LevelManager.tileSize * 3.1), (int)(LevelManager.tileSize*2) + 40, paint);

        canvas.drawText("Money: $"+lm.getMoney(), (int)(GameControler.SCREEN_WIDTH - LevelManager.tileSize * 3.1), (int)(LevelManager.tileSize*3), paint);

        advanceWave.draw(canvas);
        turrets.draw(canvas);
        pause.draw(canvas);
        menu.draw(canvas);

        if(turretMenuY > -LevelManager.tileSize*3.0) {
            Paint p = new Paint();
            p.setARGB(255, 219, 192, 96);

            canvas.drawRect((int) LevelManager.tileSize - 5, (int) turretMenuY, (int) (LevelManager.tileSize * 16) + 5, (int) (turretMenuY + LevelManager.tileSize * 3), p);

            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(8);

            canvas.drawRect((int) LevelManager.tileSize, (int) turretMenuY, (int) (LevelManager.tileSize * 16), (int) (turretMenuY + LevelManager.tileSize * 3), p);

            p = new Paint();
            p.setTextSize(60);

            for (int i = 0; i < selectionTurrets.length; i++) {
                selectionTurrets[i].draw(canvas);

                int sx = (int) (i * LevelManager.tileSize * 15.0 / 4.0 + LevelManager.tileSize);

                canvas.drawText("\"" + selectionTurrets[i].getName() + "\"", sx + (int) (LevelManager.tileSize * 1.6), (int) (LevelManager.tileSize * 1.0 + turretMenuY), p);

            }
        }

    }

}
