package com.example.hwofirgorenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {
    public final static String WIN_NAME = "WIN_NAME";
    public final static String WIN_SCORE = "WIN_SCORE";

    private ArrayList<Cards> allCards;
    private TextView game_LBL_score_player_one;
    private TextView game_LBL_score_player_two;
    private TextView game_LBL_name_play_one;
    private TextView game_LBL_name_play_two;
    private ImageView game_IMG_card_player_one;
    private ImageView game_IMG_card_player_two;
    private ImageButton game_BTN_play_game;
    private GameManager manager;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        manager = new GameManager();

        allFindViewById();

        allCards = GameManager.LoadCardsIntoAnArray();

        GameManager.shuffleCards(allCards);

        clickPlayGame();

    }


    private void allFindViewById() {
        game_BTN_play_game = findViewById(R.id.game_BTN_play_game);
        game_LBL_score_player_one = findViewById(R.id.game_LBL_score_player_one);
        game_LBL_score_player_two = findViewById(R.id.game_LBL_score_player_two);
        game_IMG_card_player_one = findViewById(R.id.game_IMG_card_player_one);
        game_IMG_card_player_two = findViewById(R.id.game_IMG_card_player_two);
        game_LBL_name_play_one = findViewById(R.id.game_LBL_name_play_one);
        game_LBL_name_play_two = findViewById(R.id.game_LBL_name_play_two);

    }


    private void clickPlayGame() {

        game_BTN_play_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runGame();

            }
        });

    }

    private void runGame() {

        if (manager.checkGameFinish()) {

            newIntentWinner();

        } else {

            setImagesOnScreen();
            updateRoundScoreWinOnScreen();
            manager.changeCards();
        }

    }


    private void newIntentWinner() {

        Intent intentWin = new Intent(GameActivity.this, WinActivity.class);

        String winName = GameManager.checkWhoWon(scorePlayer1, scorePlayer2);

        if (winName.equals(GameManager.PLAYER_1)) {
            intentWin.putExtra(WIN_NAME, game_LBL_name_play_one.getText());
            intentWin.putExtra(WIN_SCORE, scorePlayer1);
        } else if (winName.equals(GameManager.PLAYER_2)) {
            intentWin.putExtra(WIN_NAME, game_LBL_name_play_two.getText());
            intentWin.putExtra(WIN_SCORE, scorePlayer2);
        } else {
            intentWin.putExtra(WIN_NAME, GameManager.TIE);
        }

        this.startActivity(intentWin);
        finish();
    }


    private void setImagesOnScreen() {

        Cards cardPlayer1 = manager.getCardPlayerOne(allCards);
        Cards cardsPlayer2 = manager.getCardPlayerTwo(allCards);

        game_IMG_card_player_one.setImageResource(cardPlayer1.getResourceId());
        game_IMG_card_player_two.setImageResource(cardsPlayer2.getResourceId());
    }


    private void updateRoundScoreWinOnScreen() {
        Cards cardPlayer1;
        Cards cardsPlayer2;
        String winnerRoundName;

        cardPlayer1 = manager.getCardPlayerOne(allCards);
        cardsPlayer2 = manager.getCardPlayerTwo(allCards);

        winnerRoundName = GameManager.checkWhoWon(cardPlayer1.getNumCard(), cardsPlayer2.getNumCard());

        if (winnerRoundName.equals(GameManager.PLAYER_1)) {
            scorePlayer1++;
            game_LBL_score_player_one.setText("" + scorePlayer1);
        } else if (winnerRoundName.equals(GameManager.PLAYER_2)) {
            scorePlayer2++;
            game_LBL_score_player_two.setText("" + scorePlayer2);
        }


    }


}