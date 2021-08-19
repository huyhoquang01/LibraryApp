package com.example.superandroidproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLBanSach.db";
    private static final int DATABASE_VERSION = 29;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TẠO BẢNG NGƯỜI DÙNG
        db.execSQL("create table TABLE_NGUOI_DUNG(" +
                "userName text primary key, " +
                "password text, " +
                "hoTen text not null, " +
                "soDienThoai text)");
        db.execSQL("insert into TABLE_NGUOI_DUNG(userName, password, hoTen, soDienThoai) values('admin', '123456', 'Nguyen Van A', '')");
        db.execSQL("insert into TABLE_NGUOI_DUNG(userName, password, hoTen, soDienThoai) values('admin2', '1234567', 'Nguyen Van B', '0123456789')");

        // TẠO BẢNG LOẠI SÁCH
        db.execSQL("create table TABLE_LOAI_SACH(" +
                "maTheLoai integer primary key autoincrement, " +
                "tenTheLoai text not null, " +
                "moTa text, " +
                "viTri integer)");
        db.execSQL("insert into TABLE_LOAI_SACH(maTheLoai, tenTheLoai, moTa, viTri) values(null,'Sách Kinh Tế', null, 1)");
        db.execSQL("insert into TABLE_LOAI_SACH(maTheLoai, tenTheLoai, moTa, viTri) values(null,'Sách Xây Dựng', null, 2)");

        // TẠO BẢNG SÁCH
        db.execSQL("create table TABLE_SACH(" +
                "maSach integer primary key autoincrement, " +
                "maTheLoai integer references TABLE_LOAI_SACH(maTheLoai), " +
                "tieuDe text not null, " +
                "tacGia text, " +
                "nxb text, " +
                "giaBan integer, " +
                "soLuong integer)");
        db.execSQL("insert into TABLE_SACH(maSach, maTheLoai, tieuDe, tacGia, nxb, giaBan, soLuong) values(null, 1,'Không Làm Mà Vẫn Có Ăn','Hoa Hong','Dong Lao', 110000, 1)");
        db.execSQL("insert into TABLE_SACH(maSach, maTheLoai, tieuDe, tacGia, nxb, giaBan, soLuong) values(null, 2,'Học Làm Fuho','Loc Fuho','Dong Lao', 15000, 10)");

        // TẠO BẢNG HÓA ĐƠN
        db.execSQL("create table TABLE_HOA_DON(" +
                "maHoaDon integer primary key autoincrement, " +
                "tenKhachHang text, " +
                "ngayMua Date)");
        db.execSQL("insert into TABLE_HOA_DON values (null, 'huy nghiem tuc', '20-10-2020')");
        db.execSQL("insert into TABLE_HOA_DON values (null, 'huy kho hieu', '08-10-2020')");

        // TẠO BẢNG CHI TIẾT HÓA ĐƠN
        db.execSQL("create table TABLE_CHI_TIET_HOA_DON(" +
                "maHDCT integer primary key autoincrement, " +
                "maHoaDonCT integer references TABLE_HOA_DON(maHoaDon), " +
                "maSachCT integer references TABLE_SACH(maSach), " +
                "soLuongCT integer)");
        db.execSQL("insert into TABLE_CHI_TIET_HOA_DON values(null, 1, 1, 2)");
        db.execSQL("insert into TABLE_CHI_TIET_HOA_DON values(null, 2, 2, 1)");
        db.execSQL("insert into TABLE_CHI_TIET_HOA_DON values(null, 2, 2, 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NGUOI_DUNG");
        db.execSQL("DROP TABLE IF EXISTS TABLE_LOAI_SACH");
        db.execSQL("DROP TABLE IF EXISTS TABLE_SACH");
        db.execSQL("DROP TABLE IF EXISTS TABLE_HOA_DON");
        db.execSQL("DROP TABLE IF EXISTS TABLE_CHI_TIET_HOA_DON");
        onCreate(db);
    }
}
