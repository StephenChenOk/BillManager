package com.chen.fy.mytext.billsApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chen.fy.mytext.util.Utils;

import java.util.UUID;


public class RegisterActivity extends AppCompatActivity {

    private Button register_btn;
    private EditText register_userName;
    private EditText passWord1;
    private EditText passWord2;

    private String username;
    private String password1;
    private String password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        register_btn = findViewById(R.id.register_btn);
        register_userName = findViewById(R.id.register_userName);
        passWord1 = findViewById(R.id.passWord1);
        passWord2 = findViewById(R.id.passWord2);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取注册账号时输入的用户名和密码
                username = register_userName.getText().toString();
                password1 = passWord1.getText().toString();
                password2 = passWord2.getText().toString();
//                if (checkUsername(register_userName.getText().toString())) {
//                    username = register_userName.getText().toString();
//                    Log.d("chen","username-----> "+username);
//                }
//                if (checkPassword(passWord1.getText().toString())) {
//                    password1 = passWord1.getText().toString();
//                    Log.d("chen","password1-----> "+password1);
//                }
//                if (checkPassword(passWord2.getText().toString())) {
//                    password2 = passWord2.getText().toString();
//                    Log.d("chen","password2-----> "+password2);
//                }
                //获取系统随机生成的值(盐值),用于加密
                String pwSalt = UUID.randomUUID().toString().substring(0, 5);

                if (username != null && password1 != null && password2 != null) {
                    //判断所注册的用户名是否已经存在
                    if (!StartActivity.infodao.queryRegister(username)) {
                        //判断所输入的两次密码是否一致
                        if (password1.equals(password2)) {
                            //用输入的密码+盐值进行MD5加密
                            String pwHash = Utils.getMD5(password1 + pwSalt);
                            //把注册成功的加密过的密码和所对应的唯一盐值一起放到数据库中
                            StartActivity.infodao.insert(username, pwHash, pwSalt, null, null, null, null);
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            //并跳转到登入界面
                            Intent intent = new Intent(RegisterActivity.this, LoginViewActivity.class);
                            startActivityForResult(intent, 0);
                        } else {
                            Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //判断账号是否以小写字母开头,且不少于8位
    private boolean checkUsername(String username) {
        Log.d("chen","-----> "+username.charAt(0));
        if (username.charAt(0) >= 'a' && username.charAt(0) <= 'z' && username.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    //判断密码是否不少于8位
    private boolean checkPassword(String password) {
        if (password.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }
}
