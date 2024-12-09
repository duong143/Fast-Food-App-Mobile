package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class hoadon extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hoadon";
    private static final int DATABASE_VERSION = 8;

    public hoadon(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cập nhật câu lệnh tạo bảng với trường tendangnhap
        String createTableSQL = "CREATE TABLE IF NOT EXISTS HoaDon (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tendangnhap TEXT NOT NULL, " +
                "tensanpham TEXT NOT NULL, " +
                "soluong INTEGER NOT NULL, " +
                "tonggia INTEGER NOT NULL, " +
                "diachi TEXT, " +
                "sodienthoai TEXT" +
                ")";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {

            db.execSQL("DROP TABLE IF EXISTS HoaDon");

            onCreate(db);
        }
        }
}
