package com.example.myapplicationn;

public class AppConstants
{

    public static int RADIUS = 450;
    public static float X = 900;
    public static float Y1 = 600;
    public static float Y2 = 1650;

    public static float[] arrX;
    public static  float[] arrY1;// =
    public static  float[] arrY2;  //{(float) ((Y2 - RADIUS) + (0.1*RADIUS)), (float) (Y2-0.6*RADIUS), Y2, (float) (Y2+ 0.6*RADIUS), (float) ((Y2 + RADIUS) - (0.3*RADIUS)), (float) (Y2+ 0.5*RADIUS), Y2, (float) (Y2 - 0.7*RADIUS)};


    public static final int CREATED = 1;
    public static  final int JOINED = 2;
    public  static final  int STARTED = 3;
    public static final int WINP1 = 4;
    public static final int WINP2 = 5;

    public static  final int WIN = 1;
    public static  final int LOST = 2;
    public static final int WAIT = 0;

    public static int cardCounter = 10;

    public static final String GAME_CONFIG = "GameConfiguration"; //האופי של המשחק (2 טלפונים או אחד)
    public static final int ONE_PHONE = 1;
    public static final int TWO_PHONES = 2;
    public static final int HOST = 1;
    public static final int OTHER = 2;


    public static long startTime = 0;
    public static long endTime = 0;

    public static int currentPlayer = HOST;
}
