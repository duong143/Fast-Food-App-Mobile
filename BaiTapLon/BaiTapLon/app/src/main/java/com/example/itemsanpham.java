package com.example;

import java.io.Serializable;

public class itemsanpham implements Serializable {
    private String ten;
    private double gia;
    private int soluong;
    private String anh;

    public itemsanpham(String ten, double gia, int soluong, String anh) {
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public double getGia() {
        return gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public String getAnh() {
        return anh;
    }
    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}

