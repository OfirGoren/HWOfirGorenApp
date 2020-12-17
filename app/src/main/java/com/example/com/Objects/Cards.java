package com.example.com.Objects;

public class Cards {
    public static final int NUM_CARDS = 52;
    private int numCard;
    private String nameCard;


    public Cards() {

    }

    public Cards(int numCard, String nameCard) {
        this.numCard = numCard;
        this.nameCard = nameCard;
    }

    public int getNumCard() {
        return numCard;
    }

    public void setNumCard(int numCard) {
        this.numCard = numCard;
    }

    public String getNameCard() {
        return nameCard;
    }


}
