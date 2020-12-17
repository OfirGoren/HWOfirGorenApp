package com.example.com.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.com.Fragmens.MapFragment;
import com.example.com.Fragmens.TopTenScoresFragment;
import com.example.com.Interface.CallBackLocation;
import com.example.com.R;


public class RecordsActivity extends AppCompatActivity implements CallBackLocation {


    private MapFragment map;
    private TopTenScoresFragment topTenScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        //initialize
        // allFindViews();
        topTenScore = new TopTenScoresFragment();
        map = new MapFragment();

        topTenScore.setCallBackLocation(this);

        getSupportFragmentManager().beginTransaction().add(R.id.records_LAY_map, map).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.records_LAY_list, topTenScore).commit();


    }


    // pass to map the location and date
    @Override
    public void addMarkerCurrentLocation(double latitude, double longitude, String date) {

        map.locationOnMap(latitude, longitude, date);

    }
}