package com.example.finn.towerdefence.Turret;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-14.
 */

public class TurretA extends Turret {

    public TurretA(int cx, int cy, BulletManager bulletManager) {
        super(cx, cy, R.drawable.turreta, bulletManager);
    }

    public TurretA(BulletManager bulletManager) {
        super(R.drawable.turreta, bulletManager);
    }

    public void initComps(){
        cost = 5;
        range = 2;
        rate = 1000;
    }

    public void update(){
        super.update();

        if(setting && !GameControler.Inputs.pressed){
            TurretManager.turretManager.removeTurret(this);
            if(valid){
                TurretManager.turretManager.addTurret(new TurretA(cx, cy, bulletManager), true);
            }
            TurretManager.placing = false;
        }

    }
}
