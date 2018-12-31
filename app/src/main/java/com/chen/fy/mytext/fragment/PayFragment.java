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

import com.chen.fy.mytext.billsApp.InputPayActivity;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;

public class PayFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override  //反射一个视图View,就像Activity中的setContentView方法
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pay, container, false);
    }

    @Override
    //相当于Activity中的onCreate方法的重写
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_food = view.findViewById(R.id.count_food);
        Button btn_shopping = view.findViewById(R.id.count_shopping);
        Button btn_traffic = view.findViewById(R.id.count_traffic);
        Button btn_commodity = view.findViewById(R.id.count_commodity);
        Button btn_treatment = view.findViewById(R.id.count_treatment);
        Button btn_red_paper = view.findViewById(R.id.count_pay_red_paper);
        Button btn_other = view.findViewById(R.id.count_pay_other);

        btn_food.setOnClickListener(this);
        btn_shopping.setOnClickListener(this);
        btn_traffic.setOnClickListener(this);
        btn_commodity.setOnClickListener(this);
        btn_treatment.setOnClickListener(this);
        btn_red_paper.setOnClickListener(this);
        btn_other.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.count_food:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "餐饮");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
            case R.id.count_shopping:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "购物");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
            case R.id.count_traffic:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "交通");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
            case R.id.count_commodity:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "日用");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
            case R.id.count_treatment:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "医疗");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
            case R.id.count_pay_red_paper:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "发红包");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
            case R.id.count_pay_other:
                intent = new Intent(getActivity(), InputPayActivity.class);
                intent.putExtra("type", "其它支出");
                StartActivity.pay_or_income = false;
                startActivity(intent);
                break;
        }
    }
}
