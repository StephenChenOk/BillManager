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
import android.widget.TextView;

import com.chen.fy.mytext.billsApp.LoginViewActivity;
import com.chen.fy.mytext.billsApp.ModifyInfo;
import com.chen.fy.mytext.billsApp.ModifyPassword;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;

public class InfoFragment extends Fragment {

    private String username = LoginViewActivity.username;   //获取当前用户名

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取各个信息控件
        TextView tv_username = view.findViewById(R.id.info_userName);
        TextView tv_sex = view.findViewById(R.id.sex);
        TextView tv_birthday = view.findViewById(R.id.birthday);
        TextView tv_phone = view.findViewById(R.id.phone);
        TextView tv_address = view.findViewById(R.id.address);

        Button modify_info_btn = view.findViewById(R.id.modify_info_btn);
        Button modify_password_btn = view.findViewById(R.id.modify_password_btn);


        //给控件赋值
        tv_username.setText(String.format("用户名: %s", username));
        tv_sex.setText(String.format("性别: %s", StartActivity.infodao.getSex(username)));
        tv_birthday.setText(String.format("生日: %s", StartActivity.infodao.getBirthday(username)));
        tv_phone.setText(String.format("电话号码: %s", StartActivity.infodao.getPhone(username)));
        tv_address.setText(String.format("地址: %s", StartActivity.infodao.getAddress(username)));

        //修改基本信息按钮事件
        modify_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ModifyInfo.class);
                startActivity(intent);
            }
        });

        //修改密码按钮事件
        modify_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ModifyPassword.class);
                startActivity(intent);
            }
        });
    }
}
