package com.example.superandroidproject.model;

public class LoaiSachModel {
    private String tenTheLoai, moTa;
    private int maTheLoai;
    private int viTri;

    public LoaiSachModel() {
    }

    public LoaiSachModel(int maTheLoai, String tenTheLoai, String moTa, int viTri) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.moTa = moTa;
        this.viTri = viTri;
    }

    public LoaiSachModel(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public LoaiSachModel(String tenTheLoai, String moTa, int viTri) {
        this.tenTheLoai = tenTheLoai;
        this.moTa = moTa;
        this.viTri = viTri;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public int getViTri() {
        return viTri;
    }

    @Override
    public String toString() {
        return maTheLoai + " | " + tenTheLoai;
    }

}
