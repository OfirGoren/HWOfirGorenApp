package com.example.hwofirgorenapp;

public class Cards {
    public static final int NUM_CARDS = 52;
    private int numCard;
    private int resourceId;


    public Cards() {

    }

    public Cards(int numCard, int sourceId) {
        this.numCard = numCard;
        this.resourceId = sourceId;
    }

    public int getNumCard() {
        return numCard;
    }

    public void setNumCard(int numCard) {
        this.numCard = numCard;
    }

    public int getResourceId() {
        return resourceId;
    }


}
