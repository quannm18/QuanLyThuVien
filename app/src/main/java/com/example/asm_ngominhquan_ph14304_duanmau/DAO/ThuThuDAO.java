package com.example.asm_ngominhquan_ph14304_duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.asm_ngominhquan_ph14304_duanmau.Model.Sach;
import com.example.asm_ngominhquan_ph14304_duanmau.Model.ThuThu;
import com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private MyHelper myHelper;

    public ThuThuDAO(MyHelper myHelper) {
        this.myHelper = myHelper;
    }

    public long insertTT(ThuThu thuThu){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",thuThu.getMaTT());
        contentValues.put("hoTen",thuThu.getHoTen());
        contentValues.put("matKhau",thuThu.getMatKhau());
        long result = sqLiteDatabase.insert("ThuThu",null,contentValues);
        return result;
    }
    public int updateTT(ThuThu thuThu){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT",thuThu.getMaTT());
        contentValues.put("hoTen",thuThu.getHoTen());
        contentValues.put("matKhau",thuThu.getMatKhau());
        int result = sqLiteDatabase.update("ThuThu",contentValues,"maTT=?",new String[]{thuThu.getMaTT()});
        return result;
    }
    public int deleteTT(ThuThu thuThu){
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        int result = sqLiteDatabase.delete("ThuThu","maTT=?",new String[]{thuThu.getMaTT()});
        return result;
    }
    
    public List<ThuThu> getAllThuThu(){
        List<ThuThu> thuThuList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM ThuThu";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maTT = cursor.getString(cursor.getColumnIndex("maTT"));
                String hoTen = cursor.getString(cursor.getColumnIndex("hoTen"));
                String matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));
                thuThuList.add(new ThuThu(maTT,hoTen,matKhau));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return thuThuList;
    }
    public int checkLogin(String user, String pass){
        List<ThuThu> thuThuList = getAllThuThu();
        int check = -1;
        for (int i = 0; i < thuThuList.size(); i++) {
            if (user.equals(thuThuList.get(i).getMaTT()) &&  pass.equals(thuThuList.get(i).getMatKhau())){
                check = 0;
                return check;
            }
        }
        return check;
    }
    public ThuThu getTTFromID(String id){
        List<ThuThu> thuThuList = new ArrayList<>();
        String SELECT = "SELECT * FROM ThuThu WHERE maTT=?";
        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, new String[]{id});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maTT = cursor.getString(cursor.getColumnIndex("maTT"));
                String hoTen = cursor.getString(cursor.getColumnIndex("hoTen"));
                String matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));

                thuThuList.add(new ThuThu(maTT,hoTen,matKhau));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return thuThuList.get(0);
    }
}
