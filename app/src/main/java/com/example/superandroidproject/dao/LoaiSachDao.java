package com.example.superandroidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.superandroidproject.database.DbHelper;
import com.example.superandroidproject.model.LoaiSachModel;
import com.example.superandroidproject.model.NguoiDungModel;

import java.util.ArrayList;

public class LoaiSachDao {
    // TODO: UPDATE THÔNG TIN LOẠI SÁCH
    public static boolean updateLoaiSach(LoaiSachModel ls, String tenLoaiSachMoi, String moTaMoi, int viTriMoi, Context context){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTheLoai", tenLoaiSachMoi);
        values.put("moTa", moTaMoi);
        values.put("viTri", viTriMoi);
        int row = db.update("TABLE_LOAI_SACH",values,"maTheLoai=? ", new String[]{String.valueOf(ls.getMaTheLoai())});
        return row>0;

    }
    public static ArrayList<LoaiSachModel> GetAll(Context context){
        ArrayList<LoaiSachModel> listLoaiSach = new ArrayList<>();
        DbHelper helper = new DbHelper(context);
        // TẠO CƠ SỞ DL SQL
        SQLiteDatabase db = helper.getReadableDatabase();
        // Câu lệnh truy vấn toàn bộ dữ liệu từ bảng TABLE_LOAI_SACH
        String sql = "select * from TABLE_LOAI_SACH";
        // Tạo con trỏ, trả về result set
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int maTheLoai = cs.getInt(0);
            String tenTheLoai = cs.getString(1);
            String moTa = cs.getString(2);
            int viTri = cs.getInt(3);

            LoaiSachModel ls = new LoaiSachModel(maTheLoai, tenTheLoai, moTa, viTri);
            listLoaiSach.add(ls);
            cs.moveToNext();
        }
        cs.close();
        return listLoaiSach;
    }
    // THÊM LOẠI SÁCH
    public static boolean ThemLoaiSach(LoaiSachModel ls, Context context){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTheLoai", ls.getTenTheLoai());
        values.put("moTa", ls.getMoTa());
        values.put("viTri", ls.getViTri());
        return db.insert("TABLE_LOAI_SACH", null, values) > 0;
    }

    // XÓA LOẠI SÁCH
    public static boolean XoaLoaiSach(int maTheLoai, Context context){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("TABLE_LOAI_SACH","maTheLoai=?",new String[]{String.valueOf(maTheLoai)});
        return row>0;
    }
}
