package com.example.superandroidproject.QuanLi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superandroidproject.DoiMatKhauActivity;
import com.example.superandroidproject.LoginActivity;
import com.example.superandroidproject.R;
import com.example.superandroidproject.adapter.NguoiDungAdapter;
import com.example.superandroidproject.dao.NguoiDungDao;
import com.example.superandroidproject.model.NguoiDungModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLiNguoiDungActivity extends AppCompatActivity {
    RecyclerView rcvNguoiDung;
    FloatingActionButton fabThemNguoiDung;
    NguoiDungAdapter adapter;
    ArrayList<NguoiDungModel> list_NguoiDung = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_nguoi_dung);

        rcvNguoiDung = findViewById(R.id.rcvNguoiDung);
        rcvNguoiDung.setHasFixedSize(true);
        fabThemNguoiDung = findViewById(R.id.fab_ThemNguoiDung);

        // ADD Recycler View vào giao diện
        RecyclerView.LayoutManager manager = new LinearLayoutManager(QuanLiNguoiDungActivity.this);
        rcvNguoiDung.setLayoutManager(manager);

        list_NguoiDung = NguoiDungDao.GetAll(QuanLiNguoiDungActivity.this);
        adapter = new NguoiDungAdapter(QuanLiNguoiDungActivity.this, list_NguoiDung);
        rcvNguoiDung.setAdapter(adapter);

        // HIỆN DIALOG THÊM NGƯỜI DÙNG
        fabThemNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem(QuanLiNguoiDungActivity.this);
            }
        });
    }


    // DIALOG THÊM NGƯỜI DÙNG
    public void DialogThem(final Context context) {
        // todo: hiện dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_them_nguoi_dung, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();
        // ========================================
        Button btnThemNguoiDung = view.findViewById(R.id.btnThemNguoiDung);
        final EditText edtTenDangNhap = view.findViewById(R.id.edtThemTenDangNhap);
        final EditText edtMatKhau = view.findViewById(R.id.edtThemMatKhau);
        final EditText edtMatKhau2 = view.findViewById(R.id.edtThemLaiMatKhau);
        final EditText edtHoTen = view.findViewById(R.id.edtThemHoTen);
        final EditText edtSoDienThoai = view.findViewById(R.id.edtThemSoDienThoai);
        final Button btnHuy = view.findViewById(R.id.btnHuyThemNguoiDung);


        btnThemNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameMoi = edtTenDangNhap.getText().toString();
                String matkhauMoi = edtMatKhau.getText().toString();
                String matKhauMoi2 = edtMatKhau2.getText().toString();
                String hoTenMoi = edtHoTen.getText().toString();
                String soDienThoaiMoi = edtSoDienThoai.getText().toString();
                NguoiDungModel nguoiDungMoi = new NguoiDungModel(usernameMoi, matkhauMoi, hoTenMoi, soDienThoaiMoi);

                if (edtTenDangNhap.getText().toString().equals("") || edtMatKhau.getText().toString().equals("") || edtHoTen.getText().toString().equals("")) {
                    Toast.makeText(context, "Điền đầy đủ nào ", Toast.LENGTH_SHORT).show();
                } else if (!matkhauMoi.equals(matKhauMoi2)) {
                    Toast.makeText(context, "Xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    if (NguoiDungDao.ThemNguoiDung(nguoiDungMoi, context)) {
                        Toast.makeText(context, "Thêm hoàn tất. Cày view Sếp thôi", Toast.LENGTH_SHORT).show();
                        list_NguoiDung.clear();
                        list_NguoiDung.addAll(NguoiDungDao.GetAll(context));
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Oi zoi oi!! Lỗi kìa", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}