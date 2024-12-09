package com.example.baitaplon;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database.hoadon;
import com.example.database.hoadonchitiet;
import com.example.database.sanpham;
import com.example.itemsanpham;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class thanhtoan extends AppCompatActivity {
    private TextView thongtingiohang;
    private EditText diachi, sodienthoai;
    private Button btnxacnhandathang;
    private ImageButton quaylaittgh;
    private ArrayList<itemsanpham> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanhtoan);
        thongtingiohang = findViewById(R.id.thongtingiohang);
        sodienthoai = findViewById(R.id.sodienthoai);
        diachi = findViewById(R.id.diachi);
        btnxacnhandathang = findViewById(R.id.btnxacnhandathang);
        quaylaittgh = findViewById(R.id.quaylaittgh);
        cartList = (ArrayList<itemsanpham>) getIntent().getSerializableExtra("cart");

        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        showCartDetails();
        quaylaittgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(thanhtoan.this, quanlyitemsp.class));
            }
        });
        btnxacnhandathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = diachi.getText().toString();
                String phoneNumber = sodienthoai.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(thanhtoan.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(thanhtoan.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveToInvoice(address, phoneNumber);
                Toast.makeText(thanhtoan.this, "Đặt hàng thành công vui lòng chú ý điện thoại!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(thanhtoan.this, HOADON.class);
                startActivity(intent);
            }


        });
    }

    private void showCartDetails() {
        StringBuilder cartDetails = new StringBuilder();
        double total = 0;

        for (itemsanpham item : cartList) {
            cartDetails.append(item.getTen())
                    .append(" - Số lượng: ")
                    .append(item.getSoluong())
                    .append(" - Tổng giá: ")
                    .append(item.getGia() * item.getSoluong())
                    .append(" VND\n");
            total += item.getGia() * item.getSoluong();
        }

        cartDetails.append("\nTổng cộng: ").append(total).append(" VND");
        thongtingiohang.setText(cartDetails.toString());
    }

    private void saveToInvoice(String address, String phoneNumber) {
        // Lấy tên tài khoản từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("tendangnhap", null);

        // Kiểm tra nếu không có tên tài khoản (người dùng chưa đăng nhập)
        if (tendangnhap == null) {
            Toast.makeText(this, "Vui lòng đăng nhập trước khi đặt hàng!", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Lưu hóa đơn vào bảng "HoaDon" (cũ của bạn)
        hoadon dbHelper = new hoadon(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (itemsanpham item : cartList) { // Duyệt qua từng item trong giỏ hàng
            // Lưu thông tin vào bảng HoaDon (hóa đơn chính)
            ContentValues values = new ContentValues();
            values.put("tensanpham", item.getTen());
            values.put("soluong", item.getSoluong());
            values.put("tonggia", item.getGia() * item.getSoluong());
            values.put("diachi", address); // Lưu địa chỉ vào hóa đơn
            values.put("sodienthoai", phoneNumber);
            values.put("tendangnhap", tendangnhap); // Lưu tên tài khoản vào hóa đơn

            long rowId = db.insert("HoaDon", null, values);
            if (rowId == -1) {
                Log.e("ThanhToan", "Không thể lưu vào cơ sở dữ liệu");
            } else {
                Log.d("ThanhToan", "Đã lưu vào hóa đơn với ID: " + rowId);
            }

            // **Lưu chi tiết hóa đơn vào bảng HoaDonChiTiet**
            hoadonchitiet dbHelperCT = new hoadonchitiet(this);  // Đối tượng cho bảng HoaDonChiTiet
            SQLiteDatabase dbCT = dbHelperCT.getWritableDatabase();

            // Lưu thông tin chi tiết vào bảng HoaDonChiTiet
            ContentValues valuesCT = new ContentValues();
            valuesCT.put("tensanpham", item.getTen());
            valuesCT.put("soluong", item.getSoluong());
            valuesCT.put("tonggia", item.getGia() * item.getSoluong());
            valuesCT.put("diachi", address);
            valuesCT.put("sodienthoai", phoneNumber);
            valuesCT.put("ngay",currentDate);  // Lưu ngày đặt hàng vào cột "ngay"

            long rowIdCT = dbCT.insert("HoaDonChiTiet", null, valuesCT);
            if (rowIdCT == -1) {
                Log.e("ThanhToan", "Không thể lưu vào bảng HoaDonChiTiet");
            } else {
                Log.d("ThanhToan", "Đã lưu vào HoaDonChiTiet với ID: " + rowIdCT);
            }
        }
    }
}