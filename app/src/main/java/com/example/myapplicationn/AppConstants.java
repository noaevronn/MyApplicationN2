package com.example.myapplicationn;

public class AppConstants
{

    public static final int RADIUS = 450;
    public static final float X = 900;
    public static final float Y1 = 600;
    public static final float Y2 = 1650;

    public static final float[] arrX = {X, (float) (X - 0.7*RADIUS), (float) ((X - RADIUS) + (0.1 * RADIUS)), (float) (X - 0.5*RADIUS), X, (float) (X + 0.5 * RADIUS), (float) ((X + RADIUS) - (0.3 * RADIUS)), (float) (X + 0.5*RADIUS)};
    public static final float[] arrY1 = {(float) ((Y1 - RADIUS) + (0.1*RADIUS)), (float) (Y1-0.6*RADIUS), Y1, (float) (Y1+ 0.6*RADIUS), (float) ((Y1 + RADIUS) - (0.3*RADIUS)), (float) (Y1+ 0.5*RADIUS), Y1, (float) (Y1 - 0.7*RADIUS)};
    public static final float[] arrY2 = {(float) ((Y2 - RADIUS) + (0.1*RADIUS)), (float) (Y2-0.6*RADIUS), Y2, (float) (Y2+ 0.6*RADIUS), (float) ((Y2 + RADIUS) - (0.3*RADIUS)), (float) (Y2+ 0.5*RADIUS), Y2, (float) (Y2 - 0.7*RADIUS)};



}
