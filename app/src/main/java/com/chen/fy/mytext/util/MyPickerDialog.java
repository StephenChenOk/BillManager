package com.chen.fy.mytext.util;
/**
 * 日期选择器,只显示年月,除去日
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

public class MyPickerDialog extends DatePickerDialog {

    public MyPickerDialog(@NonNull Context context, @StyleRes int themeResId, @Nullable OnDateSetListener listener,
                          int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
        this.setTitle(year + "年" + (monthOfYear + 1) + "月");
    }

    @Override
    public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);
        this.setTitle(year + "年" + (month + 1) + "月");
    }

    @Override
    public void show() {
        super.show();
        DatePicker dp = this.getDatePicker();
        NumberPicker view0 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(0); //获取最前一位的宽度
        NumberPicker view1 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(1); //获取中间一位的宽度
        NumberPicker view2 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2);//获取最后一位的宽度


        //年的最大值为2100
        //月的最大值为11
        //日的最大值为28,29,30,31
        int value0 = view0.getMaxValue();//获取第一个View的最大值
        int value1 = view1.getMaxValue();//获取第二个View的最大值
        int value2 = view2.getMaxValue();//获取第三个View的最大值
        if (value0 >= 28 && value0 <= 31) {
            view0.setVisibility(View.GONE);
        } else if (value1 >= 28 && value1 <= 31) {
            view1.setVisibility(View.GONE);
        } else if (value2 >= 28 && value2 <= 31) {
            view2.setVisibility(View.GONE);
        }
    }
}
