package com.example.superandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.superandroidproject.dao.HoaDonCT_DAO;

public class ThongKeActivity extends AppCompatActivity {
    TextView tvDoanhThuNgay, tvDoanhThuThang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        tvDoanhThuNgay = findViewById(R.id.tvDoanhThuNgay);
        tvDoanhThuThang = findViewById(R.id.tvDoanhThuThang);

        int doanhThuNgay = HoaDonCT_DAO.getDoanhThuTheoNgay(ThongKeActivity.this);
        tvDoanhThuNgay.setText(String.valueOf(doanhThuNgay));

        int doanhThuThang = HoaDonCT_DAO.getDoanhThuTheoThang(ThongKeActivity.this);
        tvDoanhThuThang.setText(String.valueOf(doanhThuThang));
    }
}