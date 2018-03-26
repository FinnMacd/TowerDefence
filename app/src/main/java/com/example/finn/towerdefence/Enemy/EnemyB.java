package com.example.finn.towerdefence.Enemy;

import com.example.finn.towerdefence.Level.Wave;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-18.
 */

public class EnemyB extends Enemy {

    public EnemyB(int x, int y, int dx, int dy, Wave wave) {
        super(7, 80, x, y, dx, dy, R.drawable.enemyb, wave);
    }
}
