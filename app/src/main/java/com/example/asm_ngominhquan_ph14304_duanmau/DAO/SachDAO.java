package com.example.asm_ngominhquan_ph14304_duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private MyHelper myHelper;

    public SachDAO(MyHelper myHelper) {
        this.myHelper = myHelper;
    }
    public long insertSach(Sach sach){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach",sach.getTenSach());
        contentValues.put("giaThue",sach.getGiaThue());
        contentValues.put("tacGia",sach.getTacGia());
        contentValues.put("maLoai",sach.getMaLoai());
        long result = sqLiteDatabase.insert("Sach",null,contentValues);
        return result;
    }
    public int updateSach(Sach sach){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSach",sach.getMaSach());
        contentValues.put("tenSach",sach.getTenSach());
        contentValues.put("giaThue",sach.getGiaThue());
        contentValues.put("tacGia",sach.getTacGia());
        contentValues.put("maLoai",sach.getMaLoai());
        int result = sqLiteDatabase.update("Sach",contentValues,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        return result;
    }
    public int deleteSach(Sach sach){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        int result = sqLiteDatabase.delete("Sach","maSach =?",new String[]{String.valueOf(sach.getMaSach())});
        return result;
    }

    public List<Sach> getAllSach(){
        List<Sach> sachList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM Sach";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
                String tenSach = cursor.getString(cursor.getColumnIndex("tenSach"));
                String giaThue = cursor.getString(cursor.getColumnIndex("giaThue"));
                String tacGia = cursor.getString(cursor.getColumnIndex("tacGia"));
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));

                sachList.add(new Sach(Integer.parseInt(maSach),tenSach,Integer.parseInt(giaThue),tacGia,Integer.parseInt(maLoai)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sachList;
    }
    public Sach getSachFromName(String name){
        List<Sach> sachList = new ArrayList<>();
        String SELECT = "SELECT * FROM Sach WHERE tenSach=?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, new String[]{name});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
                String tenSach = cursor.getString(cursor.getColumnIndex("tenSach"));
                String giaThue = cursor.getString(cursor.getColumnIndex("giaThue"));
                String tacGia = cursor.getString(cursor.getColumnIndex("tacGia"));
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));

                sachList.add(new Sach(Integer.parseInt(maSach),tenSach,Integer.parseInt(giaThue),tacGia,Integer.parseInt(maLoai)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sachList.get(0);
    }
    public Sach getSachFromID(String id){
        List<Sach> sachList = new ArrayList<>();
        String SELECT = "SELECT * FROM Sach WHERE maSach=?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, new String[]{id});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maSach = cursor.getString(cursor.getColumnIndex("maSach"));
                String tenSach = cursor.getString(cursor.getColumnIndex("tenSach"));
                String giaThue = cursor.getString(cursor.getColumnIndex("giaThue"));
                String tacGia = cursor.getString(cursor.getColumnIndex("tacGia"));
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));

                sachList.add(new Sach(Integer.parseInt(maSach),tenSach,Integer.parseInt(giaThue),tacGia,Integer.parseInt(maLoai)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sachList.get(0);
    }
}
