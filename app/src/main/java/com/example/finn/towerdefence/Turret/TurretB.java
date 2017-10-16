package com.example.finn.towerdefence.Turret;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-14.
 */

public class TurretB extends Turret{


    public TurretB(int cx, int cy, BulletManager bulletManager) {
        super(cx, cy, R.drawable.turretb, bulletManager);
    }

    public TurretB(BulletManager bulletManager) {
        super(R.drawable.turretb, bulletManager);
    }

    public void initComps(){
        cost = 6;
        range = 2.5;
        rate = 800;
    }

    public void update(){
        super.update();

        if(setting && !GameControler.Inputs.pressed){
            TurretManager.turretManager.removeTurret(this);
            if(valid){
                TurretManager.turretManager.addTurret(new TurretB(cx, cy, bulletManager), true);
            }
            TurretManager.placing = false;
        }

    }

}
