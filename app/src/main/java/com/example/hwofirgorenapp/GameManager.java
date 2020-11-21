package com.example.hwofirgorenapp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    public static final String PLAYER_1 = "PLAYER_1";
    public static final String PLAYER_2 = "PLAYER_2";
    public static final String TIE = "TIE";
    private int fromFirstIndexPlayer1 = 0;
    private int fromLastIndexPlayer2 = Cards.NUM_CARDS - 1;

    public void Manager() {

    }

    /**
     * player1 run 0-25 and player2 run 51-26 on array,
     */
    public void changeCards() {
        fromFirstIndexPlayer1++;
        fromLastIndexPlayer2--;

    }

    /**
     * after 26 rounds the game will end.
     */
    public boolean checkGameFinish() {
        if (fromLastIndexPlayer2 < fromFirstIndexPlayer1) {
            return true;
        } else {
            return false;
        }
    }

    public Cards getCardPlayerTwo(ArrayList<Cards> allCards) {

        if (checkGameFinish()) {
            return null;
        } else {
            return allCards.get(fromLastIndexPlayer2);
        }
    }


    public Cards getCardPlayerOne(ArrayList<Cards> allCards) {

        if (checkGameFinish()) {
            return null;
        } else {
            return allCards.get(fromFirstIndexPlayer1);
        }
    }


    public static void shuffleCards(ArrayList<Cards> allCards) {
        Collections.shuffle(allCards);
    }

    /**
     * the method is loading all the images from drawable ,
     * and take only the resource id cards and name cards,
     * separate the number from the name card and put the resource
     * and the number inside array list.  (we have 52 cards
     * inside).
     */
    public static ArrayList<Cards> LoadCardsIntoAnArray() {

        int resourceId;
        int valueCard;
        ArrayList<Cards> allCards = new ArrayList<>();

        Class cls = R.drawable.class;
        Field fieldList[] = cls.getDeclaredFields();

        for (Field fld : fieldList) {
            String fieldName = fld.getName();
            if (fieldName.contains("img_card_")) {
                try {

                    resourceId = fld.getInt(null);
                    valueCard = Integer.parseInt(fld.getName().
                            replaceAll("[^0-9]", ""));
                    allCards.add(new Cards(valueCard, resourceId));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return allCards;
    }


    public static String checkWhoWon(int scorePlayer1, int scorePlayer2) {

        if (scorePlayer1 > scorePlayer2) {
            return PLAYER_1;
        } else if (scorePlayer1 < scorePlayer2) {
            return PLAYER_2;
        }
        return TIE;
    }

}