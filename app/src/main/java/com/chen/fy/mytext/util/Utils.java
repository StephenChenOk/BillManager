package com.chen.fy.mytext.util;

import android.annotation.SuppressLint;

import com.chen.fy.mytext.billsApp.LoginViewActivity;
import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;
import com.chen.fy.mytext.entity.BillInfo;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 */
public class Utils {

    //时间格式转化1(转为 年-月 )
    public static String time_change1(long time) {
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(date);
    }

    //时间格式转化2(转为 日 小时:分钟 )
    public static String time_change2(long time) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd日 HH:mm");
        return simpleDateFormat.format(date);
    }

    //判断月份前是否要加0
    public static String month_change(int month) {
        if ((month + 1) <= 9) {
            return "0" + (month + 1);
        } else {
            return (month + 1) + "";
        }
    }

    //判断天数前是否要加0
    public static String day_change(int day) {
        if (day <= 9) {
            return "0" + day;
        } else {
            return day + "";
        }
    }

    //判断小时前是否要加0
    public static String hour_change(int hour) {
        if (hour <= 9) {
            return "0" + hour;
        } else {
            return hour + "";
        }
    }

    //判断分钟前是否要加0
    public static String minute_change(int minute) {
        if (minute <= 9) {
            return "0" + minute;
        } else {
            return minute + "";
        }
    }

    //判断消费方式,并选择相应的图片和计算相应消费方式的金额
    public static void type_select(BillInfo billInfo, String type, int number) {
        //收入
        if (type.equals("薪水")) {
            billInfo.setLogo(R.drawable.money);
            StartActivity.salary_table = StartActivity.salary_table + number;
        }
        if (type.equals("兼职")) {
            billInfo.setLogo(R.drawable.part_time_job);
            StartActivity.part_time_job_table = StartActivity.part_time_job_table + number;
        }
        if (type.equals("股票")) {
            billInfo.setLogo(R.drawable.shares);
            StartActivity.shares_table = StartActivity.shares_table + number;
        }
        if (type.equals("收红包")) {
            billInfo.setLogo(R.drawable.red_paper);
            StartActivity.income_red_paper_table = StartActivity.income_red_paper_table + number;
        }
        if (type.equals("其它收入")) {
            billInfo.setLogo(R.drawable.other);
            StartActivity.income_other_table = StartActivity.income_other_table + number;
        }
        if (type.equals("餐饮")) {
            billInfo.setLogo(R.drawable.food);
            StartActivity.food_table = StartActivity.food_table + number;
        }
        if (type.equals("购物")) {
            billInfo.setLogo(R.drawable.shopping);
            StartActivity.shopping_table = StartActivity.shopping_table + number;
        }
        if (type.equals("交通")) {
            billInfo.setLogo(R.drawable.traffic);
            StartActivity.traffic_table = StartActivity.traffic_table + number;
        }
        if (type.equals("日用")) {
            billInfo.setLogo(R.drawable.commodity);
            StartActivity.commodity_table = StartActivity.commodity_table + number;
        }
        if (type.equals("医疗")) {
            billInfo.setLogo(R.drawable.treatment);
            StartActivity.treatment_table = StartActivity.treatment_table + number;
        }
        if (type.equals("发红包")) {
            billInfo.setLogo(R.drawable.red_paper);
            StartActivity.pay_red_paper_table = StartActivity.pay_red_paper_table + number;
        }
        if (type.equals("其它支出")) {
            billInfo.setLogo(R.drawable.other);
            StartActivity.pay_other_table = StartActivity.pay_other_table + number;
        }
    }

    //判断当前选择月份所对应的天数
    public static int day_of_month(String date) {
        String str[] = date.split("-");
        int year = Integer.parseInt(str[0]);
        int month = Integer.parseInt(str[1]);
            switch (month){
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    return 31;
                case 4: case 6: case 9: case 11:
                    return 30;
                case 2:
                    if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){  //闰年
                        return 29;
                    }else {
                        return 28;
                    }
                default:return 0 ;
            }
    }

    //MD5加密密码
    public static String getMD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }

    //保留 位小数
    public static String retainDecimal(double x){
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(x);
    }

    //每次重新进入主界面都重新初始化数据集合并载入已有的数据源
    public static List<BillInfo> prepareData(List<BillInfo> list, String[] str){
        //当登入时清除list集合中所有的账单信息
        list.clear();
        //当登入时重新赋值
        StartActivity.total_number = "0";
        StartActivity.pay_number = "0";
        StartActivity.income_number = "0";
        //报表备用
        StartActivity.salary_table = 0;
        StartActivity.part_time_job_table = 0;
        StartActivity.shares_table = 0;
        StartActivity.income_red_paper_table = 0;
        StartActivity.income_other_table = 0;
        StartActivity.food_table = 0;
        StartActivity.shopping_table = 0;
        StartActivity.traffic_table = 0;
        StartActivity.commodity_table = 0;
        StartActivity.treatment_table = 0;
        StartActivity.pay_red_paper_table = 0;
        StartActivity.pay_other_table = 0;
        list = StartActivity.billdao.getOldBill(list, LoginViewActivity.username, str);      //获取数据库中的账单信息

        return list;
    }
}

