package com.example.finn.towerdefence.Main;

import android.graphics.Point;
import android.view.MotionEvent;

public class Inputs{

    private static MotionEvent e;

    public static boolean pressed = false, lastpressed = false, tapped = false, dtapped = false;

    public static float x = -1, y = -1;
    public static Point pos = new Point();

    private static long timer = 0;

    public static void setMotionEvent(MotionEvent e){
        Inputs.e = e;

        lastpressed = pressed;

        if((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
            pressed = false;
            if(System.currentTimeMillis()-timer < 500)tapped = true;
        }else if((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
            tapped = false;
            pressed = true;
            timer = System.currentTimeMillis();
        }

        x = e.getX();
        y = e.getY();
        pos.set((int)x,(int)y);

    }


}