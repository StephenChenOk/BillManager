package com.chen.fy.mytext.billsApp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chen.fy.mytext.util.Utils;

import java.util.Calendar;

public class InputPayActivity extends AppCompatActivity {

    private Button date_incomeBtn;
    private Button time_incomeBtn;
    private Button input_count_btn;
    private TextView tv_income_type;
    private EditText ed_input_count;
    private String type;     //支出或者收入的类型
    private String number = null;   //所支出或者收入的数目
    private String mydate = null;
    private String mytime = null;
    private String myday = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_count);

        //获取控件对象
        date_incomeBtn = findViewById(R.id.input_date_btn);
        time_incomeBtn = findViewById(R.id.input_time_btn);
        input_count_btn = findViewById(R.id.input_count_btn);
        tv_income_type = findViewById(R.id.text_income_type);
        ed_input_count = findViewById(R.id.input_count);

        //写入支出或者收入类型
        type = getIntent().getStringExtra("type");
        tv_income_type.setText(type);

        //点击确认按钮后跳转菜单明细界面
        input_count_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取写入的数目
                number = ed_input_count.getText().toString();
                Intent intent = new Intent(InputPayActivity.this, MainActivity.class);
                intent.putExtra("number", number);    //把输入的数目传到菜单明细界面
                intent.putExtra("ways", "支出");
                intent.putExtra("type", type);         //把支出的类型也传出去
                intent.putExtra("date", mydate);
                intent.putExtra("time", myday + "日 " + mytime);
                if (number.isEmpty() || mydate.isEmpty() || mytime.isEmpty()) {
                    Toast.makeText(InputPayActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    //写一个与控件按钮中onClick属性同名的方法
    public void date_btnClick(View v) { //View参数表示按钮对象

        Calendar calendar = Calendar.getInstance();

        //创建一个DatePickerDialog对象
        new DatePickerDialog(this, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //给主界面做准备
                myday = Utils.day_change(day) + "";
                mydate = year + "-" + Utils.month_change(month);
                //日期
                String date = year + "-" + Utils.month_change(month) + "-" + Utils.day_change(day);
                date_incomeBtn.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //写一个与控件按钮中onClick属性同名的方法
    public void time_btnClick(View v) { //View参数表示按钮对象

        Calendar calendar = Calendar.getInstance();

        //创建一个TimePickerDialog对象
        new TimePickerDialog(this,TimePickerDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                //给主界面做准备
                mytime = Utils.hour_change(hour) + ":" + Utils.minute_change(minute);
                //时间
                time_incomeBtn.setText(mytime);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

    }
}
