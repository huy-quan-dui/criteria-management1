package com.example.quan_ly_chi_tieu.classes;

public class BanGhiThu {
    private int STT,ID;
    private float Money;
    private String Ngay,Note;

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getMoney() {
        return Money;
    }

    public void setMoney(float money) {
        Money = money;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public BanGhiThu(int STT, String ngay, int ID, float money, String note) {
        this.STT = STT;
        this.ID = ID;
        this.Money = money;
        this.Ngay = ngay;
        this.Note = note;
    }
}
