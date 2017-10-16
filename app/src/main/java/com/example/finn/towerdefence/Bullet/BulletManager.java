package com.example.finn.towerdefence.Bullet;

import android.graphics.Canvas;

import com.example.finn.towerdefence.Level.Wave;

import java.util.ArrayList;

/**
 * Created by Finn on 2017-09-18.
 */

public class BulletManager {

    private ArrayList<Bullet> bullets;

    private Wave currentWave;

    public BulletManager(){

        bullets = new ArrayList<Bullet>();

    }

    public void update(){

        for(int i = 0; i < bullets.size(); i++){

            bullets.get(i).update();

            if(!bullets.get(i).isAlive()){
                bullets.remove(i);
                i--;
            }

        }

    }

    public void draw(Canvas canvas){

        for(Bullet b: bullets)b.draw(canvas);

    }

    public void addBullet(Bullet b){

        b.setWave(currentWave);
        bullets.add(b);

    }

    public void setWave(Wave wave){

        currentWave = wave;

        for(Bullet b: bullets)b.setWave(wave);

    }

}
