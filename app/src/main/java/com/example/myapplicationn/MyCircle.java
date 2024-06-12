package com.example.myapplicationn;

import android.graphics.Color;
import android.graphics.Paint;

public class MyCircle
{
    private float x;
    private float y;
    private float r;
    private Paint paint;

    public MyCircle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(12);
    }

    public Paint getPaint() {
        return paint;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getR() {
        return r;
    }
}
