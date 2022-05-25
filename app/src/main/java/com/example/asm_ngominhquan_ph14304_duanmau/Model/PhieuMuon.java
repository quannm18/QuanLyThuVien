package com.example.asm_ngominhquan_ph14304_duanmau.Model;

import java.util.Date;

public class PhieuMuon {
    private int maPm;
    private int maTV;
    private int maSach;
    private String maTT;
    private int tienThue;
    private String ngayMuon;
    private int traSach;

    public PhieuMuon(int maPm, int maTV, int maSach, String maTT, int tienThue, String ngayMuon, int traSach) {
        this.maPm = maPm;
        this.maTV = maTV;
        this.maSach = maSach;
        this.maTT = maTT;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
    }

    public PhieuMuon() {
    }

    public int getMaPm() {
        return maPm;
    }

    public void setMaPm(int maPm) {
        this.maPm = maPm;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }
}
