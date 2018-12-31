package com.chen.fy.mytext.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chen.fy.mytext.myConst.Const;
import com.chen.fy.mytext.util.Utils;

import java.util.UUID;

/**
 * 实现对用户信息数据表的增删查改
 */
public class InfoDao {

    private DataBase dataBase;

    public InfoDao(Context context) {
        dataBase = new DataBase(context);       //创建数据库
    }

    //增( 用户信息 )
    public void insert(String username, String pwHash, String pwSalt,
                       String sex, String birthday, String phone, String address) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();  //相当于一个HashMap集合
        values.put("userName", username);
        values.put("pwHash", pwHash);
        values.put("pwSalt", pwSalt);
        values.put("sex", sex);
        values.put("birthday", birthday);
        values.put("phone", phone);
        values.put("address", address);
        db.insert(Const.TABLE_NAME_LOGIN, null, values);
        db.close();
    }

    //增( 账单信息 )
    public void insert(String currentTime, String type, String number) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", currentTime);
        values.put("type", type);
        values.put("number", number);
        db.insert(Const.TABLE_NAME_BILLS, null, values);
        db.close();
    }

    //删
    public void delete(String deleteUserName) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.delete(Const.TABLE_NAME_LOGIN, "userName = " + deleteUserName, null);
        db.close();
    }

    //查1(判断账号密码是否一致
    public boolean queryLogin(String user, String passWord) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_LOGIN, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {   //判断下一行是否有数据
            //获取当前行的用户名
            int index_username = cursor.getColumnIndex("userName");
            String userName = cursor.getString(index_username);
            //获取当前行的加密过的hash码
            int index_pwHash = cursor.getColumnIndex("pwHash");
            String pwHash = cursor.getString(index_pwHash);
            //获取当前的对应盐值
            int index_pwSalt = cursor.getColumnIndex("pwSalt");
            String pwSalt = cursor.getString(index_pwSalt);
            //加密当前所登陆入的密码,判断与原密码的加密结果是否一致,同时判断用户名是否一致
            if (user.equals(userName) && pwHash.equals(Utils.getMD5(passWord + pwSalt))) {   //判断用户填写的用户名和密码是否正确
                cursor.close();
                db.close();
                return true;   //一致
            }
        }
        cursor.close();
        db.close();
        return false;   //不一致
    }

    //查2(判断注册时账号是否已经存在
    public boolean queryRegister(String username) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_LOGIN, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("userName");
            String userName = cursor.getString(index);
            if (userName.equals(username)) {
                cursor.close();
                db.close();
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }

    //查3(获取当前行性别
    public String getSex(String username) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_LOGIN, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            int index_username = cursor.getColumnIndex("userName");
            if (username.equals(cursor.getString(index_username))) {
                int index = cursor.getColumnIndex("sex");
                String sex = cursor.getString(index);
                cursor.close();
                db.close();
                return sex;
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    //查4(获取当前行生日
    public String getBirthday(String username) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_LOGIN, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            int index_username = cursor.getColumnIndex("userName");
            if (username.equals(cursor.getString(index_username))) {
                int index = cursor.getColumnIndex("birthday");
                String birthday = cursor.getString(index);
                cursor.close();
                db.close();
                return birthday;
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    //查5(获取当前行电话号码
    public String getPhone(String username) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_LOGIN, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            int index_username = cursor.getColumnIndex("userName");
            if (username.equals(cursor.getString(index_username))) {
                int index = cursor.getColumnIndex("phone");
                String phone = cursor.getString(index);
                cursor.close();
                db.close();
                return phone;
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    //查6(获取当前行住址
    public String getAddress(String username) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME_LOGIN, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            int index_username = cursor.getColumnIndex("userName");
            if (username.equals(cursor.getString(index_username))) {
                int index = cursor.getColumnIndex("address");
                String address = cursor.getString(index);
                cursor.close();
                db.close();
                return address;
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    //改(改密码
    public void updatePassword(String newUsername, String newPassword) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        //重新获取系统随机生成的值(盐值),用于加密
        String pwSalt = UUID.randomUUID().toString().substring(0, 5);
        //用输入的新密码+新盐值进行MD5加密
        String pwHash = Utils.getMD5(newPassword + pwSalt);
        ContentValues values = new ContentValues();
        values.put("pwHash", pwHash);
        values.put("pwSalt", pwSalt);
        db.update(Const.TABLE_NAME_LOGIN, values, "userName = ?",  new String[]{newUsername});
        db.close();
    }

    //改基本信息
    public void updateInfo(String username, String sex, String birthday, String phone, String address) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sex", sex);
        values.put("birthday", birthday);
        values.put("phone", phone);
        values.put("address", address);
        db.update(Const.TABLE_NAME_LOGIN, values, "userName = ?", new String[]{username});
        db.close();
    }

}
