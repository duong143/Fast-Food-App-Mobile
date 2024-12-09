package com.example.baitaplon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.database.sql;
import com.google.android.material.textfield.TextInputEditText;

public class dangnhap extends AppCompatActivity {
    TextInputEditText nhapdangnhap, nhapmatkhau;
    Button btndangnhap;
    TextView tvdangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangnhap);

        nhapdangnhap = findViewById(R.id.nhapdangnhap);
        nhapmatkhau = findViewById(R.id.nhapmatkhau);
        btndangnhap = findViewById(R.id.btndangnhap);
        tvdangky = findViewById(R.id.tvdangky);

        // Chuyển sang màn hình đăng ký
        tvdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dangnhap.this, dangky.class));
            }
        });

        // Xử lý sự kiện đăng nhập
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tendangnhap = nhapdangnhap.getText().toString();
                String Matkhau = nhapmatkhau.getText().toString();
                if (Tendangnhap.isEmpty() || Matkhau.isEmpty()) {
                    Toast.makeText(dangnhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    sql sqlite = new sql(dangnhap.this);

                    if (sqlite.dangnhap(Tendangnhap, Matkhau) == 1) {

                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tendangnhap", Tendangnhap);
                        editor.apply(); // Lưu dữ liệu

                        Toast.makeText(dangnhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển sang màn hình chính sau khi đăng nhập thành công
                        startActivity(new Intent(dangnhap.this, MainActivity.class));
                        finish(); // Đóng Activity đăng nhập
                    } else {
                        // Thông báo đăng nhập thất bại
                        Toast.makeText(dangnhap.this, "Tên đăng nhập hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}