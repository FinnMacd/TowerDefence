package com.example.finn.towerdefence.Level;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Main.Button;
import com.example.finn.towerdefence.Main.EndActivity;
import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.Main.MenuActivity;
import com.example.finn.towerdefence.R;
import com.example.finn.towerdefence.Turret.Turret;
import com.example.finn.towerdefence.Turret.TurretManager;

import java.io.InputStream;
import java.util.Scanner;

public class LevelManager {

    public static final int EASY = 0, MEDIUM = 1, HARD = 2;

    private int screenID, mapID, enemySetID, startX, startY, pauseFade = 0;

    private static int wave, health, money, numWaves;
    private static boolean waveRunning = false;
    private boolean paused = false;

    private Bitmap screen;

    private static int[][] map;
    public static double tileSize;
    public static Rect levelBounds;

    private HUD hud;

    private TurretManager tm;

    public static int mapWidth, mapHeight;

    private Wave[] waves;

    private BulletManager bulletManager;

    public LevelManager(int difficulty){

        if(difficulty == LevelManager.EASY){
            screenID = R.drawable.screen;
            mapID = R.raw.easymap;
            enemySetID = R.raw.easyenemyset;
        }

        init();

        tileSize = 84.0*GameControler.SCREEN_HEIGHT/800.0;

        screen = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), screenID);

        screen = Bitmap.createScaledBitmap(screen, GameControler.SCREEN_WIDTH, GameControler.SCREEN_HEIGHT, true);

        InputStream is = GameActivity.CONTEXT.getResources().openRawResource(mapID);

        Scanner scanner = new Scanner(is);

        mapHeight = scanner.nextInt();
        mapWidth = scanner.nextInt();

        map = new int[mapHeight][mapWidth];

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++) {
                map[y][x] = scanner.nextInt();
                if(map[y][x] == 5){
                    startX = (int)(x*tileSize);
                    startY = (int)((y+1)*tileSize);
                }
            }
        }

        levelBounds = new Rect((int)tileSize,(int)tileSize, (int)(tileSize*(map[0].length+1)), (int)(tileSize*(map.length+1)));

        hud = new HUD(this);

        InputStream waveStream = GameActivity.CONTEXT.getResources().openRawResource(enemySetID);

        Scanner escanner = new Scanner(waveStream);

        numWaves = Integer.parseInt(escanner.nextLine());

        waves = new Wave[numWaves];

        for(int i = 0; i < numWaves; i++){
            waves[i] = new Wave(escanner.nextLine(), startX, startY);
        }

        bulletManager = new BulletManager();

        tm = new TurretManager(this, bulletManager);

    }

    private void init(){
        wave = 0;
        health = 100;
        money = 30;
        numWaves = 0;
    }

    public void update(){

        hud.update();

        if(paused){
            if(pauseFade < 90)pauseFade+=3;
            return;
        }else if(pauseFade > 0)pauseFade -=3;

        tm.update();

        bulletManager.update();

        waves[wave].update();

        if(waves[wave].isDead()){
            waveRunning = false;
            wave++;
            hud.endWave();
            if(wave == waves.length){//win condition
                wave--;
                setPaused(true);
                //GameActivity.CONTEXT.startActivity(new Intent(GameActivity.CONTEXT, MenuActivity.class));
                return;
            }

        }

    }

    public void draw(Canvas canvas){

        long temp = System.currentTimeMillis();

        //canvas.drawBitmap(screen, 0, 0, new Paint());

        waves[wave].draw(canvas);

        tm.draw(canvas);

        bulletManager.draw(canvas);

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAlpha(pauseFade);
        canvas.drawRect(levelBounds, p);

        hud.draw(canvas);

        System.out.println(System.currentTimeMillis()-temp);

    }

    public boolean advanceWave(){
        if(TurretManager.placing || waveRunning)return false;
        tm.setWave(waves[wave]);
        bulletManager.setWave(waves[wave]);
        waves[wave].start();
        waveRunning = true;
        return true;
    }

    public static int checkMap(int x, int y){
        return map[y][x];
    }

    public static void hurt(){
        health--;
    }

    public int getWave() {
        return wave;
    }

    public int getNumWaves(){return numWaves;}

    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
    }

    public static void addMoney(int m){money += m;}

    public void spent(int t){
        money -= t;
    }

    public static boolean isRunning(){
        return waveRunning;
    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public boolean isPaused(){
        return paused;
    }

}
