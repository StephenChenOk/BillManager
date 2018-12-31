package com.chen.fy.mytext.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chen.fy.mytext.billsApp.InputIncomeActivity;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;

public class IncomeFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //反射一个视图View,就像Activity中的setContentView方法
        return inflater.inflate(R.layout.fragment_income,container,false);
    }

    @Override
    //相当于Activity中的onCreate方法的重写
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_salary = view.findViewById(R.id.count_salary);
        Button btn_part_time_job = view.findViewById(R.id.count_part_time_job);
        Button btn_shares = view.findViewById(R.id.count_shares);
        Button btn_red_paper = view.findViewById(R.id.count_income_red_paper);
        Button btn_other = view.findViewById(R.id.count_income_other);

        btn_salary.setOnClickListener(this);
        btn_part_time_job.setOnClickListener(this);
        btn_shares.setOnClickListener(this);
        btn_red_paper.setOnClickListener(this);
        btn_other.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.count_salary:
                intent= new Intent(getActivity(),InputIncomeActivity.class);
                intent.putExtra("type","薪水");
                StartActivity.pay_or_income = true;   //记住当前界面是收入还是支出界面
                startActivity(intent);
                break;
            case R.id.count_part_time_job:
                intent = new Intent(getActivity(),InputIncomeActivity.class);
                intent.putExtra("type","兼职");
                StartActivity.pay_or_income = true;
                startActivity(intent);
                break;
            case R.id.count_shares:
                intent = new Intent(getActivity(),InputIncomeActivity.class);
                intent.putExtra("type","股票");
                StartActivity.pay_or_income = true;
                startActivity(intent);
                break;
            case R.id.count_income_red_paper:
                intent = new Intent(getActivity(),InputIncomeActivity.class);
                intent.putExtra("type","收红包");
                StartActivity.pay_or_income = true;
                startActivity(intent);
                break;
            case R.id.count_income_other:
                intent = new Intent(getActivity(),InputIncomeActivity.class);
                intent.putExtra("type","其它收入");
                StartActivity.pay_or_income = true;
                startActivity(intent);
                break;
        }
    }
}
