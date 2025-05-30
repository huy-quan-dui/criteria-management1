package com.example.quan_ly_chi_tieu.classes;

import java.io.Serializable;

public class HoaDonChi implements Serializable {
    private int ID;
    private String tenHoaDon;

    public HoaDonChi(int ID, String tenHoaDon) {
        this.ID = ID;
        this.tenHoaDon = tenHoaDon;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenHoaDon() {
        return tenHoaDon;
    }

    public void setTenHoaDon(String tenHoaDon) {
        this.tenHoaDon = tenHoaDon;
    }
}
