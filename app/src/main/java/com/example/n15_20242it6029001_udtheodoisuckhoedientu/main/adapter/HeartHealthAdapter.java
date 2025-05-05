package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.HeartHealth;

public class HeartHealthAdapter extends ArrayAdapter {
    Activity context;
    int LayoutID;
    ArrayList<HeartHealth> list = null;

    public HeartHealthAdapter(@NonNull Activity context, int resource, ArrayList<HeartHealth> list) {
        super(context, resource,list);
        this.context = context;
        LayoutID = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutID,null);
        if (list.size() > 0 && position >= 0){
            HeartHealth heartHealth = list.get(position);
            final TextView txtHeartBeat = convertView.findViewById(R.id.txtHeartBeat);
            final TextView txtHeartPressure = convertView.findViewById(R.id.txtHeartPressure);
            final TextView txtStatus = convertView.findViewById(R.id.txtStatus);
            final TextView txtDate = convertView.findViewById(R.id.txtDate);

            txtHeartBeat.setText(heartHealth.getHeartbeat()+" bmp");
            txtHeartPressure.setText(heartHealth.getHeart_pressure()+" mmHg");
            txtStatus.setText(heartHealth.getStatus()+"");
            txtDate.setText(heartHealth.getCreated_date()+"");
        }
        return convertView;
    }
}
