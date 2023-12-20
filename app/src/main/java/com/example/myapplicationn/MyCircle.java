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

    public MyCircle() {}

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint)
    {
        this.paint = paint;
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

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }
}
