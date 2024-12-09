package com.example.baitaplon;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.database.sanpham;

public class quanlysanpham extends AppCompatActivity {
    private EditText tenspadmin, giaspadmin, soluongspadmin, edtProductImage;
    private Button btnthemadmin, btnxoaadmin, btnxemspadmin,btnchonhinhanhadmin;
    private sanpham databaseHelper;
    private ImageView anhadmin;
    private ImageButton quaylaiqlsp;
    private Uri selectedImageUri;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanlysanpham);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
        tenspadmin = findViewById(R.id.tenspadmin);
        giaspadmin = findViewById(R.id.edtgiaspadmin);
        soluongspadmin = findViewById(R.id.soluongspadmin);
        anhadmin = findViewById(R.id.anhadmin);
        btnchonhinhanhadmin = findViewById(R.id.btnchonhinhanhadmin);
        btnthemadmin = findViewById(R.id.btnthemadmin);
        btnxoaadmin = findViewById(R.id.btnxoaadmin);
        btnxemspadmin = findViewById(R.id.btnxemspadmin);
        databaseHelper = new sanpham(this);
        quaylaiqlsp = findViewById(R.id.quaylaiqlsp);
        quaylaiqlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quanlysanpham.this,quanlyapp.class));
            }
        });
        btnchonhinhanhadmin.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        btnthemadmin.setOnClickListener(v -> {
            String name = tenspadmin.getText().toString();
            String price = giaspadmin.getText().toString();
            String quantity = soluongspadmin.getText().toString();
            String image = selectedImageUri != null ? selectedImageUri.toString() : null;

            // Kiểm tra dữ liệu nhập
            if (name.isEmpty()) {
                Toast.makeText(this, "Tên sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }
            if (price.isEmpty()) {
                Toast.makeText(this, "Giá sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }
            if (quantity.isEmpty()) {
                Toast.makeText(this, "Số lượng sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedImageUri == null) {
                Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ghi vào cơ sở dữ liệu
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tensp", name);
            values.put("gia", Double.parseDouble(price));
            values.put("soluong", Integer.parseInt(quantity));
            values.put("hinhanh", image);

            try {
                long result = db.insertWithOnConflict("SanPham", null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (result != -1) {
                    Toast.makeText(this, "Thêm / cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm / cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Xóa sản phẩm
        btnxoaadmin.setOnClickListener(v -> {
            String name = tenspadmin.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên sản phẩm cần xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            int rows = db.delete("SanPham", "tensp = ?", new String[]{name});
            if (rows > 0) {
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển đến trang xem danh sách sản phẩm
        btnxemspadmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, quanlyitemsp.class);
            startActivity(intent);
        });
    }
    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    anhadmin.setImageURI(selectedImageUri);
                    Log.d("DEBUG", "Selected Image URI: " + selectedImageUri); // Kiểm tra giá trị
                } else {
                    Toast.makeText(this, "Chọn ảnh thất bại", Toast.LENGTH_SHORT).show();
                }

            }
    );
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã cấp quyền truy cập bộ nhớ.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền truy cập bộ nhớ bị từ chối.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}



