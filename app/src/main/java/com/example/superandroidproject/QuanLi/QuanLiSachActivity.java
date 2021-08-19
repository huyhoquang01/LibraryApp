package com.example.superandroidproject.QuanLi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superandroidproject.R;
import com.example.superandroidproject.adapter.SachAdapter;
import com.example.superandroidproject.dao.LoaiSachDao;
import com.example.superandroidproject.dao.SachDao;
import com.example.superandroidproject.model.LoaiSachModel;
import com.example.superandroidproject.model.SachModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLiSachActivity extends AppCompatActivity {
    RecyclerView rcvSach;
    FloatingActionButton fabThemSach;
    SachAdapter adapter;
    ArrayList<SachModel> listSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_sach);

        fabThemSach = findViewById(R.id.fab_ThemSach);
        rcvSach = findViewById(R.id.rcvSach);

        // Thêm layout vào giao diện
        RecyclerView.LayoutManager manager = new LinearLayoutManager(QuanLiSachActivity.this);
        rcvSach.setLayoutManager(manager);

        listSach = SachDao.GetAll(QuanLiSachActivity.this);
        adapter = new SachAdapter(QuanLiSachActivity.this, listSach);
        rcvSach.setAdapter(adapter);

        fabThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem(QuanLiSachActivity.this);
            }
        });

    }
    private  void DialogThem(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuanLiSachActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        final Dialog dialog = builder.create();
        dialog.show();
        // ======================================== //
        final Spinner spnThemMaTheLoaiSach = view.findViewById(R.id.spnThemMaTheLoaiSach);
        Button btnThemSach = view.findViewById(R.id.btnThemSach);
        final EditText edtTenSach = view.findViewById(R.id.edtThemTenSach);
        final EditText edtTacGia = view.findViewById(R.id.edtThemTacGia);
        final EditText edtNXB = view.findViewById(R.id.edtThemNXB);
        final EditText edtGiaTien = view.findViewById(R.id.edtThemGiaTien);
        final EditText edtSoLuong = view.findViewById(R.id.edtThemSoLuong);
        final Button btnHuy = view.findViewById(R.id.btnHuyThemSach);

        // THÊM ITEM VÀO SPINNER SỬA MÃ THỂ LOẠI
        ArrayList<LoaiSachModel> theLoaiSach = LoaiSachDao.GetAll(QuanLiSachActivity.this);
        ArrayAdapter spnAdapter = new ArrayAdapter(QuanLiSachActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                theLoaiSach);
        spnThemMaTheLoaiSach.setAdapter(spnAdapter);

        btnThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSachMoi = edtTenSach.getText().toString();
                String tacGiaMoi = edtTacGia.getText().toString();
                String nxbMoi = edtNXB.getText().toString();
                int giaTienMoi = Integer.parseInt(edtGiaTien.getText().toString());
                int soLuongMoi = Integer.parseInt(edtSoLuong.getText().toString());

                LoaiSachModel loaiSachModel = (LoaiSachModel) spnThemMaTheLoaiSach.getSelectedItem();
                int maTheLoai = loaiSachModel.getMaTheLoai();

                SachModel sachMoi = new SachModel(tenSachMoi, maTheLoai, tacGiaMoi, nxbMoi, giaTienMoi, soLuongMoi);

                if (SachDao.ThemSach(sachMoi, context)) {
                    Toast.makeText(context, "Thêm hoàn tất. Làm tí nhạc chill đi", Toast.LENGTH_SHORT).show();
//                    int spnPosition = spnThemMaTheLoaiSach.getSelectedItemPosition(); // IN RA VỊ TRÍ
//                    Toast.makeText(context, "Vị trí số " + spnPosition, Toast.LENGTH_SHORT).show();
                    // CẬP NHẬT DANH SÁCH
                    listSach.clear();
                    listSach.addAll(SachDao.GetAll(context));
                    adapter.notifyDataSetChanged();

                    // TẮT DIALOG
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Oi zoi oi!! Lỗi kìa", Toast.LENGTH_SHORT).show();
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