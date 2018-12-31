package com.chen.fy.mytext.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chen.fy.mytext.myConst.Const;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, Const.DATABASE_NAME, null, Const.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建用户信息数据表
        sqLiteDatabase.execSQL("create table " + Const.TABLE_NAME_LOGIN +
                "(userName varchar not null,pwHash varchar not null,pwSalt varchar not null," +
                "sex varchar,birthday varchar,phone integer,address varchar)");

        //创建账单信息数据表
        sqLiteDatabase.execSQL("create table " + Const.TABLE_NAME_BILLS +
                "(id integer primary key autoincrement,userName varchar not null," +
                "date varchar not null,time varchar not null" +
                ",ways varchar not null,type varchar not null,number varchar not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
