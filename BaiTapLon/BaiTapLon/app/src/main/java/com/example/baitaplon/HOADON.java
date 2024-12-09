package com.example.baitaplon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database.hoadon;

public class HOADON extends AppCompatActivity {
    private TextView thongtinhoadon;
    private ImageButton quaylaihd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoadon);
        thongtinhoadon = findViewById(R.id.thongtinhoadon);
        quaylaihd = findViewById(R.id.quaylaihd);
quaylaihd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(HOADON.this,MainActivity.class));
    }
});

        displayInvoiceDetails();
    }

    private void displayInvoiceDetails() {

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("tendangnhap", null);

        if (tendangnhap == null) {
            thongtinhoadon.setText("Vui lòng đăng nhập!");
            return;
        }

        hoadon dbHelper = new hoadon(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String query = "SELECT * FROM HoaDon WHERE tendangnhap = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tendangnhap});

        StringBuilder invoiceDetails = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tensanpham = cursor.getString(cursor.getColumnIndex("tensanpham"));
                @SuppressLint("Range") int soluong = cursor.getInt(cursor.getColumnIndex("soluong"));
                @SuppressLint("Range") int tonggia = cursor.getInt(cursor.getColumnIndex("tonggia"));
                @SuppressLint("Range") String diachi = cursor.getString(cursor.getColumnIndex("diachi"));
                @SuppressLint("Range") String sodienthoai = cursor.getString(cursor.getColumnIndex("sodienthoai"));
                @SuppressLint("Range") String tentaikhoan = cursor.getString(cursor.getColumnIndex("tendangnhap")); // Lấy tên tài khoản

                invoiceDetails.append("Tài khoản: ").append(tentaikhoan).append("\n")  // Hiển thị tên tài khoản
                        .append("Tên sản phẩm: ").append(tensanpham)
                        .append("  Số lượng: ").append(soluong)
                        .append("  Tổng giá: ").append(tonggia).append(" VND\n")
                        .append("Địa chỉ: ").append(diachi).append("\n")
                        .append("Số điện thoại: ").append(sodienthoai).append("\n\n");

            } while (cursor.moveToNext());
            cursor.close();
        } else {
            invoiceDetails.append("Không có hóa đơn nào.");
        }


        thongtinhoadon.setText(invoiceDetails.toString());
    }
}
