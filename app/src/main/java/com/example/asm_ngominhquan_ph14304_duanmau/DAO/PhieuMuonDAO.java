package com.example.asm_ngominhquan_ph14304_duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.PhieuMuon;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private MyHelper myHelper;

    public PhieuMuonDAO(MyHelper myHelper) {
        this.myHelper = myHelper;
    }
    public long insertPM(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",phieuMuon.getMaTT());
        contentValues.put("maTV",phieuMuon.getMaTV());
        contentValues.put("maSach",phieuMuon.getMaSach());
        contentValues.put("tienThue",phieuMuon.getTienThue());
        contentValues.put("ngayMuon", String.valueOf(phieuMuon.getNgayMuon()));
        contentValues.put("traSach",phieuMuon.getTraSach());
        long result = sqLiteDatabase.insert("PhieuMuon",null,contentValues);
        return result;
    }
    public int updatePM(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",phieuMuon.getMaTT());
        contentValues.put("maTV",phieuMuon.getMaTV());
        contentValues.put("maSach",phieuMuon.getMaSach());
        contentValues.put("tienThue",phieuMuon.getTienThue());
        contentValues.put("ngayMuon", String.valueOf(phieuMuon.getNgayMuon()));
        contentValues.put("traSach",phieuMuon.getTraSach());
        int result = sqLiteDatabase.update("PhieuMuon",contentValues,"maPM=?",new String[]{String.valueOf(phieuMuon.getMaPm())});
        return result;
    }
    public int deletePM(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        int result = sqLiteDatabase.delete("PhieuMuon","maPM=?",new String[]{String.valueOf(phieuMuon.getMaPm())});
        return result;
    }
    public List<PhieuMuon> getALlPM(){
        List<PhieuMuon> phieuMuonList = new ArrayList<>();
        String SELECT_ALL = "SELECT *FROM PhieuMuon";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maPM = cursor.getString(cursor.getColumnIndex("maPM"));
                String maTT = cursor.getString(cursor.getColumnIndex("maTT"));
                String maTV = cursor.getString(cursor.getColumnIndex("maTV"));
                String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
                String tienThue = cursor.getString(cursor.getColumnIndex("tienThue"));
                String ngayMuon = cursor.getString(cursor.getColumnIndex("ngayMuon"));
                String traSach = cursor.getString(cursor.getColumnIndex("traSach"));
                PhieuMuon phieuMuon =new PhieuMuon(Integer.parseInt(maPM),Integer.parseInt(maTV),Integer.parseInt(maSach),maTT,Integer.parseInt(tienThue), ngayMuon,Integer.parseInt(traSach));
                phieuMuonList.add(phieuMuon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return phieuMuonList;
    }
}
