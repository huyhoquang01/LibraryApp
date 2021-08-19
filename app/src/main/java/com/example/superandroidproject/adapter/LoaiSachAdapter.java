package com.example.superandroidproject.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superandroidproject.R;
import com.example.superandroidproject.dao.LoaiSachDao;
import com.example.superandroidproject.model.LoaiSachModel;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    private Context context;
    private ArrayList<LoaiSachModel> list_LoaiSach;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSachModel> list_LoaiSach) {
        this.context = context;
        this.list_LoaiSach = list_LoaiSach;
    }

    @NonNull
    @Override
    public LoaiSachAdapter.LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_sach, parent, false);
        return new LoaiSachViewHolder(view);
    }
    // HÀM SỬA LOẠI SÁCH
    private  void DialogSua(final LoaiSachModel ls){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sua_loai_sach, null);
        builder.setView(view);

        final EditText tenLoaiSach = view.findViewById(R.id.edtSuaTenLoaiSach);
        final EditText moTa = view.findViewById(R.id.edtSuaMoTa);
        final EditText viTri = view.findViewById(R.id.edtSuaViTri);
        Button btnHuy = view.findViewById(R.id.btnHuySuaLoaiSach);
        Button btnThem = view.findViewById(R.id.btnSuaLoaiSach);

        tenLoaiSach.setText(ls.getTenTheLoai());
        moTa.setText(ls.getMoTa());
        viTri.setText(String.valueOf(ls.getViTri()));

        final Dialog dialog = builder.create();
        dialog.show();

        // BUTTON SỬA
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiSachMoi = tenLoaiSach.getText().toString();
                String moTaMoi = moTa.getText().toString();
                int viTriMoi = Integer.parseInt(viTri.getText().toString());

                if(LoaiSachDao.updateLoaiSach(ls, tenLoaiSachMoi, moTaMoi, viTriMoi, context)){
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    list_LoaiSach.clear();
                    list_LoaiSach.addAll(LoaiSachDao.GetAll(context));
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TẮT DIALOG THÊM
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.LoaiSachViewHolder holder, final int position) {
        holder.tvViTri.setText(String.valueOf(list_LoaiSach.get(position).getViTri()));
        holder.tvTenLoaiSach.setText(list_LoaiSach.get(position).getTenTheLoai());
        holder.tvMaTheLoai.setText(String.valueOf(list_LoaiSach.get(position).getMaTheLoai()));
        holder.tvMoTa.setText(list_LoaiSach.get(position).getMoTa());
        holder.ivAvatarLoaiSach.setImageResource(R.drawable.skull);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSua(list_LoaiSach.get(position));
            }
        });
        holder.ivXoaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Loại Sách");
                builder.setMessage("Suy nghĩ kĩ đi!! Có chắc muốn xóa?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (LoaiSachDao.XoaLoaiSach(list_LoaiSach.get(position).getMaTheLoai(), context)){
                                    Toast.makeText(context, "Đã xóa! Tối ngủ cẩn thận.", Toast.LENGTH_SHORT).show();
                                    list_LoaiSach.remove(position);
                                    list_LoaiSach.clear();
                                    list_LoaiSach.addAll(LoaiSachDao.GetAll(context));
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Nước đi hay đấy", Toast.LENGTH_SHORT).show();
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
        return list_LoaiSach.size();
    }

    public static class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        ImageView ivAvatarLoaiSach, ivXoaLoaiSach;
        TextView tvTenLoaiSach, tvViTri, tvMaTheLoai, tvMoTa;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.cardView_LoaiSach);
            ivAvatarLoaiSach = itemView.findViewById(R.id.ivAvatarLoaiSach);
            ivXoaLoaiSach = itemView.findViewById(R.id.ivXoaLoaiSach);
            tvTenLoaiSach = itemView.findViewById(R.id.tvTenLoaiSach);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            tvMaTheLoai = itemView.findViewById(R.id.tvMaTheLoai);
            tvViTri = itemView.findViewById(R.id.tvViTri);
        }
    }
}
