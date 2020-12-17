package com.example.com;

import android.app.Application;

import com.example.com.Utils.Anim;
import com.example.com.Utils.CoordinatesMap;
import com.example.com.Utils.SharedPref;
import com.example.com.Utils.Sounds;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Anim.init(this);
        SharedPref.init(this);
        Sounds.init(this);
        CoordinatesMap.init(this);


    }
}
