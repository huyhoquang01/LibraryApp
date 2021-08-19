package com.example.superandroidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.superandroidproject.LoginActivity;
import com.example.superandroidproject.database.DbHelper;
import com.example.superandroidproject.model.NguoiDungModel;

import java.util.ArrayList;

public class NguoiDungDao {
    // TODO: UPDATE THÔNG TIN NGƯỜI DÙNG
    public static boolean updateNguoiDung(NguoiDungModel nd, String tenMoi, String soDienThoaiMoi, Context context){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", tenMoi);
        values.put("soDienThoai", soDienThoaiMoi);
        int row = db.update("TABLE_NGUOI_DUNG",values,"userName=? ", new String[]{nd.getUserName()});
        return row>0;

    }
    public static ArrayList<NguoiDungModel> GetAll(Context context){
        ArrayList<NguoiDungModel> list = new ArrayList<>();
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getReadableDatabase();
        // Câu lệnh truy vấn toàn bộ dữ liệu từ bảng TABLE_NGUOI_DUNG
        String sql = "select * from TABLE_NGUOI_DUNG";
        // Tạo con trỏ, trả về result set
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            String userName = cs.getString(0);
            String password = cs.getString(1);
            String hoTen = cs.getString(2);
            String soDienThoai = cs.getString(3);
            NguoiDungModel nd = new NguoiDungModel(userName, password, hoTen, soDienThoai);

            list.add(nd);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    // Dùng boolean check login
    public static boolean checkNguoiDung(Context context, String username, String password){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getReadableDatabase();
        // Câu lệnh truy vấn toàn bộ dữ liệu từ bảng TABLE_NGUOI_DUNG
        String sql = "select * from TABLE_NGUOI_DUNG where userName like '" + username + "' and password like '" + password + "'";
        // Tạo con trỏ, trả về result set
        Cursor cs = db.rawQuery(sql, null);
        if (cs.getCount() <= 0){
            return false;
        }
        cs.close();
        return true;
    }

    // Dùng NguoiDungModel để check login
//    public static NguoiDungModel checkNguoiDung(Context context, String username, String password){
//        DbHelper helper = new DbHelper(context);
//        // TẠO CSDL SQLITE
//        SQLiteDatabase db = helper.getReadableDatabase();
//        // Câu lệnh truy vấn toàn bộ dữ liệu từ bảng TABLE_NGUOI_DUNG
//        String sql = "select * from TABLE_NGUOI_DUNG where userName like '" + username + "' and password like '" + password + "'";
//        // Tạo con trỏ, trả về result set
//        Cursor cs = db.rawQuery(sql, null);
//        if (cs.getCount() <= 0){
//            return false;
//        }
//        cs.close();
//        return true;
//    }

    public static boolean CheckPassCu(String matKhauCu){
        String pass = LoginActivity.USER.getPassword();
        return matKhauCu.equals(pass);
    }

    public static boolean ChangePassword(NguoiDungModel nd, Context context){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", nd.getPassword());
        values.put("userName", nd.getUserName());

        int row = db.update("TABLE_NGUOI_DUNG",values," userName = ? ", new String[]{nd.getUserName()});
        return row>0;
    }

    // todo: thêm xóa sửa người dùng

    public static boolean ThemNguoiDung(NguoiDungModel nd, Context context){
        DbHelper helper = new DbHelper(context);
        // TẠO CSDL SQLITE
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", nd.getUserName());
        values.put("password", nd.getPassword());
        values.put("hoTen", nd.getTenNguoiDung());
        values.put("soDienThoai", nd.getSoDienThoai());
        return db.insert("TABLE_NGUOI_DUNG", null, values) > 0;
    }

    // XÓA NGƯỜI DÙNG
    public static boolean XoaNguoiDung(String un, Context context){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("TABLE_NGUOI_DUNG","userName=?",new String[]{String.valueOf(un)});
        return row>0;
    }
}
