package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.HeartHealthDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.ProfileDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.HeartHealth;

public class HeartHealthActivity extends AppCompatActivity {

    EditText editHeartBeat, editHeartPressure, editHHDate;
    Button btnAdd, btnList;
    ImageButton btnDate;
    Calendar cal;
    public HeartHealthDAO myDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ntv_activity_health_heart);
        getWidget();
        getDateDefault();
    }
    private void getDateDefault(){
        cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String strDate = dateFormat.format(cal.getTime());
        editHHDate.setText(strDate);
    }
    private void showDatePickerDialog(){
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editHHDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                cal.set(year,month,dayOfMonth);
            }
        };
        //Set date khi mở hộp thoại
        String s = editHHDate.getText()+"";
        String strArrTmp[] = s.split("-");
        int day = Integer.parseInt(strArrTmp[0]);
        int month = Integer.parseInt(strArrTmp[1])-1;
        int year = Integer.parseInt(strArrTmp[2]);
        DatePickerDialog pic = new DatePickerDialog(HeartHealthActivity.this,callback,year,month,day);
        pic.setTitle("Choose create date");
        pic.show();
    }
    private void getWidget(){
        editHeartBeat = findViewById(R.id.edtHeartBeat);
        editHeartPressure = findViewById(R.id.edtHeartPressure);
        editHHDate = findViewById(R.id.editHHDate);
        btnAdd = findViewById(R.id.btnAdd);
        btnDate = findViewById(R.id.btnDate);
        btnList = findViewById(R.id.btnList);
        btnAdd.setOnClickListener(new doSomeTHing());
        btnDate.setOnClickListener(new doSomeTHing());
        btnList.setOnClickListener(new doSomeTHing());
    }
    protected class doSomeTHing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnAdd){
                Add();
            }
            if (id == R.id.btnList){
                Intent intent1 = new Intent(HeartHealthActivity.this, HeartHealthListActivity.class);
                startActivity(intent1);
            }
            if (id == R.id.btnDate){
                showDatePickerDialog();
            }
        }
    }
    private void Add() {
        try {
            myDatabase = new HeartHealthDAO(HeartHealthActivity.this);
            HeartHealth heartHealth = new HeartHealth();

            if (editHeartBeat.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nhịp tim không được để trống!", Toast.LENGTH_SHORT).show();
                editHeartBeat.requestFocus();
            } else if (editHeartPressure.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nhịp tim không được để trống!", Toast.LENGTH_SHORT).show();
                editHeartPressure.requestFocus();
            } else {
                float heartBeat = Float.parseFloat(editHeartBeat.getText().toString());
                float heartPressure = Float.parseFloat(editHeartPressure.getText().toString());
                String hhDate = editHHDate.getText().toString();
                ProfileDAO profile = new ProfileDAO(this);
                ArrayList<String> arrDiseases = profile.getDisease();
                String ageGroup = profile.getAgeGroup();
                String status = getStatus(arrDiseases, heartBeat, heartPressure,ageGroup);
                heartHealth.setHeartbeat(heartBeat);
                heartHealth.setHeart_pressure(heartPressure);
                heartHealth.setCreated_date(hhDate);
                heartHealth.setStatus(status);
                myDatabase.insert(heartHealth);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                clear();
            }
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public String getStatus(ArrayList<String> arrDiseases, float heartBeat, float heartPressure, String ageGroup){
        String status = "";
        String priorityDisease = "Không bị bệnh nền";
        String[] priority  = {"Rối loạn tâm thần", "Mệt mỏi", "Huyết áp cao", "Tiểu đường"};
        for(String p : priority){
            for (String d : arrDiseases){
                if(d.contains(p)){
                    priorityDisease = p;
                    break;
                }
            }
        }
        //Ngưỡng giới hạn
        float heartBeatLowThreshold=0;
        float heartBeatHighThreshold=0;
        float heartPressureLowThreshold=0;
        float heartPressureHighThreshold=0;
        switch (priorityDisease) {
            case "Mệt mỏi":
                switch (ageGroup){
                    case "Trẻ em":
                    case "Thanh niên":
                        heartBeatLowThreshold = 50.0f;
                        heartBeatHighThreshold = 70.0f;
                        heartPressureLowThreshold = 70.0f;
                        heartPressureHighThreshold = 110.0f;
                        break;
                    case "Thanh thiếu niên":
                    case "Vị thành niên":
                        heartBeatLowThreshold = 55.0f;
                        heartBeatHighThreshold = 75.0f;
                        heartPressureLowThreshold = 75.0f;
                        heartPressureHighThreshold = 115.0f;
                        break;
                    case "Trung niên":
                    case "Người già":
                        heartBeatLowThreshold = 60.0f;
                        heartBeatHighThreshold = 80.0f;
                        heartPressureLowThreshold = 80.0f;
                        heartPressureHighThreshold = 120.0f;
                        break;
                }
                break;
            case "Huyết áp cao":
                switch (ageGroup){
                    case "Trẻ em":
                    case "Thanh niên":
                        heartBeatLowThreshold = 70.0f;
                        heartBeatHighThreshold = 80.0f;
                        heartPressureLowThreshold = 90.0f;
                        heartPressureHighThreshold = 120.0f;
                        break;
                    case "Thanh thiếu niên":
                    case "Vị thành niên":
                        heartBeatLowThreshold = 80.0f;
                        heartBeatHighThreshold = 90.0f;
                        heartPressureLowThreshold = 100.0f;
                        heartPressureHighThreshold = 130.0f;
                        break;
                    case "Trung niên":
                    case "Người già":
                        heartBeatLowThreshold = 90.0f;
                        heartBeatHighThreshold = 100.0f;
                        heartPressureLowThreshold = 120.0f;
                        heartPressureHighThreshold = 140.0f;
                        break;
                }
                break;
            case "Tiểu đường":
                switch (ageGroup){
                    case "Trẻ em":
                    case "Thanh niên":
                        heartBeatLowThreshold = 65.0f;
                        heartBeatHighThreshold = 85.0f;
                        heartPressureLowThreshold = 85.0f;
                        heartPressureHighThreshold = 125.0f;
                        break;
                    case "Thanh thiếu niên":
                    case "Vị thành niên":
                        heartBeatLowThreshold = 70.0f;
                        heartBeatHighThreshold = 90.0f;
                        heartPressureLowThreshold = 90.0f;
                        heartPressureHighThreshold = 130.0f;
                        break;
                    case "Trung niên":
                    case "Người già":
                        heartBeatLowThreshold = 75.0f;
                        heartBeatHighThreshold = 95.0f;
                        heartPressureLowThreshold = 95.0f;
                        heartPressureHighThreshold = 135.0f;
                        break;
                }
                break;
            case "Rối loạn tâm thần":
                switch (ageGroup){
                    case "Children":
                    case "Youth":
                        heartBeatLowThreshold = 60.0f;
                        heartBeatHighThreshold = 80.0f;
                        heartPressureLowThreshold = 80.0f;
                        heartPressureHighThreshold = 120.0f;
                        break;
                    case "Thanh thiếu niên":
                    case "Vị thành niên":
                        heartBeatLowThreshold = 65.0f;
                        heartBeatHighThreshold = 85.0f;
                        heartPressureLowThreshold = 85.0f;
                        heartPressureHighThreshold = 125.0f;
                        break;
                    case "Trung niên":
                    case "Người già":
                        heartBeatLowThreshold = 70.0f;
                        heartBeatHighThreshold = 90.0f;
                        heartPressureLowThreshold = 90.0f;
                        heartPressureHighThreshold = 130.0f;
                        break;
                }
                break;
            case "Không có bệnh nền":
                switch (ageGroup){
                    case "Trẻ em":
                    case "Thanh niên":
                        heartBeatLowThreshold = 70.0f;
                        heartBeatHighThreshold = 110.0f;
                        heartPressureLowThreshold = 90.0f;
                        heartPressureHighThreshold = 120.0f;
                        break;
                    case "Thanh thiếu niên":
                    case "Vị thành niên":
                        heartBeatLowThreshold = 60.0f;
                        heartBeatHighThreshold = 100.0f;
                        heartPressureLowThreshold = 80.0f;
                        heartPressureHighThreshold = 120.0f;
                        break;
                    case "Trung niên":
                    case "Người già":
                        heartBeatLowThreshold = 60.0f;
                        heartBeatHighThreshold = 80.0f;
                        heartPressureLowThreshold = 120.0f;
                        heartPressureHighThreshold = 140.0f;
                        break;
                }
        }
        //Set status
        if (heartBeat < heartBeatLowThreshold || heartPressure < heartPressureLowThreshold) {
            status = "Thấp";
        } else if (heartBeat >= heartBeatLowThreshold && heartBeat <= heartBeatHighThreshold &&
                heartPressure >= heartPressureLowThreshold && heartPressure <= heartPressureHighThreshold) {
            status = "Bình thường";
        } else if (heartBeat > heartBeatHighThreshold || heartPressure > heartPressureHighThreshold) {
            status = "Cao";
        } else {
            status = "Tăng huyết áp";
        }
        return status;
    }
    private void clear(){
        editHeartBeat.setText("");
        editHeartPressure.setText("");
        editHeartBeat.requestFocus();
    }
    public void back(View v){
        finish();
    }
}