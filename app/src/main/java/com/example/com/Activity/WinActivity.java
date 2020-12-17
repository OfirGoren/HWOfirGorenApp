package com.example.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.com.Objects.GameManager;
import com.example.com.R;
import com.example.com.Utils.Sounds;

public class WinActivity extends AppCompatActivity {


    private TextView win_LBL_win_name;
    private TextView win_LBL_win_score;
    private TextView win_LBL_name_title;
    private TextView win_LBL_score_title;
    private Intent intent;
    private int winScore;
    private String winName;
    private String msgTieGame = "Tie In The \n Game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        //initialize
        allFindViewById();
        // get details win
        getInfoWin();
        //set details on winner on views
        setTexts();
        // activate sound audience
        Sounds.getInstance().addSound(R.raw.sound_audience_applause);

    }

    private void setTexts() {
        //when there was tie
        if (winName.equals(GameManager.TIE)) {
            win_LBL_name_title.setVisibility(View.GONE);
            win_LBL_score_title.setVisibility(View.GONE);
            win_LBL_win_score.setVisibility(View.GONE);
            // set message tie
            win_LBL_win_name.setText(msgTieGame);
            win_LBL_win_name.setGravity(Gravity.LEFT);

        } else {
            // set the name of winner
            win_LBL_win_score.setText("" + winScore);
            // set the score of winner
            win_LBL_win_name.setText(winName);
        }
    }


    private void getInfoWin() {
        intent = getIntent();
        winScore = intent.getIntExtra(GameManager.WIN_SCORE, 0);
        winName = intent.getStringExtra(GameManager.WIN_NAME);
    }

    private void allFindViewById() {
        win_LBL_win_name = findViewById(R.id.win_LBL_win_name);
        win_LBL_win_score = findViewById(R.id.win_LBL_win_score);
        win_LBL_name_title = findViewById(R.id.win_LBL_name_title);
        win_LBL_score_title = findViewById(R.id.win_LBL_score_title);


    }


}

