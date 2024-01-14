package com.example.myapplicationn;

import java.util.ArrayList;

public class Round
{
    float time1;
    float time2;
    int status;
    ArrayList<Integer> myGameDeck = new ArrayList<>();
    ArrayList<Integer> otherGameDeck = new ArrayList<>();
    public Round(){}

    public Round(float t)
    {
        this.time1 = t;
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

    public ArrayList<Integer> getOtherGameDeck() {
        return otherGameDeck;
    }

    public void setOtherGameDeck(ArrayList<Integer> otherGameDeck) {
        this.otherGameDeck = otherGameDeck;
    }
}
