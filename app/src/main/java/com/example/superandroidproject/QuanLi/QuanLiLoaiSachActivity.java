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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superandroidproject.R;
import com.example.superandroidproject.adapter.LoaiSachAdapter;
import com.example.superandroidproject.dao.LoaiSachDao;
import com.example.superandroidproject.model.LoaiSachModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLiLoaiSachActivity extends AppCompatActivity {
    RecyclerView rcvLoaiSach;
    FloatingActionButton fabThemLoaiSach;
    ArrayList<LoaiSachModel> list_LoaiSach = new ArrayList<>();
    LoaiSachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_loai_sach);

        fabThemLoaiSach = findViewById(R.id.fab_ThemLoaiSach);
        rcvLoaiSach = findViewById(R.id.rcvLoaiSach);
        rcvLoaiSach.setHasFixedSize(true);

        // ADD Recycler Riew vào giao diện
        RecyclerView.LayoutManager manager = new LinearLayoutManager(QuanLiLoaiSachActivity.this);
        rcvLoaiSach.setLayoutManager(manager);

        list_LoaiSach = LoaiSachDao.GetAll(QuanLiLoaiSachActivity.this);
        adapter = new LoaiSachAdapter(QuanLiLoaiSachActivity.this, list_LoaiSach);
        rcvLoaiSach.setAdapter(adapter);

        fabThemLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem(QuanLiLoaiSachActivity.this);
            }
        });
    }

    // DIALOG THÊM LOẠI SÁCH
    private void DialogThem(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuanLiLoaiSachActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_loai_sach, null);
        builder.setView(view);

        final Dialog dialog = builder.create();
        dialog.show();
        // ========================================
        Button btnThemLoaiSach = view.findViewById(R.id.btnThemLoaiSach);
        final EditText edtTenTheLoai = view.findViewById(R.id.edtThemTenTheLoai);
        final EditText edtViTri = view.findViewById(R.id.edtThemViTri);
        final EditText edtThemMoTa = view.findViewById(R.id.edtThemMoTa);
        final Button btnHuy = view.findViewById(R.id.btnHuyThemLoaiSach);

        btnThemLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTheLoaiMoi = edtTenTheLoai.getText().toString();
                int viTriMoi = Integer.parseInt(edtViTri.getText().toString());
                String moTaMoi = edtThemMoTa.getText().toString();

                LoaiSachModel loaiSachMoi = new LoaiSachModel(tenTheLoaiMoi,moTaMoi,viTriMoi);

                if (LoaiSachDao.ThemLoaiSach(loaiSachMoi, context)) {
                    Toast.makeText(context, "Thêm hoàn tất. Cày view Sếp thôi", Toast.LENGTH_SHORT).show();
                    list_LoaiSach.clear();
                    list_LoaiSach.addAll(LoaiSachDao.GetAll(context));
                    adapter.notifyDataSetChanged();
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