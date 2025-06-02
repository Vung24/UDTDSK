package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity.RemindWaterActivity;

import java.util.Date;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String remain = intent.getStringExtra("remain"); // nhận dữ liệu lượng nước còn thiếu (nếu có)
        if (remain == null) {
            remain = "0"; // Giá trị mặc định nếu remain là null
        }

        // Tạo intent khi bấm vào notification
        Intent notificationIntent = new Intent(context, RemindWaterActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Tạo NotificationBuilder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "reminder_channel_id")
                .setContentTitle("Nhắc nhở uống nước")
                .setContentText("Đến giờ bạn uống nước rồi.\nHôm nay bạn cần uống thêm " + remain + " ml để đủ lượng nước.")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Đến giờ bạn uống nước rồi.\nHôm nay bạn cần uống thêm " + remain + " ml để đủ lượng nước."));

        // Tạo notification channel (Android 8+ yêu cầu)
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        "reminder_channel_id",
                        "Nhắc uống nước",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel.setDescription("Kênh nhắc nhở uống nước");
                notificationManager.createNotificationChannel(channel);
            }

            // Gửi thông báo
            notificationManager.notify(getNotificationId(), builder.build());
        } else {
            Log.e("ReminderReceiver", "NotificationManager is null");
        }
    }

    private int getNotificationId() {
        return (int) new Date().getTime(); // mỗi lần gửi notification có ID riêng
    }
}
