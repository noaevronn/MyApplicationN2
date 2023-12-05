package com.example.myapplicationn;

public class AppConstants
{

    public static final int RADIUS = 500;
    public static final float X = 900;
    public static final float Y = 700;

    public static final float[] arrX = {X, (float) (X - 0.5*RADIUS), (float) ((X - RADIUS) + (0.1 * RADIUS)), (float) (X - 0.5*RADIUS), X, (float) (X + 0.5*RADIUS), (float) ((X + RADIUS) - (0.1 * RADIUS)), (float) (X + 0.5*RADIUS)};
    public static final float[] arrY = {(float) ((Y - RADIUS) + (0.1*RADIUS)), (float) (Y-0.7*RADIUS), Y, (float) (Y+ 0.7*RADIUS), (float) ((Y + RADIUS) - (0.1*RADIUS)), (float) (Y+ 0.7*RADIUS), Y, (float) (Y - 0.7*RADIUS)};


}
