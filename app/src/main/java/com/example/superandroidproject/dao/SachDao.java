package com.example.superandroidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.superandroidproject.database.DbHelper;
import com.example.superandroidproject.model.LoaiSachModel;
import com.example.superandroidproject.model.SachModel;

import java.util.ArrayList;

public class SachDao {
    // TODO: UPDATE THÔNG TIN SÁCH
    public static boolean updateSach(SachModel sachUpdate, Context context) {
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tieuDe", sachUpdate.getTieuDe());
        values.put("maTheLoai", sachUpdate.getMaTheLoai());
        values.put("tacGia", sachUpdate.getTacGia());
        values.put("nxb", sachUpdate.getNxb());
        values.put("soLuong", sachUpdate.getSoLuong());
        values.put("giaBan", sachUpdate.getGiaBan());

        int row = db.update("TABLE_SACH", values, "maSach=? ", new String[]{String.valueOf(sachUpdate.getMaSach())});
        return row > 0;
    }

    // LẤY DỮ LIỆU TỪ DATABASE
    public static ArrayList<SachModel> GetAll(Context context){
        ArrayList<SachModel> list_Sach = new ArrayList<>();
        DbHelper helper = new DbHelper(context);
        // Tạo CSDL SQLITE
        SQLiteDatabase db = helper.getReadableDatabase();
        // Câu lệnh truy vấn toàn bộ dữ liệu từ bảng TABLE_LOAI_SACH
        Cursor cs = db.rawQuery("select * from TABLE_SACH", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int maSach = cs.getInt(0);
            int maTheLoai = cs.getInt(1);
            String tieuDe = cs.getString(2);
            String tacGia = cs.getString(3);
            String nxb = cs.getString(4);
            int giaBan = cs.getInt(5);
            int soLuong = cs.getInt(6);
            SachModel sachModel = new SachModel(maSach, maTheLoai, tieuDe, tacGia, nxb, giaBan, soLuong);
            list_Sach.add(sachModel);
            cs.moveToNext();
        }
        cs.close();
        return list_Sach;
    }

    // THÊM SÁCH
    public static boolean ThemSach(SachModel s, Context context){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTheLoai", s.getMaTheLoai());
        values.put("tieuDe", s.getTieuDe());
        values.put("tacGia", s.getTacGia());
        values.put("nxb", s.getNxb());
        values.put("giaBan", s.getGiaBan());
        values.put("soLuong", s.getSoLuong());
        return db.insert("TABLE_SACH", null, values) > 0;
    }

    // XÓA LOẠI SÁCH
    public static boolean XoaSach(int maSach, Context context){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("TABLE_SACH","maSach=?",new String[]{String.valueOf(maSach)});
        return row>0;
    }
}
