package com.example.finn.towerdefence.Enemy;

import android.graphics.BitmapFactory;

import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-15.
 */

public class EnemyA extends Enemy {

    public EnemyA(int x, int y, int dx, int dy) {
        super(5, 100, x, y, dx, dy, R.drawable.enemya);

    }
}
