package com.example.finn.towerdefence.Main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

public class Button {

    private int x, y, width, height;

    private Rect bounds;

    private Bitmap bitmap, scaledBM;

    //click returns to false first time the program acceses it in its true state, pressed remains true, released accessed once at the end
    private boolean clicked = false, draw = true, pressed = false, released = false;

    public Button(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rect(x, y, x + width, y + height);
    }

    public Button(int x, int y,  Bitmap bitmap){
        setBitmap(bitmap);
        this.x = x;
        this.y = y;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        bounds = new Rect(x, y, x + width, y + height);
    }

    public void setBitmap(Bitmap bit){
        bitmap = Bitmap.createScaledBitmap(bit, width, height, false);
        scaledBM = Bitmap.createScaledBitmap(bitmap, (int)(width*0.9), (int)(height*0.9), false);
    }

    public void update(){

        if(bounds.contains((int)Inputs.x, (int)Inputs.y) && Inputs.pressed){
            clicked = pressed = true;
            released = false;
        }else if(pressed == true){
            clicked = pressed = false;
            released = true;
        }else{
            clicked = pressed = false;
        }

    }

    public boolean isClicked(){
        boolean ret = clicked;
        clicked = false;
        return ret;
    }

    public boolean isReleased(){
        boolean ret = released;
        released = false;
        return ret;
    }

    public void setDraw(boolean draw){
        this.draw = draw;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void draw(Canvas canvas){
        if(!pressed && draw){
            canvas.drawBitmap(bitmap, x, y, new Paint());
        }else if(pressed && draw){
            canvas.drawBitmap(scaledBM, x + (int)(width*0.05), y + (int)(height*0.05), new Paint());
        }else{
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            Paint p = new Paint();
            p.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(bitmap, x, y, p);
        }
    }

}
