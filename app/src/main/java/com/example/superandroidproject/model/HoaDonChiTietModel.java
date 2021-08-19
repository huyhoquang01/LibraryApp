package com.example.superandroidproject.model;

public class HoaDonChiTietModel {
    private int maHoaDonCT, maHD, maSach, soLuong, gia1Sach;
    private String tieuDe;

    public int getGia1Sach() {
        return gia1Sach;
    }


    public HoaDonChiTietModel(int maHD, int maSach, int soLuong) {
        this.maHD = maHD;
        this.maSach = maSach;
        this.soLuong = soLuong;
    }

    public void setGia1Sach(int gia1Sach) {
        this.gia1Sach = gia1Sach;
    }

    public HoaDonChiTietModel() {
    }

    public HoaDonChiTietModel(int maHoaDonCT, int maHD, int maSach, int soLuong, int gia1Sach, String tieuDe) {
        this.maHoaDonCT = maHoaDonCT;
        this.maHD = maHD;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.gia1Sach = gia1Sach;
        this.tieuDe = tieuDe;
    }


    public int getMaHoaDonCT() {
        return maHoaDonCT;
    }

    public int getMaHD() {
        return maHD;
    }

    public int getMaSach() {
        return maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getTieuDe() {
        return tieuDe;
    }
}
