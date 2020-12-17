package com.example.com.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.com.R;
import com.example.com.Utils.Anim;
import com.example.com.Utils.Sounds;

public class HomeActivity extends AppCompatActivity implements View.OnTouchListener {
    public static final String Name_Player_One = "Name_Player_One";
    public static final String Name_Player_Two = "Name_Player_Two";

    private EditText home_EDT_name_player_one;
    private EditText home_EDT_name_player_two;
    private Button home_BTN_start_game;
    private Button home_BTN_top_ten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //initialize
        allFindViewById();
        // on touch listener
        touchListener();


    }

    @SuppressLint("ClickableViewAccessibility")
    private void touchListener() {
        home_BTN_start_game.setOnTouchListener(this);
        home_BTN_top_ten.setOnTouchListener(this);

    }


    private void OpenIntentGame() {
        Intent intent = new Intent(HomeActivity.this, GameActivity.class);
        intent.putExtra(Name_Player_One, home_EDT_name_player_one.getText().toString());
        intent.putExtra(Name_Player_Two, home_EDT_name_player_two.getText().toString());
        this.startActivity(intent);

    }

    private void allFindViewById() {
        home_BTN_start_game = findViewById(R.id.home_BTN_start_game);
        home_BTN_top_ten = findViewById(R.id.home_BTN_top_ten);
        home_EDT_name_player_one = findViewById(R.id.home_EDT_name_player_one);
        home_EDT_name_player_two = findViewById(R.id.home_EDT_name_player_two);

    }


    private void openIntentRecords() {
        Intent intent = new Intent(this, RecordsActivity.class);
        this.startActivity(intent);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();

        //when pressed
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //zoom in animation
                activateAnimZoomOut(id);
                //activate the sound button
                Sounds.getInstance().addSound(R.raw.sound_button);
                break;
            case MotionEvent.ACTION_UP:
                Animation animZoomIn;
                // zoom out animation
                animZoomIn = activateAnimZoomIn(id);
               /*
               Listener to animation ZoomIn when finish(activate the activity
               according to id button
               */
                AnimListener(animZoomIn, id);
                break;

        }

        return true;

    }


    private Animation activateAnimZoomIn(int id) {
        Animation anim = null;
        switch (id) {
            case R.id.home_BTN_start_game:
                // start ZoomIn
                anim = Anim.getInstance().startAnim(Anim.AnimationType.ZoomIn, home_BTN_start_game);

                break;
            case R.id.home_BTN_top_ten:
                // start ZoomIn
                anim = Anim.getInstance().startAnim(Anim.AnimationType.ZoomIn, home_BTN_top_ten);

                break;
        }
        return anim;
    }

    private void activateAnimZoomOut(int id) {
        switch (id) {
            case R.id.home_BTN_start_game:
                //start animation ZoomOut
                Anim.getInstance().startAnim(Anim.AnimationType.ZoomOut, home_BTN_start_game);
                break;
            case R.id.home_BTN_top_ten:
                //start animation ZoomOut
                Anim.getInstance().startAnim(Anim.AnimationType.ZoomOut, home_BTN_top_ten);
                break;
        }
    }


    private void AnimListener(Animation animZoomIn, int id) {
        animZoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            // after ZoomIn animation end
            @Override
            public void onAnimationEnd(Animation animation) {
                switch (id) {
                    case R.id.home_BTN_start_game:
                        // activate game activity
                        OpenIntentGame();
                        break;
                    case R.id.home_BTN_top_ten:
                        // activate Records activity
                        openIntentRecords();
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
