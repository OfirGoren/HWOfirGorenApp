package com.example.com.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.com.R;

import java.util.ArrayList;

public class WinnerListAdapter extends BaseAdapter {

    private ArrayList<Winner> listData;
    private LayoutInflater layoutInflater;

    public WinnerListAdapter(Context context, ArrayList<Winner> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            holder = new ViewHolder();

            v = layoutInflater.inflate(R.layout.list_row, null);
            holder.uNumbering = (TextView) v.findViewById(R.id.list_row_LBL_numbering);
            holder.uScore = (TextView) v.findViewById(R.id.list_LBL_score);
            holder.uName = (TextView) v.findViewById(R.id.list_row_LBL_name);
            holder.uDate = (TextView) v.findViewById(R.id.list_row_LBL_date);
            holder.uCoordinates = (TextView) v.findViewById(R.id.list_row_LBL_coordinates);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.uNumbering.setText(position + 1 + ")");
        holder.uScore.setText(" " + listData.get(position).getScore());
        holder.uName.setText(listData.get(position).getName());
        holder.uDate.setText(listData.get(position).getDate());
        holder.uCoordinates.setText(listData.get(position).getLatitude() + "," + listData.get(position).getLongitude());

        return v;
    }

    static class ViewHolder {
        TextView uScore;
        TextView uNumbering;
        TextView uName;
        TextView uDate;
        TextView uCoordinates;
    }
}
