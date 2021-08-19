package com.example.superandroidproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superandroidproject.QuanLi.QuanLiHDCTActivity;
import com.example.superandroidproject.R;
import com.example.superandroidproject.dao.HoaDonCT_DAO;
import com.example.superandroidproject.dao.HoaDonDAO;
import com.example.superandroidproject.model.HoaDonModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {
    private Context context;
    private ArrayList<HoaDonModel> list_HoaDon;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public HoaDonAdapter(Context context, ArrayList<HoaDonModel> list_HoaDon) {
        this.context = context;
        this.list_HoaDon = list_HoaDon;
    }

    @NonNull
    @Override
    public HoaDonAdapter.HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoa_don, parent, false);
        return new HoaDonAdapter.HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.HoaDonViewHolder holder, final int position) {
        holder.txtTenKhacHang.setText(list_HoaDon.get(position).getKhachHang());
        holder.txtNgayMua.setText(list_HoaDon.get(position).getNgayMua());
        holder.ivAvatarhoaDon.setImageResource(R.drawable.emtwo);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, QuanLiHDCTActivity.class);
                double tongtien = HoaDonCT_DAO.tinhTongTien(context, list_HoaDon.get(position).getMaHoaDon());
                i.putExtra("hdct", list_HoaDon.get(position));
                i.putExtra("thanhtien", tongtien);
                context.startActivity(i);
            }
        });
        holder.ivXoaHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Hóa đơn");
                builder.setMessage("Suy nghĩ kĩ đi!! Có chắc muốn xóa?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (HoaDonDAO.XoaHoaDon(list_HoaDon.get(position).getMaHoaDon(), context)){
                                    Toast.makeText(context, "Đã xóa! Uống tí nước đi.", Toast.LENGTH_SHORT).show();
                                    list_HoaDon.remove(position);
                                    list_HoaDon.clear();
                                    list_HoaDon.addAll(HoaDonDAO.GetAll(context));
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "OK Fine", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_HoaDon.size();
    }


    public static class HoaDonViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        ImageView ivAvatarhoaDon, ivXoaHoaDon;
        TextView txtTenKhacHang, txtNgayMua;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemHoaDon);
            ivAvatarhoaDon = itemView.findViewById(R.id.ivAvatarHoaDon);
            ivXoaHoaDon = itemView.findViewById(R.id.ivXoaHoaDon);
            txtTenKhacHang = itemView.findViewById(R.id.txtTenKhachHang);
            txtNgayMua = itemView.findViewById(R.id.txtNgayMua);
        }
    }
}
