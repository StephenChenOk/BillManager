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

/**
 * 修改密码
 */

public class ModifyPassword extends AppCompatActivity {

    private Button finish_modify_password_btn;
    private EditText et_old_password;
    private EditText et_new_password1;
    private EditText et_new_password2;
    private String username = LoginViewActivity.username;   //获取当前用户名
    private String old_password;
    private String new_password1;
    private String new_password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);

        finish_modify_password_btn = findViewById(R.id.finish_modify_password_btn);
        et_old_password = findViewById(R.id.old_password);
        et_new_password1 = findViewById(R.id.new_password1);
        et_new_password2 = findViewById(R.id.new_password2);

        finish_modify_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取旧密码
                old_password = et_old_password.getText().toString();
                //获取两次新密码
                new_password1 = et_new_password1.getText().toString();
                new_password2 = et_new_password2.getText().toString();
                Log.d("chen",username +"======="+old_password);
                Log.d("chen",new_password1 +"======="+new_password2);
                if (StartActivity.infodao.queryLogin(username, old_password)) {
                    if(new_password1.equals(new_password2)) {
                        StartActivity.infodao.updatePassword(username,new_password1);
                        Intent intent = new Intent(ModifyPassword.this, LoginViewActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ModifyPassword.this,"两次密码不正确",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ModifyPassword.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
