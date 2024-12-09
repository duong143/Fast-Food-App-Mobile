package com.example.baitaplon;

import android.content.Intent;
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

public class dangky extends AppCompatActivity {
    TextInputEditText tendangnhap, hovaten, matkhau, nhaplaimatkhau;
    Button btndangky;
    TextView tvdangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangky);
        tendangnhap = findViewById(R.id.tendangnhap);
        hovaten = findViewById(R.id.hovaten);
        matkhau = findViewById(R.id.matkhau);
        nhaplaimatkhau = findViewById(R.id.nhaplaimatkhau);
        btndangky = findViewById(R.id.btndangky);
        tvdangnhap = findViewById(R.id.tvdangnhap);

        tvdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dangky.this, dangnhap.class));

            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tendangnhap = tendangnhap.getText().toString();
                String Hoten = hovaten.getText().toString();
                String Matkhau = matkhau.getText().toString();
                String Nhaplaimatkhau = nhaplaimatkhau.getText().toString();


                sql sqlite = new sql(dangky.this);
                if (Tendangnhap.isEmpty() || Hoten.isEmpty() || Matkhau.isEmpty() || Nhaplaimatkhau.isEmpty()) {
                    Toast.makeText(dangky.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (Matkhau.equals(Nhaplaimatkhau)) {
                        if (isValid(Matkhau)) {
                            sqlite.dangky(Tendangnhap, Matkhau, Hoten);
                            Toast.makeText(dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                            // Chuyển sang màn hình đăng nhập
                            startActivity(new Intent(dangky.this, dangnhap.class));
                            finish();
                        } else {
                            Toast.makeText(dangky.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(dangky.this, "Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private static boolean isValid(String passwordHere) {

        return passwordHere.length() >= 8;
    }
}


