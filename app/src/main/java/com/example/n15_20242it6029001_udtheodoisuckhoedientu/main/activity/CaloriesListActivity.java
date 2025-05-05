package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.CaloriesDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.Calories;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.activity.MainActivity;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.adapter.CaloriesAdapter;
public class CaloriesListActivity extends AppCompatActivity {
    Button btnHome, btnRemove;
    ListView lv;
    CaloriesDAO caloriesDAO;
    ArrayAdapter caloriesAdapter;
    List<Calories> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhs_activity_calories_list);
        getWidget();

    }
    public void getWidget(){
        caloriesDAO = new CaloriesDAO(this);
        btnHome = findViewById(R.id.btnHome);
        btnRemove = findViewById(R.id.btnRemove);
        btnHome.setOnClickListener(new doSomeThing());
        btnRemove.setOnClickListener(new doSomeThing());
        setListView();
        getSelectLV();
    }
    public void setListView(){
        list = new ArrayList<>();
        lv = findViewById(R.id.lvCalories);//Kết nối với giao diện
        list.clear();//Xoá toàn bộ dữ liệu trong list
        list = caloriesDAO.getAll();//Gán danh sách lấy từ DAO
        caloriesAdapter = new CaloriesAdapter(this,list );//Gán danh sách vào Adapter
        lv.setAdapter(caloriesAdapter);//Gán Adapter vào lv
    }
    public void back(View v){
        finish();
    }
    public class doSomeThing implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id==R.id.btnHome){
                Intent intent = new Intent(CaloriesListActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }
    public void getSelectLV(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                remove(position);
            }
        });
    }
    public void remove(final int selectedPosition){
        Context context = CaloriesListActivity.this;
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition != ListView.INVALID_POSITION) {
                    Calories selectedCalories = list.get(selectedPosition);
                    // Hiển thị AlertDialog để xác nhận việc xóa
                    new AlertDialog.Builder(context)
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có muốn xóa mục này không?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Gọi phương thức xóa từ caloriesDAO để xóa dữ liệu từ cơ sở dữ liệu
                                    int result = (int) caloriesDAO.delete(String.valueOf(selectedCalories.getCalories_id()));

                                    if (result > 0) {
                                        // Nếu xóa thành công, cập nhật danh sách và cập nhật ListView
                                        list.remove(selectedPosition);
                                        caloriesAdapter.notifyDataSetChanged();
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                } else {
                    Toast.makeText(context, "Không có mục nào", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}