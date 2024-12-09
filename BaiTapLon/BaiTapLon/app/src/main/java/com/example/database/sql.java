package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sql extends SQLiteOpenHelper {
    public sql(Context context) {
        super(context, "QLSP", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qND="create table NguoiDung(tendangnhap text primary key, matkhau text, hoten text)";
        db.execSQL(qND);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void dangky(String Tendangnhap, String Matkhau, String Hoten){
        ContentValues cv = new ContentValues();
        cv.put("tendangnhap",Tendangnhap);
        cv.put("matkhau",Matkhau);
        cv.put("hoten",Hoten);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("NguoiDung",null,cv);
        db.close();
    }


    public int dangnhap(String Tendangnhap, String Matkhau){
        int result = 0;
        String str[] = new String[2];
        str[0] = Tendangnhap;
        str[1] = Matkhau;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from NguoiDung where tendangnhap=? and matkhau=?",str);
        if(c.moveToNext()){
            result = 1;
        }
        return result;
    }
}
