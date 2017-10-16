package com.example.finn.towerdefence.Main;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.finn.towerdefence.R;

public class GameActivity extends AppCompatActivity {

    private GameControler controler;
    public static Context CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        GameActivity.CONTEXT = this;

        controler = new GameControler(size.x, size.y);

        setContentView(controler);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    protected void onPause(){
        super.onPause();
        controler.pause();
    }

    protected void onResume(){
        super.onResume();
        controler.resume();
    }

}
