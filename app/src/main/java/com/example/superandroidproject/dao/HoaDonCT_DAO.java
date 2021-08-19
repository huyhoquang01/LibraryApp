package com.example.superandroidproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.superandroidproject.database.DbHelper;
import com.example.superandroidproject.model.HoaDonChiTietModel;

import java.util.ArrayList;

public class HoaDonCT_DAO {
    public static ArrayList<HoaDonChiTietModel> GetHDCT(Context context, int maHD) {
        ArrayList<HoaDonChiTietModel> list = new ArrayList<>();
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        //                                     0              1                           2                          3
        Cursor cs = db.rawQuery("SELECT maHDCT, TABLE_HOA_DON.maHoaDon, TABLE_HOA_DON.tenKhachHang , TABLE_HOA_DON.ngayMua, " +
                //        4                    5                  6                   7                 8               9
                "TABLE_SACH.maSach, TABLE_SACH.maTheLoai, TABLE_SACH.tieuDe, TABLE_SACH.tacGia, TABLE_SACH.nxb, TABLE_SACH.giaBan, " +
                //       10                    11
                "TABLE_SACH.soLuong, TABLE_CHI_TIET_HOA_DON.soLuongCT FROM TABLE_CHI_TIET_HOA_DON INNER JOIN TABLE_HOA_DON " +
                "on TABLE_CHI_TIET_HOA_DON.maHoaDonCT = TABLE_HOA_DON.maHoaDon " +
                "INNER JOIN TABLE_SACH on TABLE_SACH.maSach = TABLE_CHI_TIET_HOA_DON.maSachCT" +
                " where TABLE_HOA_DON.maHoaDon = " + maHD, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int maHDCT = cs.getInt(0);
            int maHoaDon = cs.getInt(1);
            int maSach = cs.getInt(4);
            int soLuongCT = cs.getInt(11);
            int gia1Sach = cs.getInt(9);
            String tieuDe = cs.getString(6);
            HoaDonChiTietModel ct = new HoaDonChiTietModel(maHDCT, maHoaDon, maSach, soLuongCT, gia1Sach, tieuDe);
            list.add(ct);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    public static boolean ThemHDCT(HoaDonChiTietModel hdct, Context context) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHoaDonCT", hdct.getMaHD());
        values.put("maSachCT", hdct.getMaSach());
        values.put("soLuongCT", hdct.getSoLuong());
        return db.insert("TABLE_CHI_TIET_HOA_DON", null, values) > 0;
    }

    public static int gia1Sach(Context context, int maSach) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from TABLE_SACH where maSach='" + maSach + "'", null);
        cs.moveToFirst();
        int gia = cs.getInt(5);
        return gia;
    }

    public static int tinhTongTien(Context context, int maHD) {
        int tongTien = 0;
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT SUM(tongtien) from " +
                "(SELECT SUM(TABLE_SACH.giaBan * TABLE_CHI_TIET_HOA_DON.soLuongCT) as 'tongtien' " +
                "FROM TABLE_HOA_DON INNER JOIN TABLE_CHI_TIET_HOA_DON on TABLE_HOA_DON.maHoaDon = TABLE_CHI_TIET_HOA_DON.maHoaDonCT " +
                "INNER JOIN TABLE_SACH on TABLE_CHI_TIET_HOA_DON.maSachCT = TABLE_SACH.maSach" +
                " where TABLE_HOA_DON.maHoaDon = " + maHD + " )", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            tongTien = cs.getInt(0);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return tongTien;
    }

    public static int getDoanhThuTheoNgay(Context context) {
        int doanhThu = 0;
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(TABLE_SACH.giaBan * TABLE_CHI_TIET_HOA_DON.soLuongCT) as 'tongtien' " +
                "FROM TABLE_HOA_DON INNER JOIN TABLE_CHI_TIET_HOA_DON on TABLE_HOA_DON.maHoaDon = TABLE_CHI_TIET_HOA_DON.maHoaDonCT " +
                "INNER JOIN TABLE_SACH on TABLE_CHI_TIET_HOA_DON.maSachCT = TABLE_SACH.maSach where TABLE_HOA_DON.ngayMua = date('now') " +
                "GROUP BY TABLE_CHI_TIET_HOA_DON.maSachCT)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        db.close();
        return doanhThu;
    }

    public static int getDoanhThuTheoThang(Context context) {
        int doanhThu = 0;
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(TABLE_SACH.giaBan * TABLE_CHI_TIET_HOA_DON.soLuongCT)as 'tongtien' " +
                "FROM TABLE_HOA_DON INNER JOIN TABLE_CHI_TIET_HOA_DON on TABLE_HOA_DON.maHoaDon = TABLE_CHI_TIET_HOA_DON.maHoaDonCT " +
                "INNER JOIN TABLE_SACH on TABLE_CHI_TIET_HOA_DON.maSachCT = TABLE_SACH.maSach where strftime('%m',TABLE_HOA_DON.ngayMua) = strftime('%m','now') " +
                "GROUP BY TABLE_CHI_TIET_HOA_DON.maSachCT)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        db.close();
        return doanhThu;
    }

    public static boolean XoaHoaDonCT(int hdct, Context context){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("TABLE_CHI_TIET_HOA_DON","maHDCT=?",new String[]{String.valueOf(hdct)});
        return row>0;
    }
}
