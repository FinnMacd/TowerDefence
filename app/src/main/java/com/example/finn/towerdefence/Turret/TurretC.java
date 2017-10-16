package com.example.finn.towerdefence.Turret;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-14.
 */

public class TurretC extends Turret {


    public TurretC(int cx, int cy, BulletManager bulletManager) {

        super(cx, cy, R.drawable.turretc, bulletManager);

    }

    public TurretC(BulletManager bulletManager) {

        super(R.drawable.turretc, bulletManager);

    }

    public void initComps(){
        cost = 8;
        range = 1.7;
        rate = 2000;
    }

    public void update(){
        super.update();

        if(setting && !GameControler.Inputs.pressed){
            TurretManager.turretManager.removeTurret(this);
            if(valid){
                TurretManager.turretManager.addTurret(new TurretC(cx, cy, bulletManager), true);
            }
            TurretManager.placing = false;
        }

    }

}
