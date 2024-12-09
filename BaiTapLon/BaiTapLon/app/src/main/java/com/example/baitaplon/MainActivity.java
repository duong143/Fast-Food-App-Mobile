package com.example.baitaplon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
Button btntrainghiemngay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.navigationview);
        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        btntrainghiemngay = findViewById(R.id.btntrainghiemngay);
        btntrainghiemngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, quanlyitemsp.class));
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close );
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.thoat){
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear(); // Xóa toàn bộ dữ liệu trong SharedPreferences
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, dangnhap.class));
                    Toast.makeText(MainActivity.this,"Hẹn gặp lại quý khách :3",Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId()==R.id.thongtinlienhe){
                    startActivity(new Intent(MainActivity.this, thongtinlienhe.class));
                }
                if(item.getItemId()==R.id.trangchu){
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
                if (item.getItemId()==R.id.sanpham){
                    startActivity(new Intent(MainActivity.this, quanlyitemsp.class));
                }
                if (item.getItemId()==R.id.giohang){
                    startActivity(new Intent(MainActivity.this, giohang.class));
                }
                if(item.getItemId()==R.id.adimin){
                    startActivity(new Intent(MainActivity.this,admin.class));
                }
                if (item.getItemId()==R.id.donhang){
                    startActivity(new Intent(MainActivity.this,HOADON.class));
                }

                return false;
            }
        });

    }
}