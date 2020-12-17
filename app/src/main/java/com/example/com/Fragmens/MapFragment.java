package com.example.com.Fragmens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.com.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private SupportMapFragment supportMapFragment;
    private View view;
    private double latitude;
    private double longitude;
    private String date;


    public MapFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_map, container, false);

        this.supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);


        return view;
    }


    //get location and date and insert to map
    public void locationOnMap(double latitude, double longitude, String date) {
        //initialize
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Coordinates location
        LatLng latLng = new LatLng(this.latitude, this.longitude);

        // Create marker options on map
        MarkerOptions options = (new MarkerOptions().position(latLng)
                .title("i Am Here").snippet(this.date));

        // Zoom map
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        // add marker on map
        googleMap.addMarker(options);


    }


}