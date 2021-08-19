package com.example.superandroidproject.model;


import android.os.Parcelable;

import java.io.Serializable;

public class HoaDonModel implements Serializable {
    private int maHoaDon;
    private String khachHang;
    private String ngayMua;

    public HoaDonModel() {
    }

    public HoaDonModel(String khachHang, String ngayMua) {
        this.khachHang = khachHang;
        this.ngayMua = ngayMua;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public HoaDonModel(int maHoaDon, String khachHang, String ngayMua) {
        this.maHoaDon = maHoaDon;
        this.khachHang = khachHang;
        this.ngayMua = ngayMua;
    }

    @Override
    public String toString() {
        return maHoaDon + " - " + khachHang;
    }
}
