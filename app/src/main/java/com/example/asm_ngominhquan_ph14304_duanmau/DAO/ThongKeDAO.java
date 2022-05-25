package com.example.asm_ngominhquan_ph14304_duanmau.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.Top;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private MyHelper myHelper;

    public ThongKeDAO(MyHelper myHelper) {
        this.myHelper = myHelper;
    }
    public List<Top> getTop(){
        String sqlTop =
                "SELECT maSach, count(maSach) as soLuong" +
                        " FROM PhieuMuon" +
                        " GROUP BY maSach" +
                        " ORDER by soLuong DESC LIMIT 10";

        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(myHelper);
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlTop,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Sach sach = sachDAO.getSachFromID(cursor.getString(cursor.getColumnIndex("maSach")));
                Top top = new Top();
                top.setTenSach(sach.getTenSach());
                top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
                list.add(top);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
    public int getDoanhThu(String tuNgay, String denNgay){
        String SELECT_DT = "SELECT SUM(tienThue) AS" +
                " doanhThu FROM PhieuMuon" +
                " WHERE ngayMuon BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase  = myHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DT, new String[]{tuNgay,denNgay});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
               try {
                   list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
               }
               catch (Exception e){
                   list.add(0);
                   e.printStackTrace();
               }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list.get(0);
    }
    public int getDT(String tuNgay, String denNgay){
        String SELECT_DT = "SELECT SUM(tienThue) AS" +
                " doanhThu FROM PhieuMuon" +
                " WHERE (traSach = 1) AND (ngayMuon BETWEEN ? AND ?)";
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase  = myHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DT, new String[]{tuNgay,denNgay});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                try {
                    list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
                }
                catch (Exception e){
                    list.add(0);
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list.get(0);
    }
    public int getCT(String tuNgay, String denNgay){
        String SELECT_DT = "SELECT SUM(tienThue) AS" +
                " doanhThu FROM PhieuMuon" +
                " WHERE (traSach = 0) AND (ngayMuon BETWEEN ? AND ?)";
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase  = myHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DT, new String[]{tuNgay,denNgay});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                try {
                    list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
                }
                catch (Exception e){
                    list.add(0);
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list.get(0);
    }
}
