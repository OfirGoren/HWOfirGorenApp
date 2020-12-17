package com.example.com.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.com.Objects.Cards;
import com.example.com.Objects.GameManager;
import com.example.com.R;
import com.example.com.Utils.Anim;
import com.example.com.Utils.Sounds;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {


    private ArrayList<Cards> allCards;
    private TextView game_LBL_score_player_one;
    private TextView game_LBL_score_player_two;
    private TextView game_LBL_name_play_one;
    private TextView game_LBL_name_play_two;
    private ImageView game_IMG_card_player_one;
    private ImageView game_IMG_card_player_two;
    private ImageButton game_BTN_play_game;
    private ProgressBar game_PGR_determinateBar;
    private GameManager manager;
    private Timer timer;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private int progress = 0;
    private boolean activityInBackGround;
    private boolean isBeginCards = true;
    private boolean PlayGameStart = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //ask permission location
        getPermissionLocation();
        // initialize
        manager = new GameManager();
        timer = new Timer();
        activityInBackGround = false;

        //initialize views
        allFindViewById();

        //set names of players on board
        setPlayersName();
        // initialize progress bar to 26 rounds
        game_PGR_determinateBar.setMax(26);

        allCards = GameManager.LoadCardsIntoAnArray();
        GameManager.shuffleCards(allCards);

        //listener play Game Button
        clickPlayGame();


    }


    // Set names Of Players On Game Board
    private void setPlayersName() {
        Intent intent = getIntent();
        game_LBL_name_play_one.setText(intent.getStringExtra(HomeActivity.Name_Player_One));
        game_LBL_name_play_two.setText(intent.getStringExtra(HomeActivity.Name_Player_Two));
    }


    @Override
    protected void onResume() {
        super.onResume();
        /*
         when the activity existing in the background and
         the play game already start
         continue the game from pause state
         */
        if (activityInBackGround && PlayGameStart == false) {
            activityInBackGround = false;
            setImagesOnScreenEverySecond();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //when activity in the background
        activityInBackGround = true;
        timer.cancel();
    }

    //initialize
    private void allFindViewById() {
        game_BTN_play_game = findViewById(R.id.game_BTN_play_game);
        game_LBL_score_player_one = findViewById(R.id.game_LBL_score_player_one);
        game_LBL_score_player_two = findViewById(R.id.game_LBL_score_player_two);
        game_IMG_card_player_one = findViewById(R.id.game_IMG_card_player_one);
        game_IMG_card_player_two = findViewById(R.id.game_IMG_card_player_two);
        game_LBL_name_play_one = findViewById(R.id.game_LBL_name_play_one);
        game_LBL_name_play_two = findViewById(R.id.game_LBL_name_play_two);
        game_PGR_determinateBar = findViewById(R.id.game_PGR_determinateBar);


    }


    private void clickPlayGame() {

        game_BTN_play_game.setOnClickListener(new View.OnClickListener() {
            // when click start game button
            @Override
            public void onClick(View v) {
                // When we have not started the game yet
                if (PlayGameStart) {
                    // button sound
                    Sounds.getInstance().addSound(R.raw.sound_button);
                    // change icon play to icon timer
                    changeIcon();
                    //play the game
                    setImagesOnScreenEverySecond();
                    // when we already pressed the play game button
                    PlayGameStart = false;
                }
            }
        });

    }

    //change to icon timer
    private void changeIcon() {
        game_BTN_play_game.setBackgroundResource(R.drawable.img_timer);
    }


    private void newIntentWinner() {
        // initialize
        Intent intentWin = new Intent(GameActivity.this, WinActivity.class);
        // return who win in the game
        String winName = manager.checkWhoWon(scorePlayer1, scorePlayer2);
        // when player_1 win
        if (winName.equals(GameManager.PLAYER_1)) {
            //pass to new intent  (WinActivity) name and score details
            intentWin.putExtra(GameManager.WIN_NAME, game_LBL_name_play_one.getText());
            intentWin.putExtra(GameManager.WIN_SCORE, scorePlayer1);
            // save to cache (name and score details)
            manager.SaveAndUpdateWinnersSharedPref(this.getApplicationContext(), game_LBL_name_play_one.getText().toString(), scorePlayer1);

            // when player_2 win
        } else if (winName.equals(GameManager.PLAYER_2)) {
            //pass to new intent  (WinActivity) name and score details
            intentWin.putExtra(GameManager.WIN_NAME, game_LBL_name_play_two.getText());
            intentWin.putExtra(GameManager.WIN_SCORE, scorePlayer2);
            // save to cache (name and score details)
            manager.SaveAndUpdateWinnersSharedPref(this.getApplicationContext(), game_LBL_name_play_two.getText().toString(), scorePlayer2);

            //when there is tie
        } else {
            // pass to new intent WinActivity ( tie message)
            intentWin.putExtra(GameManager.WIN_NAME, GameManager.TIE);


        }

        this.startActivity(intentWin);
        finish();
    }

    /*
    change cards every second and update the progress bar ,
    after 26 round the game is over and we pass
    to new intent
     */
    private void setImagesOnScreenEverySecond() {

        //initialize
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // when the game is finished
                        if (manager.checkGameFinish()) {
                            //cancel the timer
                            timer.cancel();
                            // pass to win activity
                            newIntentWinner();
                        } else {
                            //when begin card is false
                            if (isBeginCards) {
                                //up the progress bar
                                progress += 1;
                                //activate animation cards
                                animFadeCards();
                                //activate sound
                                Sounds.getInstance().addSound(R.raw.sound_replace_cards);
                                // update the progress bar , images card , update score win round
                                updateAllTheDataOnGameBoard();
                                // change card for next round
                                manager.changeCards();

                                isBeginCards = false;

                            } else {
                                // set begin card
                                setBeginCards();

                                isBeginCards = true;
                            }
                        }

                    }
                });

            }
        }, 1500, 1500);

    }

    // animation fade in
    private void animFadeCards() {
        Anim.getInstance().startAnim(Anim.AnimationType.FadeIn, game_IMG_card_player_one);
        Anim.getInstance().startAnim(Anim.AnimationType.FadeIn, game_IMG_card_player_two);
    }

    // update the game board (images card , progress bar , round score)
    private void updateAllTheDataOnGameBoard() {
        setImageCardsGlide();
        setProgressBar();
        updateRoundScoreWinOnScreen();

    }

    private void setBeginCards() {
        game_IMG_card_player_one.setImageResource(R.drawable.img_begin_card);
        game_IMG_card_player_two.setImageResource(R.drawable.img_begin_card);
    }

    private void setProgressBar() {
        game_PGR_determinateBar.setProgress(progress);
    }

    private void setImageCardsGlide() {
        // initialize
        String nameImage1;
        String nameImage2;
        nameImage1 = manager.getCardPlayerOne(allCards).getNameCard();
        nameImage2 = manager.getCardPlayerTwo(allCards).getNameCard();


        // set images on board game
        Glide
                .with(this)
                .load(this.getResources().getIdentifier(nameImage1, "drawable", this.getPackageName()))
                .into(game_IMG_card_player_one);

        Glide
                .with(this)
                .load(this.getResources().getIdentifier(nameImage2, "drawable", this.getPackageName()))
                .into(game_IMG_card_player_two);

    }


    private void updateRoundScoreWinOnScreen() {
        //initialize
        Cards cardPlayer1;
        Cards cardsPlayer2;
        String winnerRoundName;
        cardPlayer1 = manager.getCardPlayerOne(allCards);
        cardsPlayer2 = manager.getCardPlayerTwo(allCards);

        // check who is win the round game
        winnerRoundName = manager.checkWhoWon(cardPlayer1.getNumCard(), cardsPlayer2.getNumCard());
        // when player one win
        if (winnerRoundName.equals(GameManager.PLAYER_1)) {
            // go up the score by 1 of Player one
            scorePlayer1++;
            // set the score on game board (game activity)
            game_LBL_score_player_one.setText("" + scorePlayer1);
            //when player two win
        } else if (winnerRoundName.equals(GameManager.PLAYER_2)) {
            // go up the score by 1 of player two
            scorePlayer2++;
            // set the score on game board (game activity)
            game_LBL_score_player_two.setText("" + scorePlayer2);
        }


    }

    public void getPermissionLocation() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
    }

}

