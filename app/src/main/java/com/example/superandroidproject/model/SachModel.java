package com.example.superandroidproject.model;

public class SachModel {
    private String tieuDe, tacGia, nxb;
    private int maSach, maTheLoai;
    private int giaBan, soLuong;

    public SachModel() {
    }

    public SachModel(int maSach, int maTheLoai, String tieuDe, String tacGia, String nxb, int giaBan, int soLuong) {
        this.maSach = maSach;
        this.maTheLoai = maTheLoai;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public SachModel(String tieuDe, int maTheLoai, String tacGia, String nxb, int giaBan, int soLuong) {
        this.tieuDe = tieuDe;
        this.maTheLoai = maTheLoai;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public int getMaSach() {
        return maSach;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public String getNxb() {
        return nxb;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public String toString() {
        return maSach + " - " + tieuDe;
    }
}
