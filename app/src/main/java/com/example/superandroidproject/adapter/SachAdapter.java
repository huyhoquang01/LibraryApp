package com.example.superandroidproject.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superandroidproject.R;
import com.example.superandroidproject.dao.LoaiSachDao;
import com.example.superandroidproject.dao.SachDao;
import com.example.superandroidproject.model.LoaiSachModel;
import com.example.superandroidproject.model.SachModel;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    private Context context;
    private ArrayList<SachModel> list_Sach;

    public SachAdapter(Context context, ArrayList<SachModel> list_Sach) {
        this.context = context;
        this.list_Sach = list_Sach;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, parent, false);
        return new SachViewHolder(view);
    }

    // HÀM SỬA SÁCH
    private  void DialogSua(final SachModel sach){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sua_sach, null);
        builder.setView(view);

        final Spinner spnThemMaTheLoaiSach = view.findViewById(R.id.spnSuaMaTheLoai);
        final EditText tenSach = view.findViewById(R.id.edtSuaTenSach);
        final EditText tacGia = view.findViewById(R.id.edtSuaTacGia);
        final EditText nxb = view.findViewById(R.id.edtSuaNXB);
        final EditText giaBan = view.findViewById(R.id.edtSuaGiaBan);
        final EditText soLuong = view.findViewById(R.id.edtSuaSoLuong);
        Button btnHuy = view.findViewById(R.id.btnHuySuaSach);
        Button btnSuaSach = view.findViewById(R.id.btnSuaSach);

        // Hiện dữ liệu có sẵn lên dialog
        tenSach.setText(sach.getTieuDe());
        tacGia.setText(sach.getTacGia());
        nxb.setText(String.valueOf(sach.getNxb()));
        giaBan.setText(String.valueOf(sach.getGiaBan()));
        soLuong.setText(String.valueOf(sach.getSoLuong()));

        final Dialog dialog = builder.create();
        dialog.show();


        ArrayList<LoaiSachModel> theLoaiSach = LoaiSachDao.GetAll(context);
        ArrayAdapter spnAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, theLoaiSach);
        spnThemMaTheLoaiSach.setAdapter(spnAdapter);
        spnThemMaTheLoaiSach.setSelection(sach.getMaTheLoai() - 1);

        // BUTTON SỬA SACH
        btnSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSachMoi = tenSach.getText().toString();
                String tacGiaMoi = tacGia.getText().toString();
                String nxbMoi = nxb.getText().toString();
                int giaBanMoi = Integer.parseInt(giaBan.getText().toString());
                int soLuongMoi = Integer.parseInt(soLuong.getText().toString());

                LoaiSachModel loaiSachModel = (LoaiSachModel) spnThemMaTheLoaiSach.getSelectedItem();
                int maTheLoai = loaiSachModel.getMaTheLoai();

                SachModel sachUpdate = new SachModel(tenSachMoi, maTheLoai, tacGiaMoi, nxbMoi, giaBanMoi, soLuongMoi);

                if(SachDao.updateSach(sachUpdate, context)){
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    list_Sach.clear();
                    list_Sach.addAll(SachDao.GetAll(context));
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
    public void onBindViewHolder(@NonNull SachViewHolder holder, final int position) {
        holder.tvTenSach.setText(list_Sach.get(position).getTieuDe());
        holder.tvTacGia.setText(list_Sach.get(position).getTacGia());
        holder.tvNXB.setText(list_Sach.get(position).getNxb());
        holder.tvSoLuong.setText(String.valueOf(list_Sach.get(position).getSoLuong()));
        holder.tvGiaBan.setText(String.valueOf(list_Sach.get(position).getGiaBan()));
        holder.ivAvatarSach.setImageResource(R.drawable.inspiration);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSua(list_Sach.get(position));
            }
        });

        holder.ivXoaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Sách");
                builder.setMessage("Suy nghĩ kĩ đi!! Có chắc muốn xóa?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (SachDao.XoaSach(list_Sach.get(position).getMaSach(), context)){
                                    Toast.makeText(context, "Đã xóa! Tối ngủ tốt.", Toast.LENGTH_SHORT).show();
                                    list_Sach.remove(position);
                                    list_Sach.clear();
                                    list_Sach.addAll(SachDao.GetAll(context));
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
        return list_Sach.size();
    }

    public static class SachViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        ImageView ivAvatarSach, ivXoaSach;
        TextView tvTenSach, tvTacGia, tvNXB, tvGiaBan, tvSoLuong;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemSach);
            ivAvatarSach = itemView.findViewById(R.id.ivAvatarSach);
            ivXoaSach = itemView.findViewById(R.id.ivXoaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            tvGiaBan = itemView.findViewById(R.id.tvGiaSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvNXB = itemView.findViewById(R.id.tvNXB);
        }
    }
}

// TODO: mã thể loại sách tự tăng ko thay thế
// TODO: load mã thể loại của sách lên spinner để sửa sách
