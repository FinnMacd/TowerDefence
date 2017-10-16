package com.example.finn.towerdefence.Turret;

import com.example.finn.towerdefence.Bullet.BulletManager;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-14.
 */

public class TurretD extends Turret {


    public TurretD(int cx, int cy, BulletManager bulletManager) {
        super(cx, cy, R.drawable.turretd, bulletManager);
    }

    public TurretD(BulletManager bulletManager) {
        super(R.drawable.turretd, bulletManager);
    }

    public void initComps(){
        cost = 15;
        range = 3;
    }

    public void update(){
        super.update();

        if(setting && !GameControler.Inputs.pressed){
            TurretManager.turretManager.removeTurret(this);
            if(valid){
                TurretManager.turretManager.addTurret(new TurretD(cx, cy, bulletManager), true);
            }
            TurretManager.placing = false;
        }

    }

}
