package com.example.asm_ngominhquan_ph14304_duanmau.Model;

public class ThanhVien {
    private int maTV;
    private String tenTV;
    private String sdt;
    private String cmnd;

    public ThanhVien(int maTV, String tenTV, String sdt, String cmnd) {
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.sdt = sdt;
        this.cmnd = cmnd;
    }

    public ThanhVien() {
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
}
