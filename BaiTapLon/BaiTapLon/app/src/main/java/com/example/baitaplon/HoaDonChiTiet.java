package com.example.baitaplon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database.hoadonchitiet;

import java.util.ArrayList;

public class HoaDonChiTiet extends AppCompatActivity {

    private ListView lvHoaDonChiTiet;
    private Button btnBack;
    private ImageButton quaylaihdct;
    private ArrayList<String> hoaDonChiTietList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);

        lvHoaDonChiTiet = findViewById(R.id.lvHoaDonChiTiet);
        btnBack = findViewById(R.id.btnBack);
        quaylaihdct= findViewById(R.id.quaylaihdct);
        quaylaihdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HoaDonChiTiet.this,quanlyapp.class));
            }
        });

        hoaDonChiTietList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hoaDonChiTietList);

        lvHoaDonChiTiet.setAdapter(adapter);

        loadHoaDonChiTiet();

        btnBack.setOnClickListener(v -> {
            // Quay lại màn hình trước đó
            onBackPressed();
        });

        // Xử lý sự kiện click vào một item trong ListView
        lvHoaDonChiTiet.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = hoaDonChiTietList.get(position);
            Toast.makeText(HoaDonChiTiet.this, "Chọn: " + selectedItem, Toast.LENGTH_SHORT).show();
        });
    }

    private void loadHoaDonChiTiet() {
        hoadonchitiet dbHelper = new hoadonchitiet(this);
        Cursor cursor = dbHelper.getReadableDatabase().query("HoaDonChiTiet", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tenSanPham = cursor.getString(cursor.getColumnIndex("tensanpham"));
                @SuppressLint("Range") int soLuong = cursor.getInt(cursor.getColumnIndex("soluong"));
                @SuppressLint("Range") int tongGia = cursor.getInt(cursor.getColumnIndex("tonggia"));
                @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("diachi"));
                @SuppressLint("Range") String soDienThoai = cursor.getString(cursor.getColumnIndex("sodienthoai"));
                @SuppressLint("Range") String ngay = cursor.getString(cursor.getColumnIndex("ngay"));

                // Tạo chuỗi để hiển thị trong ListView
                String hoaDonChiTiet = "Sản phẩm: " + tenSanPham + "\n" +
                        "Số lượng: " + soLuong + "\n" +
                        "Tổng giá: " + tongGia + " VND\n" +
                        "Địa chỉ: " + diaChi + "\n" +
                        "Số điện thoại: " + soDienThoai + "\n" +
                        "Ngày: " + ngay;

                hoaDonChiTietList.add(hoaDonChiTiet);
            } while (cursor.moveToNext());
            cursor.close();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Không có dữ liệu hóa đơn chi tiết.", Toast.LENGTH_SHORT).show();
        }
    }
}
