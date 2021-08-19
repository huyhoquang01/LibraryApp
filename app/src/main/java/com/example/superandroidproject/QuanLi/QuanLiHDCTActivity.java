package com.example.superandroidproject.QuanLi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superandroidproject.R;
import com.example.superandroidproject.adapter.HoaDonCTAdapter;
import com.example.superandroidproject.dao.HoaDonCT_DAO;
import com.example.superandroidproject.dao.HoaDonDAO;
import com.example.superandroidproject.dao.SachDao;
import com.example.superandroidproject.model.HoaDonChiTietModel;
import com.example.superandroidproject.model.HoaDonModel;
import com.example.superandroidproject.model.LoaiSachModel;
import com.example.superandroidproject.model.SachModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QuanLiHDCTActivity extends AppCompatActivity {
    RecyclerView rcv_HDCT;
    Button btnThemHDCT, btnTongTien;
    ArrayList<HoaDonChiTietModel> listCT = new ArrayList<>();
    EditText edtMaHD, edtSoLuong, edtTenKhachHang;
    Spinner spnMaSach;
    HoaDonCTAdapter adapter;
    HoaDonModel hd = null;
    TextView tvTongTien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_hdct);
        AnhXaView();

        // show DỮ LIỆU LÊN RECYCLER VIEW
        Intent i = getIntent();
        hd = (HoaDonModel) i.getSerializableExtra("hdct");

        RecyclerView.LayoutManager manager = new LinearLayoutManager(QuanLiHDCTActivity.this);
        rcv_HDCT.setLayoutManager(manager);
//        listCT.clear();
        listCT = HoaDonCT_DAO.GetHDCT(QuanLiHDCTActivity.this, hd.getMaHoaDon());
        adapter = new HoaDonCTAdapter(QuanLiHDCTActivity.this, listCT);
        rcv_HDCT.setAdapter(adapter);

        // THÀNH TIỀN TẤT CẢ HÓA ĐƠN
        final double thanhTien = i.getDoubleExtra("thanhtien", 69);

        if (thanhTien != 69) {
            NumberFormat formatter = new DecimalFormat("#,###");
            tvTongTien.setText(formatter.format(thanhTien));
        }
//        btnTongTien.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NumberFormat formatter = new DecimalFormat("#,###");
//                tvTongTien.setText(formatter.format(thanhTien));
//            }
//        });

        // todo: chuyển hàm thêm hóa đơn chi tiết vào dialog
        insertHDCT(hd);
    }

    void AnhXaView() {
        rcv_HDCT = findViewById(R.id.rcvHDCT);
        btnThemHDCT = findViewById(R.id.btnThemHDCT);
        btnTongTien = findViewById(R.id.btnTongTien);
        edtMaHD = findViewById(R.id.edtMaHoaDonCT);
        spnMaSach = findViewById(R.id.spnThemMaSachCT);
        edtSoLuong = findViewById(R.id.edtSoLuongCT);
        tvTongTien = findViewById(R.id.tvTongTien);
        edtTenKhachHang = findViewById(R.id.tvTenKhachHang);
    }

    void insertHDCT(final HoaDonModel hd) {
        AnhXaView();
        edtMaHD.setText(String.valueOf(hd.getMaHoaDon()));
        edtTenKhachHang.setText(hd.getKhachHang());
        ArrayList<SachModel> list_MaSach = SachDao.GetAll(QuanLiHDCTActivity.this);
        ArrayAdapter spnAdapter = new ArrayAdapter(QuanLiHDCTActivity.this,
                android.R.layout.simple_spinner_dropdown_item, list_MaSach);
        spnMaSach.setAdapter(spnAdapter);

        btnThemHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maHoaDon = Integer.parseInt(edtMaHD.getText().toString());
                int soLuongMoi = Integer.parseInt(edtSoLuong.getText().toString());
                SachModel sachModel = (SachModel) spnMaSach.getSelectedItem();
                int gia1Sach = HoaDonCT_DAO.gia1Sach(QuanLiHDCTActivity.this, sachModel.getMaSach());

                HoaDonChiTietModel hdctMoi = new HoaDonChiTietModel(maHoaDon, sachModel.getMaSach(), soLuongMoi);
                hdctMoi.setGia1Sach(gia1Sach);

                if (HoaDonCT_DAO.ThemHDCT(hdctMoi, QuanLiHDCTActivity.this)) {
                    listCT.clear();
                    listCT.addAll(HoaDonCT_DAO.GetHDCT(QuanLiHDCTActivity.this, hd.getMaHoaDon()));
                    Toast.makeText(QuanLiHDCTActivity.this, "Đã thêm! Nhớ kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(QuanLiHDCTActivity.this, "Gâu gâu gâu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        double thanhTien2 = HoaDonCTAdapter.thanhTien2;
        Log.i("tt2", thanhTien2+" trong activity");
        if (thanhTien2 != 96) {
            NumberFormat formatter = new DecimalFormat("#,###");
            tvTongTien.setText(formatter.format(thanhTien2));
        }
    }
}