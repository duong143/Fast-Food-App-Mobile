package com.example.baitaplon;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.hoadon;
import com.example.itemsanpham;

import java.util.ArrayList;

public class giohang extends AppCompatActivity {
    private ArrayList<itemsanpham> cartList;
    private adaptergiohang cartAdapter;
    private RecyclerView danhsachgh;
    private TextView tongtien;
    private Button btndathang;
    private ImageButton quaylaigh;
    private hoadon dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        btndathang = findViewById(R.id.btndathang);
        danhsachgh = findViewById(R.id.danhsachgh);
        quaylaigh = findViewById(R.id.quaylaigh);
        tongtien = findViewById(R.id.tongtien);
        dbHelper = new hoadon(this);
        quaylaigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(giohang.this, quanlyitemsp.class));
            }
        });
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(giohang.this, thanhtoan.class);
                intent.putExtra("cart", cartList);
                startActivity(intent);
            }
        });
        cartList = (ArrayList<itemsanpham>) getIntent().getSerializableExtra("cart");

        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        danhsachgh.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new adaptergiohang(this, cartList, new adaptergiohang.OnCartActionListener() {
            @Override
            public void onIncreaseQuantity(int position) {
                cartList.get(position).setSoluong(cartList.get(position).getSoluong() + 1);
                cartAdapter.notifyItemChanged(position);
                calculateTotalPrice();
            }

            @Override
            public void onDecreaseQuantity(int position) {
                if (cartList.get(position).getSoluong() > 1) {
                    cartList.get(position).setSoluong(cartList.get(position).getSoluong() - 1);
                    cartAdapter.notifyItemChanged(position);
                    calculateTotalPrice();
                } else {
                    Toast.makeText(giohang.this, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onRemoveItem(int position) {
                cartList.remove(position);
                cartAdapter.notifyItemRemoved(position);
                cartAdapter.notifyItemRangeChanged(position, cartList.size());
                calculateTotalPrice();
            }
        });

        danhsachgh.setAdapter(cartAdapter);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        double total = 0;
        for (itemsanpham item : cartList) {
            total += item.getGia() * item.getSoluong();
        }
        tongtien.setText("Tổng: " + total + " VND");
    }

}
