package com.example.com.Utils;

import android.content.Context;
import android.media.MediaPlayer;

public class Sounds {

    private static Sounds instance;
    private Context context;


    private Sounds(Context context) {
        this.context = context;

    }

    public static void init(Context context) {

        if (instance == null) {
            instance = new Sounds(context);
        }
    }

    public static Sounds getInstance() {
        return instance;

    }

    /**
     * the method get resource id of the sound and activate the sound
     *
     * @param id set resource id, your sound you want to activate
     */
    public void addSound(int id) {
        MediaPlayer mp;
        mp = MediaPlayer.create(context, id);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });

    }


}
