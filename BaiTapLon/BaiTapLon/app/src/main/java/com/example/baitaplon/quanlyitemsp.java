package com.example.baitaplon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.sanpham;
import com.example.itemsanpham;

import java.util.ArrayList;

public class quanlyitemsp extends AppCompatActivity {
    private adaptersanpham adapter;
    private RecyclerView quanlyitemspp;
    private sanpham databaseHelper;
    private ImageButton quaylaisp;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanlyitemsp);
        quanlyitemspp = findViewById(R.id.quanlyitemspp);
        quaylaisp = findViewById(R.id.quaylaisp);
        quanlyitemspp.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new sanpham(this);
        quaylaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(quanlyitemsp.this, MainActivity.class));
            }
        });

        loadProducts();

    }
    private void loadProducts() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham", null);

        ArrayList<itemsanpham> productList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("tensp"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("gia"));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("soluong"));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("hinhanh"));

                productList.add(new itemsanpham(name, price, quantity, image));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new adaptersanpham(this, productList);
        quanlyitemspp.setAdapter(adapter);
    }
    public void showCart() {
        ArrayList<itemsanpham> cartList = adapter.getCartList();
        for (itemsanpham product : cartList) {
            Log.d("Cart", "Sản phẩm: " + product.getTen() + ", Giá: " + product.getGia());
        }
    }

}

