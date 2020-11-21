package com.example.hwofirgorenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        allFindViewById();

        getInfoWin();

        setTexts();

    }


    private void setTexts() {

        if (winName.equals(GameManager.TIE)) {
            win_LBL_name_title.setVisibility(View.GONE);
            win_LBL_score_title.setVisibility(View.GONE);
            win_LBL_win_score.setVisibility(View.GONE);
            win_LBL_win_name.setText(msgTieGame);
            win_LBL_win_name.setGravity(Gravity.LEFT);

        } else {
            win_LBL_win_score.setText("" + winScore);
            win_LBL_win_name.setText(winName);
        }
    }


    private void getInfoWin() {
        intent = getIntent();
        winScore = intent.getIntExtra(GameActivity.WIN_SCORE, 0);
        winName = intent.getStringExtra(GameActivity.WIN_NAME);
    }

    private void allFindViewById() {
        win_LBL_win_name = findViewById(R.id.win_LBL_win_name);
        win_LBL_win_score = findViewById(R.id.win_LBL_win_score);
        win_LBL_name_title = findViewById(R.id.win_LBL_name_title);
        win_LBL_score_title = findViewById(R.id.win_LBL_score_title);


    }


}

