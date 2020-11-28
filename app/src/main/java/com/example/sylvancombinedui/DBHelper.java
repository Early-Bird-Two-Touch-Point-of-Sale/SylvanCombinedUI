package com.example.sylvancombinedui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DBHelper extends SQLiteOpenHelper {
    private static final int REQUEST_ENABLE_BT = 1;

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(itemID TEXT primary key, orderID TEXT, itemName TEXT, " +
                "toppings TEXT, holds TEXT, other TEXT, price TEXT, date DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertUserData(String itemID, String orderID, String itemName, String toppings,
                                  String holds, String other, String price)
    {
        Date currentTime = Calendar.getInstance().getTime();
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemID", itemID);
        contentValues.put("orderID", orderID);
        contentValues.put("itemName", itemName);
        contentValues.put("toppings", toppings);
        contentValues.put("holds", holds);
        contentValues.put("other", other);
        contentValues.put("price", price);
        //contentValues.put("taxable", taxable);
        contentValues.put("date", new SimpleDateFormat("yyyy-MM-dd, h:mm a zzzz").format(new Date()));
        long result=DB.insert("Userdetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updateUserData(String itemID, String orderID, String itemName)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderID", orderID);
        contentValues.put("itemName", itemName);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where itemID = ?", new String[] {itemID});
        if(cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "itemID=?", new String[] {itemID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteData(String itemID)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where itemID = ?", new String[] {itemID});
        if(cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "itemID=?", new String[] {itemID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails ", null);
        return cursor;
    }

    public Cursor getOrder(String orderNum)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where orderID = ?", new String[] {orderNum});
        return cursor;
    }

    public Cursor dailyOrder(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String todayStart = sdf.format(date) + ", 12:00 AM, Pacific Standard Time";

        //Date todayStart = new SimpleDateFormat("yyyy-MM-dd, h:mm a zzzz").format(new Date());
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where Date >= ?", new String[] {todayStart});
        return cursor;
    }

    public Cursor yearlyOrder(){
        Date date = new Date();
        String strDateFormat = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String todayStart = sdf.format(date) + "-01-01, 12:00 AM, Pacific Standard Time";

        //Date todayStart = new SimpleDateFormat("yyyy-MM-dd, h:mm a zzzz").format(new Date());
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where Date >= ?", new String[] {todayStart});
        return cursor;
    }

}
