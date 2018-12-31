package com.chen.fy.mytext.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chen.fy.mytext.billsApp.MainActivity;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;

public class CountFragment extends Fragment implements View.OnClickListener{

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private IncomeFragment incomeFragment;
    private PayFragment payFragment;

    private MainActivity mainActivityTest;

    public static CountFragment newInstance(){
        CountFragment countFragment = new CountFragment();
        return countFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //找到控件对象
        radioGroup = view.findViewById(R.id.radio_group_count);

        //实例化Fragment
        incomeFragment = new IncomeFragment();
        payFragment = new PayFragment();

        if (getActivity() != null) {
            mainActivityTest = (MainActivity) getActivity();
        }

        //把Fragment添加到Activity中   首先显示收入界面
        mainActivityTest.FirstCountFragment(incomeFragment,payFragment);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = group.findViewById(checkedId);
                radioButton.setOnClickListener(CountFragment.this);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count_income_btn:
                StartActivity.pay_or_income=true;
                mainActivityTest.CountFragmentController(StartActivity.pay_or_income,incomeFragment,payFragment);
                break;
            case R.id.count_pay_btn:
                StartActivity.pay_or_income=false;
                mainActivityTest.CountFragmentController(StartActivity.pay_or_income,incomeFragment,payFragment);
                break;
        }
    }
}
