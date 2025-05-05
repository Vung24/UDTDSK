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
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.BMIIndex;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.BMIIndexDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.ProfileDAO;
public class BMIIndexActivity extends AppCompatActivity {
    public EditText editHeight;
    public EditText editWeight;
    public EditText editDate;
    public Button btnAdd;
    public Button btnList;
    public ImageButton btnDate;
    Calendar cal;
    public BMIIndexDAO myDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ntv_activity_bmiindex);
        getWidget();
        getDateDefault();
    }
    private void getWidget(){
        editHeight = findViewById(R.id.editHeight);
        editDate = findViewById(R.id.editDate);
        editWeight = findViewById(R.id.editWeight);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        btnDate = findViewById(R.id.btnDate);
        btnAdd.setOnClickListener(new doSomeThing());
        btnDate.setOnClickListener(new doSomeThing());
        btnList.setOnClickListener(new doSomeThing());
    }
    private void getDateDefault(){
        //Lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String strDate = dateFormat.format(cal.getTime());
        editDate.setText(strDate);
    }
    private void showDatePickerDialog(){
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                cal.set(year,month,dayOfMonth);
            }
        };
        //Set date khi mở hộp thoại
        String s = editDate.getText()+"";
        String strArrTmp[] = s.split("-");
        int day = Integer.parseInt(strArrTmp[2]);
        int month = Integer.parseInt(strArrTmp[1])-1;
        int year = Integer.parseInt(strArrTmp[0]);
        DatePickerDialog pic = new DatePickerDialog(BMIIndexActivity.this,callback,year,month,day);
        pic.setTitle("Chọn ngày tạo");
        pic.show();
    }
    protected class doSomeThing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnAdd){
                Add();
            }
            if (id == R.id.btnDate){
                showDatePickerDialog();
            }
            if (id == R.id.btnList){
                Intent intent1 = new Intent(BMIIndexActivity.this, BMIIndexListActivity.class);
                startActivity(intent1);
            }
        }
    }
    private void Add() {
        try {
            myDatabase = new BMIIndexDAO(BMIIndexActivity.this);
            BMIIndex bmiIndex = new BMIIndex();
            ProfileDAO profileDAO = new ProfileDAO(this);
            if (editHeight.getText().toString().isEmpty()) {
                Toast.makeText(this, "Chiều cao không được để trống!", Toast.LENGTH_SHORT).show();
                editHeight.requestFocus();
            } else if (editWeight.getText().toString().isEmpty()) {
                Toast.makeText(this, "Cân nặng không được để trống!", Toast.LENGTH_SHORT).show();
                editWeight.requestFocus();
            } else {
                float weight = Float.parseFloat(editWeight.getText().toString());
                float height = Float.parseFloat(editHeight.getText().toString());
                String date = editDate.getText().toString();

                ArrayList<String> arrDisease = new ArrayList<>();
                arrDisease.addAll(profileDAO.getDisease());
                String ageGroup = profileDAO.getAgeGroup();
                String status = getStatus(arrDisease, height, weight,ageGroup);
                bmiIndex.setHeight(height);
                bmiIndex.setWeight(weight);
                bmiIndex.setCreated_date(date);
                bmiIndex.setStatus(status);

                myDatabase.insert(bmiIndex);
                Toast.makeText(this,"Thêm thành công", Toast.LENGTH_SHORT).show();
                clear();

            }
        }catch (Exception ex){
            Toast.makeText(this, "Lỗi quyền truy cập: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getStatus(ArrayList<String> arrDiseases, float height, float weight, String ageGroup) {
        if (weight <= 0 || height <= 0) {
            return "Dữ liệu không hợp lệ";
        }

        float BMI = weight / (height * height);
        String priorityDisease = "Không có bệnh nền";
        String[] priority = {"Rối loạn tâm thần", "Mệt mỏi", "Huyết áp cao", "Tiểu đường"};

        // Xác định bệnh nền ưu tiên
        for (String p : priority) {
            if (arrDiseases.stream().anyMatch(d -> d.equalsIgnoreCase(p))) {
                priorityDisease = p;
                break;
            }
        }

        String status = "Không xác định";

        switch (priorityDisease) {
            case "Mệt mỏi":
            case "Huyết áp cao":
            case "Tiểu đường":
            case "Rối loạn tâm thần":
            case "Không có bệnh nền":
                status = calculateBMIStatus(BMI, ageGroup);
                break;

            default:
                status = "Không xác định bệnh nền";
        }

        return status;
    }

    private String calculateBMIStatus(float BMI, String ageGroup) {
        switch (ageGroup) {
            case "Trẻ em":
            case "Thanh niên":
                if (BMI < 15) return "Suy dinh dưỡng";
                if (BMI >= 15 && BMI <= 16) return "Thiếu cân";
                if (BMI > 16 && BMI <= 23) return "Bình thường";
                if (BMI > 23 && BMI <= 28) return "Thừa cân";
                return "Béo phì";

            case "Thanh thiếu niên":
            case "Vị thành niên":
                if (BMI < 16) return "Suy dinh dưỡng";
                if (BMI >= 16 && BMI <= 17) return "Thiếu cân";
                if (BMI > 17 && BMI <= 23) return "Bình thường";
                if (BMI > 23 && BMI <= 28) return "Thừa cân";
                return "Béo phì";

            case "Trung niên":
            case "Người già":
                if (BMI < 17.5) return "Suy dinh dưỡng";
                if (BMI >= 17.5 && BMI < 18.5) return "Thiếu cân";
                if (BMI >= 18.5 && BMI <= 25) return "Bình thường";
                if (BMI > 25 && BMI <= 30) return "Thừa cân";
                return "Béo phì";

            default:
                return "Không xác định";
        }
    }


    private void clear(){
        editHeight.setText("");
        editWeight.setText("");
        editHeight.requestFocus();
    }

    public void back(View v){
        finish();
    }

}
