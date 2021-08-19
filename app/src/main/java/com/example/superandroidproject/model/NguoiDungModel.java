package com.example.superandroidproject.model;

public class NguoiDungModel {
    private String userName;
    private String password;
    private String tenNguoiDung;
    private String soDienThoai;

    public NguoiDungModel() {
    }

    public NguoiDungModel(String taiKhoan, String matKhau, String hoTen, String soDienThoai) {
        this.userName = taiKhoan;
        this.tenNguoiDung = hoTen;
        this.soDienThoai = soDienThoai;
        this.password = matKhau;
    }

    public NguoiDungModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getUserName() {
        return userName;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getPassword() {
        return password;
    }
}
