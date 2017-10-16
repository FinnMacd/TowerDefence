package com.example.finn.towerdefence.Level;

import android.graphics.Canvas;
import android.util.Log;

import com.example.finn.towerdefence.Enemy.Enemy;
import com.example.finn.towerdefence.Enemy.EnemyA;
import com.example.finn.towerdefence.Enemy.EnemyB;
import com.example.finn.towerdefence.Enemy.EnemyC;

/**
 * Created by Finn on 2017-09-14.
 */

public class Wave {

    private Enemy[] enemies;

    private int speed;

    private boolean start = false, end = false, dead = false;

    private long time = 0;

    private int enemyCount = 0;

    public Wave(String waveData, int startX, int startY){

        Log.d("test", waveData);

        String[] tokens = waveData.split("\\|");

        speed = Integer.parseInt(tokens[0]);

        enemies = new Enemy[Integer.parseInt(tokens[1])];

        int addCount = 0;

        for(int i = 2; i < tokens.length; i++){

            String[] enData = tokens[i].split(" ");

            for(int j = 0; j < Integer.parseInt(enData[0]); j++){

                switch(enData[1]){
                    case"A":
                        enemies[addCount] = new EnemyA(startX, startY, 1, 0);
                        break;
                    case"B":
                        enemies[addCount] = new EnemyB(startX, startY, 1, 0);
                        break;
                    case"C":
                        enemies[addCount] = new EnemyC(startX, startY, 1, 0);
                        break;
                }
                addCount++;

            }

        }

    }

    public void update(){

        if(start){

            if(System.currentTimeMillis() - time >= speed){
                time += speed;

                if(enemyCount == enemies.length)end = true;
                else {
                    enemies[enemyCount].start();
                    enemyCount++;
                }
            }

            int numDeadEnemies = 0;

            for(Enemy e:enemies){
                e.update();
                if(!e.isAlive())numDeadEnemies++;
            }

            if(numDeadEnemies == enemies.length)dead = true;
        }else{
            time = System.currentTimeMillis();
        }

    }

    public void draw(Canvas canvas){

        if(start)for(Enemy e:enemies)e.draw(canvas);

    }

    public void start(){

        start = true;
        time = System.currentTimeMillis();
    }

    public boolean isDead(){
        return dead;
    }

    public Enemy[] getEnemies(){
        return enemies;
    }

}
