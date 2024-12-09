package com.example.baitaplon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database.hoadonchitiet;

public class BaoCaoThongKe extends AppCompatActivity {

    private TextView thongke;
    private ImageButton quaylaibctk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_thong_ke);
        quaylaibctk = findViewById(R.id.quaylaibctk);
        thongke = findViewById(R.id.thongke);
        quaylaibctk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaoCaoThongKe.this,quanlyapp.class));
            }
        });

        // Lấy thông tin báo cáo chi tiết về sản phẩm, số lượng và tổng tiền
        generateReport();
    }

    private void generateReport() {
        hoadonchitiet dbHelperCT = new hoadonchitiet(this);
        SQLiteDatabase db = dbHelperCT.getReadableDatabase();

        // Truy vấn thông tin chi tiết từ bảng "HoaDonChiTiet"
        Cursor cursor = db.rawQuery("SELECT tensanpham, SUM(soluong) AS total_quantity, SUM(tonggia) AS total_price FROM HoaDonChiTiet GROUP BY tensanpham", null);

        StringBuilder report = new StringBuilder();
        double grandTotal = 0;

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("tensanpham"));
                @SuppressLint("Range") int totalQuantity = cursor.getInt(cursor.getColumnIndex("total_quantity"));
                @SuppressLint("Range") double totalPrice = cursor.getDouble(cursor.getColumnIndex("total_price"));


                grandTotal += totalPrice;

                report.append("Sản phẩm: ").append(productName).append("\n")
                        .append("Số lượng bán: ").append(totalQuantity).append("\n")
                        .append("Tổng giá: ").append(totalPrice).append(" VND\n\n");

                cursor.moveToNext();
            }
            cursor.close();
        }


        report.append("\nTổng doanh thu: ").append(grandTotal).append(" VND");


        thongke.setText(report.toString());
    }
}
