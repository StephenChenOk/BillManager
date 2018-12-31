package com.chen.fy.mytext.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chen.fy.mytext.billsApp.MainActivity;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;
import com.chen.fy.mytext.entity.BillInfo;
import com.chen.fy.mytext.util.MyPickerDialog;
import com.chen.fy.mytext.util.Utils;

import java.util.Calendar;
import java.util.List;

public class TableFragment extends Fragment implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton table_PayOrIncome_btn;
    private Button pie_chart_btn;
    private Button bar_chart_btn;
    private Button table_date_btn;

    private List<BillInfo> list;
    private String[] str = new String[1];

    private TextView tv_way;
    private TextView tv_average;

    private PieChartFragment pieChartFragment;
    private BarChartFragment barChartFragment;

    public static String wayTable = "收入";   //消费方式
    public static String typeTable = "饼图";  //报表类型

    private double average;  //用于计算每个月平均每天的收入或者支出

    private MainActivity mainActivity;

    public static TableFragment newInstance(String date) {
        TableFragment tableFragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString("date", date);
        return tableFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = view.findViewById(R.id.radio_group_table_test);
        pie_chart_btn = view.findViewById(R.id.pie_chart_btn_test);
        bar_chart_btn = view.findViewById(R.id.bar_chart_btn_test);
        table_date_btn = view.findViewById(R.id.table_date_btn_test);
        tv_way = view.findViewById(R.id.tv_way);
        tv_average = view.findViewById(R.id.tv_average);

        if (getActivity() != null) {
            mainActivity = (MainActivity) getActivity();
        }

        //填入当前选择的月份
        table_date_btn.setText(Utils.time_change1(System.currentTimeMillis()));
        if (getArguments() != null) {
            table_date_btn.setText(getArguments().getString("date"));
        }

        //实例化Fragment
        pieChartFragment = new PieChartFragment();
        barChartFragment = new BarChartFragment();

        //首次进入时显示的碎片
        mainActivity.FirstTableFragment(pieChartFragment);

        //报表视图上显示当前月份的总收入或者总支出和平均每天的收入或者支出数目
        showNumber();

        //点击收入或者支出事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                table_PayOrIncome_btn = group.findViewById(checkedId);
                table_PayOrIncome_btn.setOnClickListener(TableFragment.this);
            }
        });

        //点击饼图或者柱图事件
        pie_chart_btn.setOnClickListener(this);
        bar_chart_btn.setOnClickListener(this);
        //点击日期事件
        table_date_btn.setOnClickListener(this);
    }

    //报表视图上显示当前月份的总收入或者总支出和平均每天的收入或者支出数目
    public void showNumber(){
        if (wayTable.equals("收入")) {
            tv_way.setText(String.format("总收入: %s元", StartActivity.income_number));
            average = ((1.0) * Integer.parseInt(StartActivity.income_number)) /
                    Utils.day_of_month(MainActivity.date[0]);
            tv_average.setText(String.format("平均每天收入: %s元", Utils.retainDecimal(average)));//转换为保留一位小数
        }
        if (wayTable.equals("支出")) {
            tv_way.setText(String.format("总支出: %s元", StartActivity.pay_number));
            average = ((1.0) * Integer.parseInt(StartActivity.pay_number)) /
                    Utils.day_of_month(MainActivity.date[0]);
            tv_average.setText(String.format("平均每天支出: %s元", Utils.retainDecimal(average)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击日期按钮
            case R.id.table_date_btn_test: {
                Calendar calendar = Calendar.getInstance();
                if (getContext() != null) {
                    //创建一个DatePickerDialog对象
                    new MyPickerDialog(getContext(), DatePickerDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String Month = Utils.month_change(month);
                            MainActivity.date[0] = year + "-" + Month;
                            str[0] = year + "-" + Month;
                            table_date_btn.setText(MainActivity.date[0]);
                            //按下选择年月的按钮则会重新载入相应年月的账单数据,再显示出对应的报表
                            if (getActivity() != null) {
                                MainActivity mainActivity = (MainActivity) getActivity();
                                list = mainActivity.getList();
                                Utils.prepareData(list, str);
                                showNumber();
                                mainActivity.TableFragmentController(typeTable, pieChartFragment, barChartFragment);
                            }
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
            break;
            //收入
            case R.id.table_income_btn_test: {
                //消费方式改变,重新加载一个fragment
                wayTable = "收入";
                mainActivity.TableFragmentController(typeTable, pieChartFragment, barChartFragment);
                //填入总收入
                tv_way.setText(String.format("总收入: %s元", StartActivity.income_number));
                average = ((1.0) * Integer.parseInt(StartActivity.income_number)) /
                        Utils.day_of_month(MainActivity.date[0]);
                tv_average.setText(String.format("平均每天收入: %s元", Utils.retainDecimal(average)));//转换为保留一位小数
            }
            break;
            //支出
            case R.id.table_pay_btn_test: {
                //消费方式改变,重新加载一个fragment
                wayTable = "支出";
                mainActivity.TableFragmentController(typeTable, pieChartFragment, barChartFragment);
                //填入总支出
                tv_way.setText(String.format("总支出: %s元", StartActivity.pay_number));
                average = ((1.0) * Integer.parseInt(StartActivity.pay_number)) /
                        Utils.day_of_month(MainActivity.date[0]);
                tv_average.setText(String.format("平均每天支出: %s元", Utils.retainDecimal(average)));
            }
            break;
            //饼图
            case R.id.pie_chart_btn_test: {
                //显示的图形类型改变
                typeTable = "饼图";
                mainActivity.TableFragmentController(typeTable, pieChartFragment, barChartFragment);
            }
            break;
            //柱图
            case R.id.bar_chart_btn_test: {
                //显示的图形类型改变
                typeTable = "柱图";
                mainActivity.TableFragmentController(typeTable, pieChartFragment, barChartFragment);
            }
        }
    }
}
