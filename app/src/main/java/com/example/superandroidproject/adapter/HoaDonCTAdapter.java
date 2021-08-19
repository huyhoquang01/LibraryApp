package com.example.superandroidproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superandroidproject.QuanLi.QuanLiHDCTActivity;
import com.example.superandroidproject.R;
import com.example.superandroidproject.dao.HoaDonCT_DAO;
import com.example.superandroidproject.dao.HoaDonDAO;
import com.example.superandroidproject.model.HoaDonChiTietModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonCTAdapter extends RecyclerView.Adapter<HoaDonCTAdapter.CTViewHolder> {
    Context context;
    ArrayList<HoaDonChiTietModel> list;
    public static double thanhTien2 = 0;
    public HoaDonCTAdapter(Context context, ArrayList<HoaDonChiTietModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HoaDonCTAdapter.CTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hdct, parent, false);
        return new HoaDonCTAdapter.CTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CTViewHolder holder, final int position) {
        holder.tvMaHDCT.setText(String.valueOf(list.get(position).getMaHoaDonCT()));
        holder.tvMaSachCT.setText(list.get(position).getTieuDe());
        holder.tvSoLuongCT.setText(String.valueOf(list.get(position).getSoLuong()));

        int sl = Integer.parseInt(holder.tvSoLuongCT.getText().toString());
        holder.tvThanhTien.setText(String.valueOf(list.get(position).getGia1Sach()*sl));

        holder.ivXoaHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaHDCT(position, list.get(position).getMaHD());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void XoaHDCT(final int position, final int maHD){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa Hóa đơn Chi tiết");
        builder.setMessage("Suy nghĩ kĩ đi!! Có chắc muốn xóa?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (HoaDonCT_DAO.XoaHoaDonCT(list.get(position).getMaHoaDonCT(), context)){
                            Toast.makeText(context, "Đã xóa! Uống tí nước đi.", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            list.clear();
                            list.addAll(HoaDonCT_DAO.GetHDCT(context, maHD));
                            for (int j = 0; j < list.size(); j++) {
                                thanhTien2 = thanhTien2 + list.get(j).getGia1Sach() * list.get(j).getSoLuong();
                            }
                            Log.i("tt2", thanhTien2 + " adapter");
                            Intent intent = new Intent(context, QuanLiHDCTActivity.class);
                            intent.putExtra("t2", thanhTien2);
//                            context.startActivity(intent);
//                            Bundle bundle = new Bundle();
//                            bundle.putDouble("tt2", thanhTien2);
//                            QuanLiHDCTActivity hdctActivity = new QuanLiHDCTActivity();
//                            hdctActivity.setIntent(intent);
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
    public class CTViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHDCT, tvMaSachCT, tvSoLuongCT, tvThanhTien, tvThoiGian;
        ImageView ivXoaHDCT;
        public CTViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHDCT = itemView.findViewById(R.id.tvMaHDCT);
            tvMaSachCT = itemView.findViewById(R.id.tvMaSachCT);
            tvSoLuongCT = itemView.findViewById(R.id.tvSoLuongCT);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGianMua);
            ivXoaHDCT = itemView.findViewById(R.id.ivXoaHDCT);
        }
    }
}
