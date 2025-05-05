package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.ProfileDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.CaloriesDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.Calories;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.Profile;
public class CaloriesActivity extends AppCompatActivity {
    ImageButton imgBtnDate;
    EditText edtIntake, edtBurned;
    TextView txtDate;
    Button btnAdd, btnList;
    CaloriesDAO dbHandler;
    public ProfileDAO profileDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhs_activity_calories);
        getWidget();
    }

    public void getWidget() {
        profileDAO = new ProfileDAO(this);
        dbHandler = new CaloriesDAO(this);
        imgBtnDate = findViewById(R.id.imgDate);
        edtIntake = findViewById(R.id.edtIntake);
        edtBurned = findViewById(R.id.edtBurned);
        txtDate = findViewById(R.id.txtDate);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        imgBtnDate.setOnClickListener(new doSomeThing());
        btnAdd.setOnClickListener(new doSomeThing());
        btnList.setOnClickListener(new doSomeThing());
    }

    //Them moi
    public void addCalories() {
        if (edtIntake.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập lượng kcal tiêu thụ", Toast.LENGTH_SHORT).show();
            return;
        } else if (edtBurned.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập lượng kcal đốt cháy", Toast.LENGTH_SHORT).show();
            return;
        } else {
            float intake = Float.parseFloat(edtIntake.getText().toString());
            float burned = Float.parseFloat(edtBurned.getText().toString());
            String date = txtDate.getText().toString();
            if (date.equalsIgnoreCase("Now")) {
                date = getCurrentDateAsString();
            }
            if (validateKcalBurned(burned)) {
                String status = "";
                ArrayList<String> arrDiseases = new ArrayList<>();
                arrDiseases.addAll(profileDAO.getDisease());
                status = getStatusFromAgeGroup(intake, arrDiseases);
                Calories calories = new Calories(intake, burned, status, date);
                dbHandler.insert(calories);
                edtIntake.setText("");
                edtBurned.setText("");
                txtDate.setText("Hiện tại");
                showAdviceDialog(status, intake, burned);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Nhắc nhở");
                builder.setMessage("Lượng kcal đốt cháy không hợp lý\nTrung bình trẻ em đốt cháy khoảng 500 (kcal) 1 ngày \nTrung bình người lớn đốt cháy khoảng 1000 (Kcal) 1 ngày")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                edtBurned.requestFocus();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
        }
    }
    //Kiem tra dieu kien nhap kcal
    public boolean validateKcalBurned(float burned) {
        if (burned < 500) {
            return false;
        } else {
            return true;
        }
    }

    //Xac dinh ho so: benh nen, gioi tinh va do tuoi
    public String getStatusFromAgeGroup(float intake, ArrayList<String> arrDiseases) {
        String priorityDisease = "Không có bệnh nền";
        String[] priority = {"Rối loạn tâm thần", "Mệt mỏi", "Huyết áp cao", "Tiểu đường"};
        for (String p : priority) {
            for (String d : arrDiseases) {
                if (d.equalsIgnoreCase(p)) {
                    priorityDisease = p;
                    break;
                }
            }
        }
        String ageGroup = profileDAO.getAgeGroup();
        Profile profile = profileDAO.getProfile();
        String gender = profile.getSex();
        //Tim ra nhom tuoi tuong ung de su dung phuong thuc danh gia kcal theo tung nhom tuoi
        if (ageGroup.equals("Trẻ em")) {
            return evaluateCaloriesStatusForYoungChildren(intake, gender);
        } else if (ageGroup.equals("Thanh niên")) {
            return evaluateCaloriesStatusForChildren(intake, gender);
        } else if (ageGroup.equals("Thanh thiếu niên")) {
            return evaluateCaloriesStatusForTeens(intake, gender);
        } else if (ageGroup.equals("Vị thành niên") || ageGroup.equals("Trung niên")) {
            return evaluateCaloriesStatusForAdultsAndMiddleAge(intake, gender, priorityDisease);
        } else if (ageGroup.equals("Người già")) {
            return evaluateCaloriesStatusForSeniors(intake, gender, priorityDisease);
        }
        return "";
    }

    //Danh gia tinh trang kcal
    private String evaluateCaloriesStatus(float intake, float minNeed, float maxNeed) {
        //Kcal tieu thu can >= kcal can toi thieu va <= kcal toi da thi hop ly dua theo gioi tinh, tuoi, co benh nen hay khong
        if (intake >= minNeed && intake <= maxNeed) {
            return "cân bằng";
        } else if (intake > maxNeed) {
            return "quá nhiều";
        } else {
            return "quá ít";
        }
    }

    //Loi khuyen ve kcal tieu thu va viec dot chay calo
    private String recommendation(String status, float intake, float burned) {
        //Chenh lech kcal
        float difference = intake - burned;
        String recommend = "Hôm nay lượng calo bạn tiêu thụ là " + status;
        if (difference > 500) {
            recommend += "\nLượng calo bạn đốt cháy quá thấp. Hãy tập thể dục nhiều hơn để tránh nguy cơ béo phì. ";
        } else if (difference < 250) {
            recommend += "\nLượng calo bạn đốt cháy quá cao. " + "Hãy chú ý đến cường độ tập luyện của bạn. " +
                    "\nBạn cần bổ sung thêm " + (250 - difference) + " kcal bây giờ.";
        } else {
            recommend += "\nChênh lệch calo của bạn đã được cân bằng. Hãy cố gắng duy trì thói quen sống lành mạnh. ";
        }
        //Neu tieu thu qua thap thi dua ra chu y
        if (intake < 1000) {
            recommend += "\nChú ý: Duy trì lượng kcal " + intake + " Không đảm bảo sức khỏe!";
        }
        return recommend;
    }

    //Danh gia calo cho tre em theo tung gioi tinh
    private String evaluateCaloriesStatusForYoungChildren(float intake, String gender) {
        if (gender.equals("Nam")) {
            return evaluateCaloriesStatus(intake, 1200, 1600);
        } else {
            return evaluateCaloriesStatus(intake, 1000, 1400);
        }
    }

    //Danh gia calo cho thanh nien theo tung gioi tinh
    private String evaluateCaloriesStatusForChildren(float intake, String gender) {
        if (gender.equals("Nam")) {
            return evaluateCaloriesStatus(intake, 1600, 2200);
        } else {
            return evaluateCaloriesStatus(intake, 1400, 2000);
        }
    }

    //Danh gia calo cho thanh thieu nien theo tung gioi tinh
    private String evaluateCaloriesStatusForTeens(float intake, String gender) {
        if (gender.equals("Nam")) {
            return evaluateCaloriesStatus(intake, 2200, 3000);
        } else {
            return evaluateCaloriesStatus(intake, 2000, 2800);
        }
    }

    //Danh gia calo cho nguoi vi thanh nien va tuoi trung nien dua theo tung gioi tinh kem benh nen (neu co)
    private String evaluateCaloriesStatusForAdultsAndMiddleAge(float intake, String gender, String disease) {
        if (gender.equals("Nam")) {
            if (disease.equals("Tiểu đường")) {
                return evaluateCaloriesStatus(intake, 1400, 2000);
            } else if (disease.equals("Huyết áp cao")) {
                return evaluateCaloriesStatus(intake, 2200, 2800);
            } else {
                return evaluateCaloriesStatus(intake, 2400, 3000);
            }
        } else {
            if (disease.equals("Tiểu đường")) {
                return evaluateCaloriesStatus(intake, 1200, 1800);
            } else if (disease.equals("Huyết áp cao")) {
                return evaluateCaloriesStatus(intake, 1800, 2200);
            } else {
                return evaluateCaloriesStatus(intake, 2000, 2800);
            }
        }
    }

    //Danh gia calo cho nguoi gia theo gioi tinh kem benh nen(neu co)
    private String evaluateCaloriesStatusForSeniors(float intake, String gender, String disease) {
        if (gender.equals("Nam")) {
            if (disease.equals("Tiểu đường")) {
                return evaluateCaloriesStatus(intake, 1400, 1800);
            } else if (disease.equals("Huyết áp cao")) {
                return evaluateCaloriesStatus(intake, 2000, 2400);
            } else {
                return evaluateCaloriesStatus(intake, 2000, 2600);
            }
        } else {
            if (disease.equals("Tiểu đường")) {
                return evaluateCaloriesStatus(intake, 1200, 1600);
            } else if (disease.equals("Huyết áp cao")) {
                return evaluateCaloriesStatus(intake, 1600, 2000);
            } else {
                return evaluateCaloriesStatus(intake, 1800, 2400);
            }
        }
    }
    //Lay thoi gian mac dinh hom nay
    public String getCurrentDateAsString() {
        Calendar calendar = Calendar.getInstance();
        //Lay thoi gian hien tai
        Date currentDate = calendar.getTime();
        //Dinh dang chuoi thanh date year-month-day
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }
    //Hien thi datePicker
    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //Set gia tri len Textview
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                txtDate.setText(i + "-" + (i1 + 1) + "-" + i2);
            }
        };
        //Khoi tao mot datePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(CaloriesActivity.this, callback, year, month, day);
        datePickerDialog.setTitle("Chọn ngày giờ");
        datePickerDialog.show();
    }
    //Hanh dong
    public class doSomeThing implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.imgDate) {
                showDatePicker();
            } else if (id == R.id.btnAdd) {
                addCalories();
            } else if (id == R.id.btnList) {
                Intent intent = new Intent(CaloriesActivity.this, CaloriesListActivity.class);
                startActivity(intent);
            }
        }
    }
    //Ket thuc hoat dong
    public void back(View v) {
        finish();
    }
    //Thong bao loi khuyen
    private void showAdviceDialog(String status, float intake, float burned) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lời khuyên về calo");
        builder.setMessage(recommendation(status, intake, burned));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
