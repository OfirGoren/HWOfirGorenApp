package com.example.com.Fragmens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.com.Interface.CallBackLocation;
import com.example.com.Interface.Keys;
import com.example.com.Objects.WinnerListAdapter;
import com.example.com.Objects.Winner;
import com.example.com.R;
import com.example.com.Utils.SharedPref;

import java.util.ArrayList;

public class TopTenScoresFragment extends Fragment implements Keys {

    private ListView listView;
    private CallBackLocation callBackLocation;


    public TopTenScoresFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //initialize
        View view = inflater.inflate(R.layout.fragment_top_ten_list, container, false);
        findViews(view);
        //get list winners from shared Pref and json
        ArrayList<Winner> winnerList = getListWinners();
        //set list winners
        listView.setAdapter(new WinnerListAdapter(getContext(), winnerList));
        //listener on click row in the list
        clickListenerRowListView();

        return view;

    }

    private void clickListenerRowListView() {


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {

                // get object that was press in the list
                Object listItem = listView.getItemAtPosition(pos);
                Winner listWinnerRow = (Winner) listItem;
                // pass the detail to Records Activity (from there we will pass to map)
                passLocationToRecordsActivity(listWinnerRow.getLatitude(), listWinnerRow.getLongitude(), listWinnerRow.getDate());

            }

        });
    }


    public void setCallBackLocation(CallBackLocation callBackLocation) {
        this.callBackLocation = callBackLocation;
    }


    private void passLocationToRecordsActivity(double latitude, double longitude, String date) {
        //when there is call back
        if (callBackLocation != null) {
            //activate the call back
            callBackLocation.addMarkerCurrentLocation(latitude, longitude, date);
        }
    }

    private void findViews(View view) {
        listView = view.findViewById(R.id.top_ten_LVW_list_scores);

    }

    // get list from shared pref and using json
    private ArrayList getListWinners() {

        return SharedPref.getInstance().getListFromPrefJson(SHARED_PREF_KEY, Winner.class);

    }
}
