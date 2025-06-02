package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.RemindWater;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.RemindWaterDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.adapter.RemindWaterAdapter;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.receiver.ReminderReceiver;

public class RemindWaterActivity extends AppCompatActivity {
    TextView tvTarget; //luong nuoc
    ProgressBar ctrUongNuoc;

    ListView dsNuoc;

    ImageButton imgCreate, imgUpdate;

    RemindWaterDAO remindWaterDAO;

    List<RemindWater> list = new ArrayList<>();

    RemindWaterAdapter arrayAdapter;
    Context context;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ntv_activity_remind_water);
        getWidget();
        startNewDay(); // dat thoi diem bat dau la thoi diem hien tai
        updateTvTarget();

        // Tự động đặt lịch nhắc nhở khi Activity được tạo
        ktraLuongNuoc();

        imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWaterDialog();
            }
        });
        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhapTanSuat();
            }
        });
        dsNuoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                remove(position);
                return false;
            }
        });
    }

    public void getWidget() {
        remindWaterDAO = new RemindWaterDAO(this);
        tvTarget = findViewById(R.id.tvTarget);
        ctrUongNuoc = findViewById(R.id.progressDrinkW);
        dsNuoc  = findViewById(R.id.lvWater);
        imgCreate = findViewById(R.id.imgCreate);
        imgUpdate = findViewById(R.id.imgUpdate);
        user_id = remindWaterDAO.getUser_id();
        //khởi tạo các biến
        context = this;
        //hien thi du lieu khi chay chuong trinh
        list.clear();


        list = remindWaterDAO.getAllInDay(getCurrentDate());
        arrayAdapter = new RemindWaterAdapter(context, list);
        dsNuoc.setAdapter(arrayAdapter);

        int totalConsumed = TinhTongNuoc(); // Hàm này cần được triển khai để tính tổng lượng nước đã uống
        float target = calculateTarget();
        ctrUongNuoc.setMax((int) target);
        ctrUongNuoc.setProgress(totalConsumed);
    }
    // kiem tra luong nuoc uong trong ngay
    private void ktraLuongNuoc() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        float frequency = remindWaterDAO.getFrequencyFromDatabase();

        if (frequency > 0) {
            // Tính toán thời gian giữa mỗi lần nhắc nhở (đơn vị: milliseconds)
            long reminderInterval = (long) (frequency * 60 * 1000); // Chuyển đổi phút thành milliseconds

            int totalConsumed = TinhTongNuoc();
            float target = calculateTarget();
            Log.d("Nhắc nhở gỡ lỗi", "Tổng số đã uống: " + totalConsumed);
            Log.d("Nhắc nhở gỡ lỗi", "tiêu thụ: " + target);
            if (totalConsumed < target) {
                // Đặt lịch nhắc nhở sử dụng AlarmManager
                DatLichReminder(reminderInterval);
            } else {
                // Nếu totalConsumed >= target, hủy bỏ lịch nhắc nhở
                HuyReminder();
            }
        }
    }
    // dat lich nhac nho uong nuoc
    private void DatLichReminder(long interval) {
        // Sử dụng AlarmManager để đặt lịch nhắc nhở
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int totalConsumed = TinhTongNuoc();
        float target = calculateTarget();

        int remain = (int) (target - totalConsumed);

        // Tạo Intent để gửi broadcast khi nhắc nhở được kích hoạt
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.setAction("MyAction");
        intent.putExtra("remain", String.valueOf(remain));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Thiết lập lịch nhắc nhở cứ sau mỗi khoảng thời gian interval
        // Bắt đầu từ thời điểm hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.d("Nhắc nhở chuông", "Thiết lập lại chuông");

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + interval, interval, pendingIntent);
    }
    // ham huy nhac nho uong nuoc
    private void HuyReminder() {
        Intent intent = new Intent(RemindWaterActivity.this, ReminderReceiver.class);
        intent.setAction("MyAction");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Hủy bỏ lịch nhắc nhở
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        // Hủy bỏ cả PendingIntent
        pendingIntent.cancel();
    }

    private void NhapTanSuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tần suất cập nhật");

        View view = getLayoutInflater().inflate(R.layout.ntv_dialog_update_freequency, null);
        final EditText edtFrequency = view.findViewById(R.id.edtFrequency);

        builder.setView(view);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText
                String frequencyStr = edtFrequency.getText().toString();

                // Kiểm tra xem người dùng đã nhập frequency chưa
                if (!frequencyStr.isEmpty()) {
                    float frequency = Float.parseFloat(frequencyStr);

                    // Cập nhật giá trị trong CSDL
                    CapNhatTanSuat(frequency);
                    ktraLuongNuoc();
                    // Cập nhật giao diện
                    updateUI();
                    // Thông báo sau khi tạo chuông thành công
                    Toast.makeText(context, "Bạn đã tạo chuông với tần suất " + frequency + " phút", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
    // Xoa du lieu khi qua ngay moi
    private void startNewDay() {
        // Lấy ngày hiện tại
        String currentDate = getCurrentDate();
        // Lấy ngày lưu trữ trong CSDL (nếu có)
        String createdDate = remindWaterDAO.getCreatedDate(); // Triển khai hàm này trong RemindWaterDAO
        // So sánh ngày hiện tại với ngày lưu trữ
        if (!currentDate.equals(createdDate)) {
            // Nếu là ngày mới, thực hiện reset CSDL và đặt lịch nhắc nhở
            remindWaterDAO.deleteAll();
            updateProgressBar();
            updateListView();

        }
    }

    private void CapNhatTanSuat(float frequency) {
        remindWaterDAO.updateFrequency( frequency);
    }
    private void showAddWaterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm lượng nước");

        View view = getLayoutInflater().inflate(R.layout.ntv_dialog_add_water, null);
        final EditText edtAmount = view.findViewById(R.id.edtAmount);

        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText
                String amountStr = edtAmount.getText().toString();

                // Kiểm tra xem người dùng đã nhập amount chưa
                if (!amountStr.isEmpty()) {
                    float amount = Float.parseFloat(amountStr);

                    // Thêm vào CSDL
                    insertWater(amount);

                    // Cập nhật giao diện
                    updateUI();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void insertWater(float amount) {
        String time = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        String date = getCurrentDate();
        RemindWater remindWater = new RemindWater(amount,2,time,date,user_id);
        remindWaterDAO.insert(remindWater);
        ktraLuongNuoc();
    }

    private void updateUI() {
        updateProgressBar();
        updateListView();
        updateTvTarget();
    }
    private void updateListView() {
        context = this;
        list.clear();
        remindWaterDAO = new RemindWaterDAO(context);

        list = remindWaterDAO.getAllInDay(getCurrentDate());

        arrayAdapter = new RemindWaterAdapter(context,list);
        dsNuoc.setAdapter(arrayAdapter);
    }

    // Phương thức để lấy ngày hiện tại
    private String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(formatter);
    }

    private float calculateTarget() {
        // Lấy giá trị cân nặng từ CSDL
        float weight = getWeightFromDatabase();
        // Áp dụng công thức tính target
        return (weight * 0.03f) * 1000;
    }

    private float getWeightFromDatabase() {
        // Triển khai hàm này để truy vấn CSDL và lấy giá trị cân nặng
        int userId = remindWaterDAO.getUser_id(); // Thay thế bằng userId thích hợp
        float weight = remindWaterDAO.getWeightFromDatabase(userId);
        // Kiểm tra nếu giá trị trọng lượng không hợp lệ hoặc là rỗng
        if (Float.isNaN(weight) || weight <= 0) {
            return 66.6f;
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Cân nặng không hợp lệ!");
        }
        return weight;
    }
    @SuppressLint("DefaultLocale")
    private void updateTvTarget() {
        int totalConsumed = TinhTongNuoc();
        float target = calculateTarget();
        // Tính toán giá trị cần hiển thị trong edtTarget
        float remainingTarget = target - totalConsumed;
        // Hiển thị giá trị trong edtTarget
        tvTarget.setText(String.format("%d/%.0f ml", totalConsumed, target));
    }

    // cap nhat tien trinh
    private void updateProgressBar() {
        int totalConsumed = TinhTongNuoc(); // Hàm này cần được triển khai để tính tổng lượng nước đã uống
        float target = calculateTarget();
        ProgressBar ctrUongNuoc = findViewById(R.id.progressDrinkW);
        ctrUongNuoc.setMax((int)target);
        ctrUongNuoc.setProgress(totalConsumed);
    }

    private int TinhTongNuoc() {
        // Triển khai hàm này để tính tổng lượng nước đã uống
        return remindWaterDAO.getTotalConsumed(); // Thay thế 0 bằng user_id thích hợp
    }
    public void remove(int position){
        if (position != ListView.INVALID_POSITION) {
            RemindWater selectedRemindWater = list.get(position);
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa mục này?")
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Gọi phương thức xóa từ RemindWaterDAO để xóa dữ liệu từ cơ sở dữ liệu
                            int result = (int)remindWaterDAO.delete(String.valueOf(selectedRemindWater.getRemind_water_id()));

                            if (result > 0) {
                                // Nếu xóa thành công, cập nhật danh sách và cập nhật ListView
                                list.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                updateTvTarget();
                                updateProgressBar();
                                Toast.makeText(context, "Xóa mục thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        } else {
            Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View v){
        finish();
    }
}