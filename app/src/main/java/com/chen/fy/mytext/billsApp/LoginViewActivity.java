package com.chen.fy.mytext.billsApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginViewActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText userName;
    private EditText passWord;
    private String password;
    public static String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        login_btn = findViewById(R.id.login_btn);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = userName.getText().toString();
                password = passWord.getText().toString();
                if (StartActivity.infodao.queryLogin(username, password)) {
                    Toast.makeText(LoginViewActivity.this,"登入成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginViewActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginViewActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
