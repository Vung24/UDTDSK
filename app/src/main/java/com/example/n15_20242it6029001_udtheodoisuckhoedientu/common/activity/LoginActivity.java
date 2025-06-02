package com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.objects.UserSession;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity.CreateProfileActivity;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.ProfileDAO;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtUserName, edtPassword;
    UserDAO userDAO;
    boolean isPasswordVisible = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_activity_login);
        getWidget();
        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[2].getBounds().width())){
                        isPasswordVisible = !isPasswordVisible;
                        if (isPasswordVisible) {
                            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0); // Icon mắt mở
                        } else {
                            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0); // Icon mắt đóng
                        }

                        // Đưa con trỏ văn bản về cuối
                        edtPassword.setSelection(edtPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }
    public void getWidget(){
        btnLogin = findViewById(R.id.btnLogin);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);
        userDAO = new UserDAO(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login(){
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this,"Phải nhập tên người dùng và mật khẩu.",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean checkUser = userDAO.checkUser(userName,password);

        if (checkUser){
            Toast.makeText(LoginActivity.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
            int user_id = userDAO.getUserId(userName, password);
            if(user_id==0){
                Toast.makeText(LoginActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
            else{
                UserSession userSession = UserSession.getInstance();
                userSession.setUserId(user_id);
                ProfileDAO profileDAO = new ProfileDAO(this);
                if(profileDAO.checkProfile(user_id)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, CreateProfileActivity.class);
                    startActivity(intent);
                }
                finish();
            }

        }else {
            Toast.makeText(LoginActivity.this,"Tên đăng nhập và mật khẩu không tồn tại.",Toast.LENGTH_SHORT).show();
        }

    }
    public void toRegister(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}
