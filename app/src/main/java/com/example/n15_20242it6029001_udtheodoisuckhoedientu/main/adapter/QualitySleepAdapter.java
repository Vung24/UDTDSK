package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.QualitySleep;

public class QualitySleepAdapter extends ArrayAdapter<QualitySleep> {
    private Context context;

    public QualitySleepAdapter(Context context, List<QualitySleep> qualitySleepList) {
        super(context, 0, qualitySleepList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem convertView có được sử dụng lại hay không, nếu không thì inflate layout mới
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ntv_custom_list_sleep, parent, false);
        }

        // Lấy đối tượng QualitySleep từ danh sách
        QualitySleep qualitySleep = getItem(position);

        // Ánh xạ các View trong layout custom_list_item.xml
        TextView statusTextView = convertView.findViewById(R.id.tvStatus);
        TextView startTextView = convertView.findViewById(R.id.tvStart);
        TextView finishTextView = convertView.findViewById(R.id.tvFinish);
        TextView dateTextView = convertView.findViewById(R.id.tvCreateD);

        // Thiết lập giá trị cho các TextView
        statusTextView.setText("Trạng thái: " + qualitySleep.getStatus());
        startTextView.setText("Bắt đầu: " + qualitySleep.getStart_sleep() + " ");
        finishTextView.setText("Kết thúc:" + qualitySleep.getFinish_sleep() +" ");
        dateTextView.setText("Ngày: " + qualitySleep.getCrated_date());

        return convertView;
    }
}
