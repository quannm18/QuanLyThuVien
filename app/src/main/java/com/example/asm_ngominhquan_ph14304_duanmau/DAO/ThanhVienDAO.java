package com.example.asm_ngominhquan_ph14304_duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThanhVien;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private MyHelper myHelper;

    public ThanhVienDAO(MyHelper myHelper) {
        this.myHelper = myHelper;
    }
    public long insertTV(ThanhVien thanhVien){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("maTV",thanhVien.getMaTV());
        contentValues.put("tenTV",thanhVien.getTenTV());
        contentValues.put("SDT",thanhVien.getSdt());
        contentValues.put("CMND",thanhVien.getCmnd());
        long result = sqLiteDatabase.insert("ThanhVien",null,contentValues);
        return result;
    }
    public int updateTV(ThanhVien thanhVien){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTV",thanhVien.getMaTV());
        contentValues.put("tenTV",thanhVien.getTenTV());
        contentValues.put("SDT",thanhVien.getSdt());
        contentValues.put("CMND",thanhVien.getCmnd());
        int result = sqLiteDatabase.update("ThanhVien",contentValues,"maTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
        return result;
    }
    public int deleteTV(ThanhVien thanhVien){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        int result = sqLiteDatabase.delete("ThanhVien","maTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
        return result;
    }
    public List<ThanhVien> getAllTV(){
        List<ThanhVien> thanhVienList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM ThanhVien";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                int maTV = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV")));
                String tenTV = cursor.getString(cursor.getColumnIndex("tenTV"));
                String sdt = cursor.getString(cursor.getColumnIndex("SDT"));
                String cmnd = cursor.getString(cursor.getColumnIndex("CMND"));
                thanhVienList.add(new ThanhVien(maTV,tenTV,sdt,cmnd));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return thanhVienList;
    }
    public ThanhVien getTVFromName(String name){
        List<ThanhVien> thanhVienList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM ThanhVien WHERE tenTV = ?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,new String[]{name});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                int maTV = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV")));
                String tenTV = cursor.getString(cursor.getColumnIndex("tenTV"));
                String sdt = cursor.getString(cursor.getColumnIndex("SDT"));
                String cmnd = cursor.getString(cursor.getColumnIndex("CMND"));
                thanhVienList.add(new ThanhVien(maTV,tenTV,sdt,cmnd));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return thanhVienList.get(0);
    }
    public ThanhVien getTVFromID(int id){
        List<ThanhVien> thanhVienList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM ThanhVien WHERE maTV=?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,new String[]{String.valueOf(id)});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                int maTV = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV")));
                String tenTV = cursor.getString(cursor.getColumnIndex("tenTV"));
                String sdt = cursor.getString(cursor.getColumnIndex("SDT"));
                String cmnd = cursor.getString(cursor.getColumnIndex("CMND"));
                thanhVienList.add(new ThanhVien(maTV,tenTV,sdt,cmnd));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return thanhVienList.get(0);
    }
    public List<String> getID(){
        List<String> listID = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM ThanhVien";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                int maTV = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV")));
                listID.add(String.valueOf(maTV));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listID;
    }
}
