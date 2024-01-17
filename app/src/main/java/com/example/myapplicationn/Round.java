package com.example.myapplicationn;

import android.view.View;

import java.util.ArrayList;

public class Round
{
    float time1;
    float time2;
    int status;
    ArrayList<Integer> myGameDeck = new ArrayList<>();

    public Round()
    {
        status = AppConstants.CREATED;

    }

    public float getTime1() {
        return time1;
    }

    public void setTime1(float time1) {
        this.time1 = time1;
    }

    public float getTime2() {
        return time2;
    }

    public void setTime2(float time2) {
        this.time2 = time2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Integer> getMyGameDeck() {
        return myGameDeck;
    }

    public void setMyGameDeck(ArrayList<Integer> myGameDeck) {
        this.myGameDeck = myGameDeck;
    }


}
