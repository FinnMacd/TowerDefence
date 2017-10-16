package com.example.finn.towerdefence.Level;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Main.EndActivity;
import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;
import com.example.finn.towerdefence.Turret.Turret;
import com.example.finn.towerdefence.Turret.TurretManager;

import java.io.InputStream;
import java.util.Scanner;

public class LevelManager {

    public static final int EASY = 0, MEDIUM = 1, HARD = 2;

    private int screenID, mapID, enemySetID, startX, startY;

    private static int wave = 0, health = 100, money = 30, numWaves = 0;
    private static boolean waveRunning = false;

    private Bitmap screen, nextLevel;
    private Rect nextLevelBounds;

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

        tileSize = 84.0*GameControler.SCREEN_HEIGHT/800.0;

        screen = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), screenID);
        nextLevel = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.next);

        screen = Bitmap.createScaledBitmap(screen, GameControler.SCREEN_WIDTH, GameControler.SCREEN_HEIGHT, true);
        nextLevel = Bitmap.createScaledBitmap(nextLevel, (int)(tileSize * 2.5), (int)(tileSize * 1.25 / 2), true);

        nextLevelBounds = new Rect((int)(GameControler.SCREEN_WIDTH - tileSize * 3),(int) (tileSize * 7), (int)(GameControler.SCREEN_WIDTH - tileSize * 3) + nextLevel.getWidth(),(int) (tileSize * 7) + nextLevel.getHeight());

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

    public void update(){

        hud.update();

        tm.update();

        bulletManager.update();

        waves[wave].update();

        if(waves[wave].isDead()){
            waveRunning = false;
            wave++;

            if(wave == waves.length){
                //GameActivity.CONTEXT.startActivity(new Intent(GameActivity.CONTEXT, EndActivity.class));
                GameControler.pause();

                return;
            }

        }

        if(!TurretManager.placing && !waveRunning && nextLevelBounds.contains((int)GameControler.Inputs.x, (int)GameControler.Inputs.y) && GameControler.Inputs.pressed){
            tm.setWave(waves[wave]);
            bulletManager.setWave(waves[wave]);
            waves[wave].start();
            waveRunning = true;
        }

    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(screen, 0, 0, new Paint());

        waves[wave].draw(canvas);

        tm.draw(canvas);

        bulletManager.draw(canvas);

        hud.draw(canvas);

        tm.drawHUD(canvas);

        if(!waveRunning)
            canvas.drawBitmap(nextLevel, nextLevelBounds.left,nextLevelBounds.top, new Paint());

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

    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
    }

    public void spent(int t){
        money -= t;
    }

    public static boolean isRunning(){
        return waveRunning;
    }



}
