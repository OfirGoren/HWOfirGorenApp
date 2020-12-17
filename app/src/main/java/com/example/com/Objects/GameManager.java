package com.example.com.Objects;

import android.content.Context;

import com.example.com.Interface.Keys;
import com.example.com.R;
import com.example.com.Utils.CoordinatesMap;
import com.example.com.Utils.Date;
import com.example.com.Utils.SharedPref;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager implements Keys {

    public final static String PLAYER_1 = "PLAYER_1";
    public final static String PLAYER_2 = "PLAYER_2";
    public final static String WIN_NAME = "WIN_NAME";
    public final static String WIN_SCORE = "WIN_SCORE";
    public final static String TIE = "TIE";
    private int fromFirstIndexPlayer1;
    private int fromLastIndexPlayer2;

    public GameManager() {
        this.fromFirstIndexPlayer1 = 0;
        this.fromLastIndexPlayer2 = Cards.NUM_CARDS - 1;
    }

    // player1 run 0-25 and player2 run 51-26 on array,
    public void changeCards() {
        fromFirstIndexPlayer1++;
        fromLastIndexPlayer2--;

    }


    // check if the game was finish
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

    public String checkWhoWon(int scorePlayer1, int scorePlayer2) {

        if (scorePlayer1 > scorePlayer2) {
            return PLAYER_1;
        } else if (scorePlayer1 < scorePlayer2) {
            return PLAYER_2;
        }
        return TIE;
    }

    /**
     * the method is loading all the images from drawable ,
     * and only take name cards,
     * separate the number from the name card and insert
     * the number inside array list and name cards.  (we have 52 cards
     * inside).
     */
    public static ArrayList<Cards> LoadCardsIntoAnArray() {

        // initialize
        int valueCard;
        ArrayList<Cards> allCards = new ArrayList<>();
        Class cls = R.drawable.class;
        Field fieldList[] = cls.getDeclaredFields();

        // run all files from drawable directory
        for (Field fld : fieldList) {
            //get name file
            String fieldName = fld.getName();
            //when the file is card picture
            if (fieldName.contains("img_card_")) {
                // get only the number from name file(represents number card)
                valueCard = Integer.parseInt(fld.getName().
                        replaceAll("[^0-9]", ""));
                // insert object card to array with name card and number
                allCards.add(new Cards(valueCard, fieldName));

            }
        }
        return allCards;
    }


    /*
     update the list of winners and save to shared pref
     */
    public void SaveAndUpdateWinnersSharedPref(Context context, String winName, int winScore) {

        NumberFormat formatter = new DecimalFormat("##.000000");
        //initialize Json ;
        Gson json = new Gson();
        // get from shared pref data saved
        ArrayList<Winner> listWinners = SharedPref.getInstance().getListFromPrefJson(SHARED_PREF_KEY, Winner.class);

        //when there is winner and the list no more than 11 values
        if (!WIN_NAME.equals(GameManager.TIE)) {

            //show 6 digits after the dot and convert to double
            double latitude = Double.parseDouble(formatter.format(CoordinatesMap.getInstance().getLatitude()));
            double Longitude = Double.parseDouble(formatter.format(CoordinatesMap.getInstance().getLongitude()));
            // add to ArrayList<Winner>
            listWinners.add(new Winner(winName, Date.currentDate(), winScore, latitude, Longitude));
            //sort the list according to score
            Collections.sort(listWinners, new ComparatorScore());
            // when the size of winners grater than 10
            if (listWinners.size() > 10) {
                //remove the last in list (The low result in the list)
                listWinners.remove(listWinners.size() - 1);
            }
        }
        //Convert to Json
        String ttJson = json.toJson(listWinners);
        // Add to sharedPreferences
        SharedPref.getInstance().putString(SHARED_PREF_KEY, ttJson);


    }

}
