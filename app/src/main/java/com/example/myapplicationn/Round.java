package com.example.myapplicationn;

import java.util.ArrayList;

public class Round
{
    float time1;
    float time2;
    String status;
    ArrayList<Integer> myGameDeck = new ArrayList<>();
    ArrayList<Integer> otherGameDeck = new ArrayList<>();

    public Round(float t)
    {
        this.time1 = t;
        status = "created";
    }
}
