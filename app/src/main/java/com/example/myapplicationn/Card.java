package com.example.myapplicationn;

public class Card {
    private int[] card = new int[8];

    private int index=0;

    public void addImage(int image)
    {
        card[index] = image;
        index++;
    }


}
