package com.example.finn.towerdefence.Level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.finn.towerdefence.Main.GameActivity;
import com.example.finn.towerdefence.Main.GameControler;
import com.example.finn.towerdefence.R;

/**
 * Created by Finn on 2017-09-14.
 */

public class HUD {

    private Bitmap hud;

    private Paint paint;

    private LevelManager lm;

    public HUD(LevelManager lm){

        hud = BitmapFactory.decodeResource(GameActivity.CONTEXT.getResources(), R.drawable.hud);

        hud = Bitmap.createScaledBitmap(hud, GameControler.SCREEN_WIDTH, GameControler.SCREEN_HEIGHT, true);

        paint = new Paint();
        paint.setTextSize(60);

        this.lm = lm;

    }

    public void update(){


    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(hud, 0, 0, paint);

        canvas.drawText("Wave " + (lm.getWave()+1), GameControler.SCREEN_WIDTH - 380, 120, paint);

        canvas.drawText("Health: "+lm.getHealth(), GameControler.SCREEN_WIDTH - 380, 180, paint);

        canvas.drawText("Money: $"+lm.getMoney(), GameControler.SCREEN_WIDTH - 380, 240, paint);

    }

}
