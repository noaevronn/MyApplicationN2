package com.example.myapplicationn;

public class Card {
    private int[] card = new int[8];

    private int index=0;

    public void addImage(int image)
    {
        card[index] = image;
        index++;
    }

    public int[] getCard() {
        return card;
    }

    public void setCard(int[] card) {
        this.card = card;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
