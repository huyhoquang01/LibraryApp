package com.example.superandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superandroidproject.QuanLi.QuanLiHoaDonActivity;
import com.example.superandroidproject.QuanLi.QuanLiLoaiSachActivity;
import com.example.superandroidproject.QuanLi.QuanLiNguoiDungActivity;
import com.example.superandroidproject.QuanLi.QuanLiSachActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvNguoiDung, tvLoaiSach, tvSach, tvHoaDon, tvThongKe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNguoiDung = findViewById(R.id.tvNguoiDung);
        tvLoaiSach = findViewById(R.id.tvTheLoai);
        tvSach = findViewById(R.id.tvSach);
        tvHoaDon = findViewById(R.id.tvHoaDon);
        tvThongKe = findViewById(R.id.tvThongKe);

        tvNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuanLiNguoiDungActivity.class);
                startActivity(intent);
            }
        });

        tvLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuanLiLoaiSachActivity.class);
                startActivity(intent);
            }
        });

        tvSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuanLiSachActivity.class);
                startActivity(intent);
            }
        });

        tvHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuanLiHoaDonActivity.class);
                startActivity(intent);
            }
        });

        tvThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
                startActivity(intent);
            }
        });
    }

    // HIỆN DIALOG OPTION MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.buttonDoiMatKhau) {
            Intent intent = new Intent(MainActivity.this, DoiMatKhauActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.buttonDangXuat) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}