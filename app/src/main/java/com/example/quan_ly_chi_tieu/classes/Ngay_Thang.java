package com.example.quan_ly_chi_tieu.classes;

public class Ngay_Thang {
    private int ngay,thang;

    public Ngay_Thang(int thang) {
        this.thang = thang;
        switch (thang)
        {
            case 1:
                this.ngay=31;
                break;
            case 2:
                this.ngay=28;
                break;
            case 3:
                this.ngay=31;
                break;
            case 4:
                this.ngay=30;
                break;
            case 5:
                this.ngay=31;
                break;
            case 6:
                this.ngay=30;
                break;
            case 7:
                this.ngay= 31;
                break;
            case 8:
                this.ngay= 31;
                break;
            case 9:
                this.ngay= 30;
                break;
            case 10:
                this.ngay= 31;
                break;
            case 11:
                this.ngay= 30;
                break;
            case 12:
                this.ngay= 31;
                break;
            default:
                this.ngay=0;
                break;

        }
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }
}
