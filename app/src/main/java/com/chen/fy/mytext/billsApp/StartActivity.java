package com.chen.fy.mytext.billsApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chen.fy.mytext.database.BillDao;
import com.chen.fy.mytext.database.InfoDao;

/**
 * 打开app首先显示的界面
 */

public class StartActivity extends AppCompatActivity {

    private Button main_login_btn;
    private Button main_register_btn;

    public static InfoDao infodao;  //方便调用
    public static BillDao billdao;  //方便调用

    public static boolean pay_or_income = true;
    public static String income_number = "0";
    public static String pay_number = "0";
    public static String total_number = "0";


    //报表备用
    public static int salary_table = 0;
    public static int part_time_job_table = 0;
    public static int shares_table = 0;
    public static int income_red_paper_table = 0;
    public static int income_other_table = 0;
    public static int food_table = 0;
    public static int shopping_table = 0;
    public static int traffic_table = 0;
    public static int commodity_table = 0;
    public static int treatment_table = 0;
    public static int pay_red_paper_table = 0;
    public static int pay_other_table = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_view);

        //创建数据库
        infodao = new InfoDao(this);
        billdao = new BillDao(this);

        main_login_btn = findViewById(R.id.main_login_btn);
        main_register_btn = findViewById(R.id.main_register_btn);

        main_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到登入账号界面
                Intent intent = new Intent(StartActivity.this, LoginViewActivity.class);
                startActivity(intent);
            }
        });

        main_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册账号界面
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
