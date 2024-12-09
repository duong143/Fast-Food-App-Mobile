package com.example.baitaplon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class admin extends AppCompatActivity {
    private static final String ADMINCODE = "572004";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        EditText edmaad = findViewById(R.id.edmaad);
        Button btnxacnhan = findViewById(R.id.btnxacnhan);
        TextView tieude = findViewById(R.id.tieude);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredCode = edmaad.getText().toString().trim();

                if (enteredCode.equals(ADMINCODE)) {
                    // Chuyển đến trang admin nếu mã số đúng
                    Toast.makeText(admin.this, "Truy cập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin.this, quanlyapp.class); // Tạo activity Dashboard
                    startActivity(intent);
                    finish(); // Đóng trang nhập mã số
                } else {
                    // Hiển thị lỗi nếu mã số sai
                    Toast.makeText(admin.this, "Mã số không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
