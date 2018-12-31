package com.chen.fy.mytext.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytext.adapter.MyAdapter;
import com.chen.fy.mytext.billsApp.MainActivity;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;
import com.chen.fy.mytext.entity.BillInfo;
import com.chen.fy.mytext.util.MyPickerDialog;
import com.chen.fy.mytext.util.Utils;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;


public class DetailFragmentTest extends Fragment implements AdapterView.OnItemClickListener {

    private Button detail_btn_date;

    private String[] str = MainActivity.date;
    //用于之后统计每个月平均每天的收入或者支出数目

    public void setStr(String[] date){
        this.str = date;
    }

    private List<BillInfo> list;

    private TextView tv_income;
    private TextView tv_pay;
    private TextView tv_total;
    private ListView listView;
    private MyAdapter myAdapter;


    public static DetailFragmentTest newInstance(String date) {
        DetailFragmentTest detailFragmentTest = new DetailFragmentTest();
        Bundle args = new Bundle();
        args.putString("date", date);
        detailFragmentTest.setArguments(args);
        return detailFragmentTest;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //找到控件对象
        detail_btn_date = view.findViewById(R.id.btn_date_test);
        tv_income = view.findViewById(R.id.tv_income);
        tv_pay = view.findViewById(R.id.tv_pay);
        tv_total = view.findViewById(R.id.tv_total);

        //拿到当前activity对象
        if (getActivity() != null) {
            MainActivity mainActivityTest = (MainActivity) getActivity();
            list = mainActivityTest.getList();
        }
        //拿到ListView对象
        listView = view.findViewById(R.id.list_view);
        //适配器
        myAdapter = new MyAdapter(getContext());
        //准备数据源
        //list.clear();
        if (list.isEmpty()) {    //第一次加载这个界面时
            Log.d("chen",str[0]);
            list = Utils.prepareData(list, str);
           // myAdapter.notifyDataSetChanged();
        }
        if (getArguments() != null) {   //更换显示的日期账单时
            if (getArguments().getString("date") != null) {
                str[0] = getArguments().getString("date");   //寻找所对应的年月的账单信息
            }
            list = Utils.prepareData(list, str);
            //myAdapter.notifyDataSetChanged();
        }
        //把数据源放入适配器中
        myAdapter.setList(list);
        //关联适配器
        listView.setAdapter(myAdapter);
        //给listView设置点击事件
        listView.setOnItemClickListener(this);

//        //给顶部赋值
        detail_btn_date.setText(str[0]);
        tv_income.setText(String.format("%s元", StartActivity.income_number));
        tv_pay.setText(String.format("%s元", StartActivity.pay_number));
        tv_total.setText(String.format("%s元", StartActivity.total_number));

        detail_btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (getContext() != null) {
                    //创建一个DatePickerDialog对象
                    new MyPickerDialog(getContext(), DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String Month = Utils.month_change(month);
                            MainActivity.date[0] = year + "-" + Month;
                            detail_btn_date.setText(MainActivity.date[0]);
                            //按下选择年月的按钮则会重新载入相应年月的账单数据
                            str[0] = MainActivity.date[0];
                            MainActivity.fragmentsUpdateFlag[1]=true;
                           // init();
                           // myAdapter.notifyDataSetChanged();
//                            if (getActivity() != null) {
//                                MainActivity mainActivityTest = (MainActivity) getActivity();
//                                mainActivityTest.setChantEssayFragment(MainActivity.date[0]);
//                            }
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

    public void init(){
        //准备数据源
        list.clear();
        if (list.isEmpty()) {    //第一次加载这个界面时
            Log.d("chen","init!!");
            list = Utils.prepareData(list, str);
            //myAdapter.notifyDataSetChanged();
        }
//        if (getArguments() != null) {   //更换显示的日期账单时
//            if (getArguments().getString("date") != null) {
//                str[0] = getArguments().getString("date");   //寻找所对应的年月的账单信息
//            }
//            list = Utils.prepareData(list, str);
//           // myAdapter.notifyDataSetChanged();
//        }
        //把数据源放入适配器中
        myAdapter.setList(list);
        //关联适配器
        listView.setAdapter(myAdapter);
        //给listView设置点击事件
        listView.setOnItemClickListener(this);

        //给顶部赋值
        detail_btn_date.setText(str[0]);
        tv_income.setText(String.format("%s元", StartActivity.income_number));
        tv_pay.setText(String.format("%s元", StartActivity.pay_number));
        tv_total.setText(String.format("%s元", StartActivity.total_number));

    }

    /**
     * 点击后弹出可以删除当前行的账单信息的对话框
     * 参数: 1是哪个listView 2是当前listView的item的view的布局，就是可以用这个view，获取里面的控件的id后操作控件
     * 3是第几个item, 4 在listView中是第几行
     */
    @Override
    //点击listView
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //获取当前所点击行的对象
        final BillInfo billInfo = (BillInfo) adapterView.getItemAtPosition(i);
        //创建一个builder对象(连点方法 当所有方法的返回值都一样时可以用,也可以直接创建一个对象
        new AlertDialog.Builder(getContext())
                //设计标题
                .setTitle("你确定删除么")
                //设计按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在数据库中删除当前行的信息
                        StartActivity.billdao.delete(billInfo.getId());
                        list.clear();  //清空数据库,以便重新载入数据
                        if (getActivity() != null) {
                            Toast.makeText(getContext(), "删除成功!", Toast.LENGTH_SHORT).show();
                            MainActivity mainActivityTest = (MainActivity) getActivity();
                            mainActivityTest.setChantEssayFragment(MainActivity.date[0]);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

}
