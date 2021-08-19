package com.example.superandroidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.superandroidproject.database.DbHelper;
import com.example.superandroidproject.model.HoaDonModel;

import java.util.ArrayList;

public class HoaDonDAO {
    // LOAD DỮ LIỆU TỪ BẢNG
    public static ArrayList<HoaDonModel> GetAll(Context context){
        ArrayList<HoaDonModel> list = new ArrayList<>();
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getReadableDatabase();
        // Câu lệnh truy vấn toàn bộ dữ liệu từ bảng TABLE_HOA_DON
        String sql = "select * from TABLE_HOA_DON";
        // Tạo con trỏ, trả về result set
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int maHoaDon = Integer.parseInt(cs.getString(0));
            String tenKhachHang = cs.getString(1);
            String ngayMua = cs.getString(2);

            HoaDonModel hoaDon = new HoaDonModel(maHoaDon, tenKhachHang, ngayMua);

            list.add(hoaDon);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    // THÊM HÓA ĐƠN
    public static boolean ThemHoaDon(HoaDonModel hd, Context context) {
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhachHang", hd.getKhachHang());
        values.put("ngayMua", hd.getNgayMua());
        return db.insert("TABLE_HOA_DON", null, values) > 0;
    }

    public static boolean CapNhatHoaDon(HoaDonModel hd,String tenMoi,String date, Context context){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhachHang", tenMoi);
        values.put("ngayMua", date);
        return db.update("TABLE_HOA_DON", values, "maHoaDon=? ", new String[]{String.valueOf(hd.getMaHoaDon())}) > 0;
    }

    public static boolean XoaHoaDon(int hd, Context context){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("TABLE_HOA_DON","maHoaDon=?",new String[]{String.valueOf(hd)});
        return row>0;
    }
}
