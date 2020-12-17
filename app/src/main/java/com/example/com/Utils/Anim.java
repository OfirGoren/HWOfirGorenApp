package com.example.com.Utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.com.R;

public class Anim {
    private static Anim instance;
    private Context context;

    public enum AnimationType {
        FadeIn, ZoomIn, ZoomOut
    }

    private Anim(Context context) {
        this.context = context;
    }

    public static void init(Context context) {

        if (instance == null) {
            instance = new Anim(context);
        }
    }

    public static Anim getInstance() {
        return instance;
    }

    /**
     * set view and animation type from enum , and the method
     * activate the animation that chosen on the view
     *
     * @param animationType set animation type from enum in this class
     * @param view          set view you want to activate the animation
     * @return Animation
     */
    public Animation startAnim(AnimationType animationType, View view) {

        Animation animation = null;
        switch (animationType) {
            case FadeIn:
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);

                break;
            case ZoomIn:
                animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);

                break;
            case ZoomOut:
                animation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);

                break;
        }

        view.startAnimation(animation);
        return animation;
    }
}


