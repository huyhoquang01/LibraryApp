package com.example.superandroidproject.QuanLi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superandroidproject.R;
import com.example.superandroidproject.adapter.HoaDonAdapter;
import com.example.superandroidproject.dao.HoaDonDAO;
import com.example.superandroidproject.model.HoaDonModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLiHoaDonActivity extends AppCompatActivity {
    RecyclerView rcvHoaDon;
    FloatingActionButton fabThemHoaDon;
    HoaDonAdapter adapter;
    ArrayList<HoaDonModel> list_HoaDon = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_hoa_don);

        rcvHoaDon = findViewById(R.id.rcvHoaDon);
//        rcvHoaDon.setHasFixedSize(true);
        fabThemHoaDon = findViewById(R.id.fab_ThemHoaDon);

        // GÁN DỮ LIỆU VÀO RECYCLERVIEW
        RecyclerView.LayoutManager manager = new LinearLayoutManager(QuanLiHoaDonActivity.this);
        rcvHoaDon.setLayoutManager(manager);

        list_HoaDon = HoaDonDAO.GetAll(QuanLiHoaDonActivity.this);
        adapter = new HoaDonAdapter(QuanLiHoaDonActivity.this, list_HoaDon);
        rcvHoaDon.setAdapter(adapter);

        // BUTTON THÊM HÓA ĐƠN
        fabThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem(QuanLiHoaDonActivity.this);
            }
        });
    }

    public void DialogThem(final Context context) {
        // todo: hiện dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_them_hoa_don, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();
        // ========================================
        Button btnThemHoaDon = view.findViewById(R.id.btnThemHoaDon);
        final EditText edtTenKhachHang = view.findViewById(R.id.edtThemTenKhachHang);
        final Button btnHuy = view.findViewById(R.id.btnHuyThemHoaDon);

        // < ========== NHẬP NGÀY ==============
        final EditText edtNgayMua = view.findViewById(R.id.edtThemNgayMua);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                edtNgayMua.setText(format.format(myCalendar.getTime()));
            }
        };
        edtNgayMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(QuanLiHoaDonActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // ========== HẾT ĐOẠN NHẬP NGÀY ============== />

        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenKhachHang = edtTenKhachHang.getText().toString();
                String date = edtNgayMua.getText().toString();

                    HoaDonModel hoaDon = new HoaDonModel(tenKhachHang, date);

                    if (edtTenKhachHang.getText().toString().equals("") || edtNgayMua.getText().toString().equals("")) {
                        Toast.makeText(context, "Điền đầy đủ nào ", Toast.LENGTH_SHORT).show();
                    } else {
                        if (HoaDonDAO.ThemHoaDon(hoaDon, context)) {
                            Toast.makeText(context, "Thêm hoàn tất. Cày view Sếp thôi", Toast.LENGTH_SHORT).show();
                            list_HoaDon.clear();

                            list_HoaDon.addAll(HoaDonDAO.GetAll(context));
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