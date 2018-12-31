package com.chen.fy.mytext.billsApp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytext.util.Utils;

import java.util.Calendar;


public class ModifyInfo extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button birthday_btn;
    private TextView phone_et;
    private TextView address_et;
    private Button finish_modify_info_btn;

    private String username = LoginViewActivity.username;   //获取当前用户名
    private String sex = StartActivity.infodao.getSex(username);
    private String birthday = StartActivity.infodao.getBirthday(username);
    private String phone = StartActivity.infodao.getPhone(username);
    private String address = StartActivity.infodao.getAddress(username);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_info);

        //拿到对象
        radioGroup = findViewById(R.id.radio_group_info);
        birthday_btn = findViewById(R.id.modify_birthday);
        phone_et = findViewById(R.id.modify_phone);
        address_et = findViewById(R.id.modify_address);
        finish_modify_info_btn = findViewById(R.id.finish_modify_info_btn);

        //赋值
        birthday_btn.setText(birthday);
        phone_et.setText(phone);
        address_et.setText(address);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = group.findViewById(checkedId);
            }
        });

        //设置性别当前的选中项
        if(sex.equals("男")){
            radioGroup.check(R.id.btn_sex_nan);
        }
        if(sex.equals("女")){
            radioGroup.check(R.id.btn_sex_nv);
        }

        finish_modify_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //若编辑为空的,则保持原来的状态
                if (radioButton != null) {
                    sex = radioButton.getText().toString().isEmpty() ? sex : radioButton.getText().toString();
                }
                birthday = birthday_btn.getText().toString().isEmpty() ? birthday : birthday_btn.getText().toString();
                phone = phone_et.getText().toString().isEmpty() ? phone : phone_et.getText().toString();
                address = address_et.getText().toString().isEmpty() ? address : address_et.getText().toString();

                //把输入的基本信息放入到数据库中
                StartActivity.infodao.updateInfo(LoginViewActivity.username, sex, birthday, phone, address);

                Toast.makeText(ModifyInfo.this, "编辑信息完成", Toast.LENGTH_SHORT).show();

                //编辑完成后跳转到用户信息界面
                Intent intent = new Intent(ModifyInfo.this, MainActivity.class);
                intent.putExtra("info","infoFragment");
                startActivity(intent);
            }
        });
    }

    //写一个与控件按钮中onClick属性同名的方法
    public void birthdayDate_btn(View v) { //View参数表示按钮对象
        Calendar calendar = Calendar.getInstance();
        //创建一个DatePickerDialog对象
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = year + "-" + Utils.month_change(month) + "-" + Utils.day_change(day);
                birthday_btn.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
