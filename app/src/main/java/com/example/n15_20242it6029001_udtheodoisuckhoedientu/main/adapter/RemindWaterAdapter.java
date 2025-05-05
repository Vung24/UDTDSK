package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.RemindWater;

public class RemindWaterAdapter extends ArrayAdapter<RemindWater> {

    private Context context;

    public RemindWaterAdapter(Context context, List<RemindWater> remindWaterList) {
        super(context, 0,remindWaterList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ntv_custom_list_water, parent, false);
        }
        RemindWater remindWater =getItem(position);
        // Ánh xạ các View trong layout custom_list_water.xml
        ImageView imageViewCup = convertView.findViewById(R.id.imageViewCup);
        TextView textViewWaterInfo = convertView.findViewById(R.id.textViewWaterInfo);
        TextView textViewTime = convertView.findViewById(R.id.textViewTime);
        //Thiết lập các giá trị cho các TextView
        imageViewCup.setImageResource(R.drawable.ic_water_glass);
        textViewWaterInfo.setText("Số lượng: " + remindWater.getAmount() + " ml");
        textViewTime.setText("Thời gian: " + remindWater.getCreated_time());

        return convertView;
    }


}
