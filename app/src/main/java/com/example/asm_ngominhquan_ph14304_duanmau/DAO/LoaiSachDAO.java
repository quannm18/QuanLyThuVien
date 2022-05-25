package com.example.asm_ngominhquan_ph14304_duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.LoaiSach;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private MyHelper myHelper;

    public LoaiSachDAO(MyHelper myHelper) {
        this.myHelper = myHelper;
    }
    public long insertLS(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai",loaiSach.getTenLoai());
        long result = sqLiteDatabase.insert("LoaiSach",null,contentValues);
        return result;
    }
    public int updateLS(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai",loaiSach.getTenLoai());
        int result = sqLiteDatabase.update("LoaiSach",contentValues,"maLoai=?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        return result;
    }
    public int deleteLS(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        int result = sqLiteDatabase.delete("LoaiSach","maLoai=?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        return result;
    }

    public List<LoaiSach> getAllLoaiSach(){
        List<LoaiSach> loaiSachList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM LoaiSach";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));
                String tenLoai = cursor.getString(cursor.getColumnIndex("tenLoai"));
                loaiSachList.add(new LoaiSach(Integer.parseInt(maLoai),tenLoai));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return loaiSachList;
    }
    public LoaiSach getLSFormName(String name){
        List<LoaiSach> loaiSachList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM LoaiSach WHERE tenLoai=?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,new String[]{name});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));
                String tenLoai = cursor.getString(cursor.getColumnIndex("tenLoai"));
                loaiSachList.add(new LoaiSach(Integer.parseInt(maLoai),tenLoai));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return loaiSachList.get(0);
    }
    public LoaiSach getLSFormID(int id){
        List<LoaiSach> loaiSachList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM LoaiSach WHERE maLoai=?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,new String[]{String.valueOf(id)});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));
                String tenLoai = cursor.getString(cursor.getColumnIndex("tenLoai"));
                loaiSachList.add(new LoaiSach(Integer.parseInt(maLoai),tenLoai));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return loaiSachList.get(0);
    }

}
