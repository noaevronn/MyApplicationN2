package com.example.myapplicationn;

import android.graphics.Bitmap;

public class Picture {

    private float x;
    private float y;
    private Bitmap bitmap;


    public Picture(float x, float y, Bitmap bitmap)
    {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
