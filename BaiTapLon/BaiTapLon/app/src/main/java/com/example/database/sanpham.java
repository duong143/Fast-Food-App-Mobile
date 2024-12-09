package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sanpham extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sanpham";
    private static final int DATABASE_VERSION = 5;

    public sanpham(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qSP = "CREATE TABLE IF NOT EXISTS SanPham (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tensp TEXT, " +
                "gia REAL, " +
                "soluong INTEGER, " +
                "hinhanh TEXT)";
        db.execSQL(qSP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        onCreate(db);
    }
}
