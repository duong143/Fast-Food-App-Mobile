package com.example.baitaplon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class quanlyapp extends AppCompatActivity {
Button btnQLSanPham,btnHoaDonChiTiet,btnBaoCaoThongKe;
ImageButton quaylaiqla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanlyapp);
        quaylaiqla = findViewById(R.id.quaylaiqla);
        btnQLSanPham = findViewById(R.id.btnQLSanPham);
        btnHoaDonChiTiet = findViewById(R.id.btnHoaDonChiTiet);
        btnBaoCaoThongKe = findViewById(R.id.btnBaoCaoThongKe);
        quaylaiqla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quanlyapp.this, MainActivity.class));
            }
        });
        btnQLSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quanlyapp.this,quanlysanpham.class));
            }
        });
        btnBaoCaoThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quanlyapp.this, BaoCaoThongKe.class));
            }
        });
        btnHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quanlyapp.this, HoaDonChiTiet.class));
            }
        });

    }
}