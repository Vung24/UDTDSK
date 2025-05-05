package com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.activity;
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
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.objects.User;
public class RegisterActivity extends AppCompatActivity {
    EditText editUserName, editPassword, editConfirm, editEmail;
    Button btnSignUp;
    UserDAO userDAO;
    boolean isPasswordVisible = false;
    boolean isPasswordVisible1 = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_activity_register);
        getWidget();
        editPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (editPassword.getRight() - editPassword.getCompoundDrawables()[2].getBounds().width())){
                        isPasswordVisible = !isPasswordVisible;
                        if (isPasswordVisible) {
                            editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0); // Icon mắt mở
                        } else {
                            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0); // Icon mắt đóng
                        }

                        // Đưa con trỏ văn bản về cuối
                        editPassword.setSelection(editPassword.getText().length());
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
                        isPasswordVisible1 = !isPasswordVisible1;
                        if (isPasswordVisible1) {
                            editConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editConfirm .setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0); // Icon mắt mở
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
    private void getWidget(){
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        editConfirm = findViewById(R.id.editConfirm);
        editEmail = findViewById(R.id.editEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new doSomeThing());
    }
    protected class doSomeThing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnSignUp){
                SignUp();
            }
        }
    }


    /*
     * .*\\d.*: Kiểm tra xem chuỗi có chứa ít nhất một số không.
     * .*[A-Z].*: Kiểm tra xem chuỗi có chứa ít nhất một chữ cái in hoa không.
     * .*[a-z].*: Kiểm tra xem chuỗi có chứa ít nhất một chữ cái thường không.
     * .*[!@#$%^&*()-_+=<>?].*: Kiểm tra xem chuỗi có chứa ít nhất một ký tự đặc biệt không.
     * */
    private boolean isValidUserName(String userName) {
        if (userName.length() < 8 || !userName.matches(".*[A-Z].*") || !userName.matches(".*\\d.*")){
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password) {
        if (password.contains(" ") || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[!@#$%^&*()-_+=<>?].*")){
            return false;
        }
        return true;
    }
    private boolean isConfirm(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    /*
     * [A-Za-z0-9._%+-]: chứa chữ cái, số, các ký tự như '.', '_',...
     * \.: ngăn cách tên miền
     * [A-Za-z]{2,6}$: kết thúc chuỗi với 1 tên miền có độ dài từ 2 đến 6 ký tự từ bảng chữ tiếng anh
     * */
    private boolean isValidEmail(String email){
        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regexEmail);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void SignUp() {
        userDAO = new UserDAO(RegisterActivity.this);
        String userName = editUserName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirm.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (userName.isEmpty()) {
            showToast("Phải nhập tên người dùng!");
            editUserName.requestFocus();
        }
        if (password.isEmpty()) {
            showToast("Phải nhập mật khẩu!");
            editPassword.requestFocus();
        }
        if (confirmPassword.isEmpty()) {
            showToast("Phải nhập lại mật khẩu!");
            editConfirm.requestFocus();
        }
        if(email.isEmpty()){
            showToast("Phải nhập email!");
            editEmail.requestFocus();
        }

        User rg = new User();
        if (!isValidUserName(userName)) {
            showToast("Tên người dùng không hợp lệ");
            editUserName.requestFocus();
        }
        else if(userDAO.checkExistUser(userName)){
            showToast("Tên người dùng đã tồn tại");
            editUserName.requestFocus();
        }
        else if (!isValidEmail(email)){
            showToast("Email không hợp lệ");
            editEmail.requestFocus();
        }
        else if (userDAO.checkExistEmail(email)) {
            showToast("Email đã tồn tại");
            editEmail.requestFocus();
        }
        else if (!isValidPassword(password)) {
            showToast("Mật khẩu không hợp lệ");
            editPassword.requestFocus();
        }
        else if (!isConfirm(password, confirmPassword)) {
            showToast("Mật khẩu không khớp");
            editConfirm.requestFocus();
        }
        else{
            rg.setUser_name(userName);
            rg.setUser_password(password);
            rg.setEmail(email);
            userDAO.insert(rg);
            showToast("Đăng ký thành công");
            finish();
        }
    }
    public void backToLogin(View v){
        finish();
    }
}