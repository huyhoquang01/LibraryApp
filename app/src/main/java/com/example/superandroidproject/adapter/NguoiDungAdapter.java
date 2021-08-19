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
import com.example.superandroidproject.dao.NguoiDungDao;
import com.example.superandroidproject.model.NguoiDungModel;

import java.util.ArrayList;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.NguoiDungViewHolder> {
    private Context context;
    private ArrayList<NguoiDungModel> list_NguoiDung;

    public NguoiDungAdapter(Context context, ArrayList<NguoiDungModel> list_NguoiDung) {
        this.context = context;
        this.list_NguoiDung = list_NguoiDung;
    }

    @NonNull
    @Override
    public NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_nguoi_dung, parent, false);
        return new NguoiDungViewHolder(view);
    }


    // DIALOG SỬA NGƯỜI DÙNG
    private void DialogSua(final NguoiDungModel nd) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sua_nguoi_dung, null);
        builder.setView(view);

        EditText username = view.findViewById(R.id.edtSuaTenDangNhap);
        final EditText ten = view.findViewById(R.id.edtSuaHoTen);
        final EditText soDienThoai = view.findViewById(R.id.edtSuaSoDienThoai);
        Button btnHuy = view.findViewById(R.id.btnHuySuaNguoiDung);
        Button btnSuaNguoiDung = view.findViewById(R.id.btnSuaNguoiDung);

        username.setText(nd.getUserName());
        ten.setText(nd.getTenNguoiDung());
        soDienThoai.setText(nd.getSoDienThoai());

        final Dialog dialog = builder.create();
        dialog.show();

        // BUTTON SỬA
        btnSuaNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTenMoi = ten.getText().toString();
                String soDienThoaiMoi = soDienThoai.getText().toString();

                if (NguoiDungDao.updateNguoiDung(nd, hoTenMoi, soDienThoaiMoi, context)) {
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    list_NguoiDung.clear();
                    list_NguoiDung.addAll(NguoiDungDao.GetAll(context));
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TẮT DIALOG SỬA
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungViewHolder holder, final int position) {
        holder.txtTenNguoiDung.setText(list_NguoiDung.get(position).getTenNguoiDung());
        holder.txtSoDienThoai.setText(list_NguoiDung.get(position).getSoDienThoai());
        holder.ivAvatarNguoiDung.setImageResource(R.drawable.emone);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSua(list_NguoiDung.get(position));
            }
        });
        holder.ivXoaNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Người Dùng");
                builder.setMessage("Suy nghĩ kĩ đi!! Có chắc muốn xóa?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (NguoiDungDao.XoaNguoiDung(list_NguoiDung.get(position).getUserName(), context)){
                                    Toast.makeText(context, "Đã xóa! Tối ngủ cẩn thận.", Toast.LENGTH_SHORT).show();
                                    list_NguoiDung.remove(position);
                                    list_NguoiDung.clear();
                                    list_NguoiDung.addAll(NguoiDungDao.GetAll(context));
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
        return list_NguoiDung.size();
    }

    public class NguoiDungViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        ImageView ivAvatarNguoiDung, ivXoaNguoiDung;
        TextView txtTenNguoiDung, txtSoDienThoai;

        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemNguoiDung);
            ivAvatarNguoiDung = itemView.findViewById(R.id.ivAvatarNguoiDung);
            ivXoaNguoiDung = itemView.findViewById(R.id.ivXoaNguoiDung);
            txtTenNguoiDung = itemView.findViewById(R.id.txtTenNguoiDung);
            txtSoDienThoai = itemView.findViewById(R.id.txtSoDienThoai);
        }
    }
}
