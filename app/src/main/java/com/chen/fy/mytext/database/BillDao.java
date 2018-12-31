package com.chen.fy.mytext.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chen.fy.mytext.billsApp.StartActivity;
import com.chen.fy.mytext.myConst.Const;
import com.chen.fy.mytext.entity.BillInfo;
import com.chen.fy.mytext.util.Utils;

import java.util.List;

/**
 * 实现对账单信息数据表的增删查改
 */
public class BillDao {

    private DataBase dataBase;

    public BillDao(Context context) {
        dataBase = new DataBase(context);       //创建数据库
    }

    //增( 增加一条账单信息 )
    public void insert(String username, String date, String time, String way, String type, String number) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", username);
        values.put("date", date);
        values.put("time",time);
        values.put("ways", way);
        values.put("type", type);
        values.put("number", number);
        db.insert(Const.TABLE_NAME_BILLS, null, values);
        db.close();
    }

    //删
    public void delete(int id) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.delete(Const.TABLE_NAME_BILLS, "id = "+id, null);
        db.close();
    }

    //查( 获取之前的账单信息 )
    public List<BillInfo> getOldBill(List<BillInfo> list, String username, String[] str) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_BILLS, null, "date = ?",
                str, null, null, "time desc"); //按降序排列
        while (cursor.moveToNext()) {
            //获取当前行的用户名
            int userName_index = cursor.getColumnIndex("userName");
            String userName = cursor.getString(userName_index);
            //判断当前行的用户名与所登入的用户名是否相同
            if (userName.equals(username)) {
                BillInfo billInfo = new BillInfo();
                //获取各个信息
                int id_index = cursor.getColumnIndex("id");
                int date_index = cursor.getColumnIndex("date");
                int time_index = cursor.getColumnIndex("time");
                int way_index = cursor.getColumnIndex("ways");
                int type_index = cursor.getColumnIndex("type");
                int number_index = cursor.getColumnIndex("number");
                int id = cursor.getInt(id_index);
                String date = cursor.getString(date_index);    //获取 年-月
                String time = cursor.getString(time_index);   //获取 日:小时:分钟
                String way = cursor.getString(way_index);
                String type = cursor.getString(type_index);
                String number = cursor.getString(number_index);
                //把信息填入billInfo中
                billInfo.setId(id);
                billInfo.setUtility_time(time);
                billInfo.setWay(way);
                billInfo.setType(type);
                if (way.equals("收入")) {
//                    //计算旧账单的总收入
                    StartActivity.income_number = (Integer.parseInt(StartActivity.income_number)
                            + Integer.parseInt(number)) + "";
                    StartActivity.total_number = (Integer.parseInt(StartActivity.total_number)
                            + Integer.parseInt(number)) + "";
                    //billInfo.setNumber(number);
                    billInfo.setNumber("+ " + number);
                }
                if (way.equals("支出")) {
                    //计算旧账单的总支出
                    StartActivity.pay_number = (Integer.parseInt(StartActivity.pay_number)
                            - Integer.parseInt(number)) + "";
                    StartActivity.total_number = (Integer.parseInt(StartActivity.total_number)
                            - Integer.parseInt(number)) + "";
                    //billInfo.setNumber(number);
                    billInfo.setNumber("- " + number);
                }
                //判断消费方式,并选择相应的图片和计算相应消费方式的金额
                Utils.type_select(billInfo, type,Integer.parseInt(number));
                //再放入传入的list集合中
                list.add(billInfo);
            }
        }
        cursor.close();
        db.close();
        return list;
    }
}
