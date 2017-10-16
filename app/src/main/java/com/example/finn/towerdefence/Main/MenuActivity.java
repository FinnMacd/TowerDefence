package com.example.finn.towerdefence.Main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finn.towerdefence.Level.LevelManager;
import com.example.finn.towerdefence.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button easy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        easy = (Button) findViewById(R.id.easy);

        easy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == easy.getId())GameControler.GAMESTATE = LevelManager.EASY;

        startActivity(new Intent(this, GameActivity.class));

    }
}
