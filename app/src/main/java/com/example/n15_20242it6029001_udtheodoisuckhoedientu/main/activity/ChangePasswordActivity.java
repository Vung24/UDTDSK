package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.dao.UserDAO;
public class ChangePasswordActivity extends AppCompatActivity {
    EditText editEmail, editOldPassword, editNewPassword, editConfirm;
    Button btnChange;
    UserDAO userDAO;
    boolean isPasswordVisible = false;
    boolean isPasswordVisible1 = false;
    boolean isPasswordVisible2 = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_activity_change_password);
        getWidget();
        editOldPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (editOldPassword.getRight() - editOldPassword.getCompoundDrawables()[2].getBounds().width())){
                        //Trang thai cua icon (mat dong -> mat mo)
                        isPasswordVisible = !isPasswordVisible;
                        if (isPasswordVisible) {
                            //Thay doi loai nhap lieu cua password
                            editOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            //Sua icon dong -> mo
                            editOldPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0); // Icon mắt mở
                        } else {
                            //Thay doi loai nhap lieu de an mat khau
                            editOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            //Sua icon mo -> dong
                            editOldPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0); // Icon mắt đóng
                        }

                        // Đưa con trỏ văn bản về cuối
                        editOldPassword.setSelection(editOldPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        editNewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (editNewPassword.getRight() - editNewPassword.getCompoundDrawables()[2].getBounds().width())){
                        isPasswordVisible1 = !isPasswordVisible1;
                        if (isPasswordVisible1) {
                            editNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0); // Icon mắt mở
                        } else {
                            editNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0); // Icon mắt đóng
                        }

                        // Đưa con trỏ văn bản về cuối
                        editNewPassword.setSelection(editNewPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        editConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (editConfirm.getRight() - editConfirm.getCompoundDrawables()[2].getBounds().width())){
                        isPasswordVisible2 = !isPasswordVisible2;
                        if (isPasswordVisible2) {
                            editConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editConfirm.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0); // Icon mắt mở
                        } else {
                            editConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editConfirm.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0); // Icon mắt đóng
                        }

                        // Đưa con trỏ văn bản về cuối
                        editConfirm.setSelection(editConfirm.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

    }
    public void getWidget(){
        userDAO = new UserDAO(this);
        editEmail = findViewById(R.id.editEmail);
        editOldPassword = findViewById(R.id.editOldPass);
        editNewPassword = findViewById(R.id.editNewPass);
        editConfirm = findViewById(R.id.editConfirm);
        btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }
    //Thay doi matkhau
    public void change(){
        String email = editEmail.getText().toString();
        String oldPassword = editOldPassword.getText().toString();
        String newPassword = editNewPassword.getText().toString();
        String confirmPassword = editConfirm.getText().toString();
        //Validate dieu kien
        if(!isValidPassword(newPassword)){
            Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ cái viết hoa, chữ cái thường, số và ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            return;
        }
        //So sanh mat khau moi
        if (!isConfirm(newPassword, confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String changePasswordResult = userDAO.changePassword(email, oldPassword, newPassword);
            Toast.makeText(this, changePasswordResult, Toast.LENGTH_SHORT).show();
            if (changePasswordResult.equals("Thành công")) {
                finish();
            }
        }
    }
    private boolean isConfirm(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    private boolean isValidPassword(String password) {
        if (password.contains(" ") || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[!@#$%^&*()-_+=<>?].*")){
            return false;
        }
        return true;
    }
    public void back(View v){
        finish();
    }
}
