package com.chen.fy.mytext.entity;
/**
 * 账单明细界面的实体类,也叫bean类
 */

import android.graphics.drawable.Drawable;

public class BillInfo {

    //每一个账单信息的唯一标志
    private int id;
    //图标
    private int logo;
    //时间
    private String utility_time;   //获取 日:小时:分钟
    //方式(支出后者收入)
    private String way;
    //类型
    private String type;
    //数目
    private String number;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getUtility_time() {
        return utility_time;
    }

    public void setUtility_time(String utility_time) {
        this.utility_time = utility_time;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
