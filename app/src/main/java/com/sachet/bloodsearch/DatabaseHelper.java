package com.sachet.bloodsearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sachet.bloodsearch.helper.UserInfo;
import com.sachet.bloodsearch.userModel.DataBaseUserModel;

import java.util.ArrayList;

/**
 * Created by AJ on 2/3/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    String fullname, userName, age, gender, bloodgroup, contact, email, city;
    private byte[] userImage;
    static String name = "bloodsearch";
    static int version = '1';
    DataBaseUserModel dataBaseUserModel;

    String createTableUserSql = "CREATE TABLE if not exists `bloodbank` (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`fullname`\tTEXT,\n" +
            "\t`gender`\tTEXT,\n" +
            "\t`age`\tTEXT,\n" +
            "\t`phone`\tTEXT,\n" +
            "\t`email`\tTEXT,\n" +
            "\t`location`\tTEXT,\n" +
            "\t`bloodgroup`\tTEXT,\n" +
            "\t`username`\tTEXT UNIQUE,\n" +
            "\t`password`\tTEXT,\n" +
            "\t`image`\tBLOB\n" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createTableUserSql);
    }

    public void insertUser(ContentValues contentValues) {

        getWritableDatabase().insert("bloodbank", "", contentValues);
    }

    public void updateUser(String userName, ContentValues contentValues){


        getWritableDatabase().update("bloodbank",contentValues,"username='"+userName+"'",null);
    }

    public boolean isValidLogin(String username, String password) {

        String sql = "Select count(*) from bloodbank where username= '" + username + "' and password= '" + password + "'";
        SQLiteStatement sqLiteStatement = getWritableDatabase().compileStatement(sql);
        long l = sqLiteStatement.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else {
            return false;
        }
    }



    public ArrayList<UserInfo> getUserList() {

        String sql = "Select * from bloodbank";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        ArrayList<UserInfo> list = new ArrayList<UserInfo>();
        while (c.moveToNext()) {
            UserInfo info = new UserInfo();
            info.id = c.getString(c.getColumnIndex("id"));
            info.fullname = c.getString(c.getColumnIndex("fullname"));
            info.gender = c.getString(c.getColumnIndex("gender"));
            info.age = c.getString(c.getColumnIndex("age"));
            info.phone = c.getString(c.getColumnIndex("phone"));
            info.email = c.getString(c.getColumnIndex("email"));
            info.location = c.getString(c.getColumnIndex("location"));
            info.bloodgroup = c.getString(c.getColumnIndex("bloodgroup"));
            info.password = c.getString(c.getColumnIndex("password"));
            info.username = c.getString(c.getColumnIndex("username"));
            info.image = c.getBlob(c.getColumnIndex("image"));
            list.add(info);
        }
        c.close();
        return list;
    }


    public ArrayList<DataBaseUserModel> getUserInfo(String username) {
        ArrayList<DataBaseUserModel> userModels = new ArrayList<>();
        String sql = "Select * from bloodbank where username = '" + username + "'";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        c.moveToFirst();
        if (c.getCount()>0 && !c.isAfterLast()){
            for (int i =0;i<c.getCount();i++) {
                fullname = c.getString(c.getColumnIndex("fullname"));
                gender = c.getString(c.getColumnIndex("gender"));
                age = c.getString(c.getColumnIndex("age"));
                contact = c.getString(c.getColumnIndex("phone"));
                email = c.getString(c.getColumnIndex("email"));
                city = c.getString(c.getColumnIndex("location"));
                bloodgroup = c.getString(c.getColumnIndex("bloodgroup"));
                userName = c.getString(c.getColumnIndex("username"));
                userImage = c.getBlob(c.getColumnIndex("image"));

                dataBaseUserModel = new DataBaseUserModel(fullname,
                        userName,age,gender,bloodgroup,city,contact,email,userImage);
                userModels.add(dataBaseUserModel);
                c.moveToFirst();
            }
        }
        c.close();
        return userModels;
    }

    public UserInfo getUserDetails(String userName){
        String sql = "Select * from bloodbank where username='" + userName + "'";
Cursor c = getReadableDatabase().rawQuery(sql,null);
UserInfo info = new UserInfo();
while (c.moveToNext()){
    info.id = c.getString(c.getColumnIndex("id"));
    info.fullname = c.getString(c.getColumnIndex("fullname"));
    info.gender = c.getString(c.getColumnIndex("gender"));
    info.age = c.getString(c.getColumnIndex("age"));
    info.phone = c.getString(c.getColumnIndex("phone"));
    info.email = c.getString(c.getColumnIndex("email"));
    info.location = c.getString(c.getColumnIndex("location"));
    info.bloodgroup = c.getString(c.getColumnIndex("bloodgroup"));
    info.password = c.getString(c.getColumnIndex("password"));
    info.username = c.getString(c.getColumnIndex("username"));
    info.image = c.getBlob(c.getColumnIndex("image"));


}
c.close();

return info;

    }



    public ArrayList<UserInfo> getSearchUserList(String bloodgroup, String location) {

        String sql = "Select * from bloodbank where bloodgroup='" + bloodgroup + "' and location ='" + location + "'";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        ArrayList<UserInfo> list = new ArrayList<UserInfo>();
        if (c != null) {

            if (c.getCount() > 0) {
                c.moveToNext();
                UserInfo info = new UserInfo();
                info.id = c.getString(c.getColumnIndex("id"));
                info.fullname = c.getString(c.getColumnIndex("fullname"));
                info.gender = c.getString(c.getColumnIndex("gender"));
                info.age = c.getString(c.getColumnIndex("age"));
                info.phone = c.getString(c.getColumnIndex("phone"));
                info.email = c.getString(c.getColumnIndex("email"));
                info.location = c.getString(c.getColumnIndex("location"));
                info.bloodgroup = c.getString(c.getColumnIndex("bloodgroup"));
                info.password = c.getString(c.getColumnIndex("password"));
                info.username = c.getString(c.getColumnIndex("username"));
                info.image = c.getBlob(c.getColumnIndex("image"));
                list.add(info);


                c.close();
                return list;
            }
        }
        return null;

    }




    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
