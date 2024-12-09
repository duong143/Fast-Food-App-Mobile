package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class hoadonchitiet extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HoaDonChiTiet";
    private static final int DATABASE_VERSION = 1;
    public hoadonchitiet(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS HoaDonChiTiet (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tensanpham TEXT NOT NULL, " +
                "soluong INTEGER NOT NULL, " +
                "tonggia INTEGER NOT NULL, " +
                "sodienthoai TEXT, " +
                "diachi TEXT, " +
                "ngay TEXT NOT NULL DEFAULT (date('now'))" +
                ")";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HoaDonChiTiet");
        onCreate(db);
    }
}
