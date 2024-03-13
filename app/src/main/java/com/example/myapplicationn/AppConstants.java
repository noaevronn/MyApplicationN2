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


    public static final int CREATED = 1;
    public static  final int JOINED = 2;
    public  static final  int STARTED = 3;
    public static  final int WIN = 1;
    public static  final int LOST = 2;
    public static final int WAIT = 0;

    public static final String GAME_CONFIG = "GameConfiguration"; //האופי של המשחק (2 טלפונים או אחד)
    public static final int ONE_PHONE = 1;
    public static final int TWO_PHONES = 2;
    public static final int HOST = 1;
    public static final int OTHER = 2;


    public static long startTime = 0;
    public static long endTime = 0;

    public static int currentPlayer = HOST;
}
