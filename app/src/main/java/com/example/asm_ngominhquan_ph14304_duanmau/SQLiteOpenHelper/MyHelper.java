package com.example.asm_ngominhquan_ph14304_duanmau.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PNLINB.db";
    private static final int VERSION = 1;
    public MyHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng Thủ thư
        String CREATE_TT =
                "create table ThuThu (" +
                        "maTT TEXT PRIMARY KEY UNIQUE, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL)";
        db.execSQL(CREATE_TT);
        db.execSQL("INSERT INTO ThuThu VALUES(\"admin\",\"Administration\",\"admin\")");
        db.execSQL("INSERT INTO ThuThu VALUES(\"TT1\",\"Nguyễn Thủ Một\",\"123\")");
        db.execSQL("INSERT INTO ThuThu VALUES(\"TT2\",\"Trần Thủ Hai\",\"123\")");
        db.execSQL("INSERT INTO ThuThu VALUES(\"TT3\",\"Lê Thủ Ba\",\"123\")");
        //Bảng Thành viên
        String CREATE_TV =
                "create table ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenTV TEXT NOT NULL, " +
                        "SDT TEXT NOT NULL, " +
                        "CMND TEXT NOT NULL)";
        db.execSQL(CREATE_TV);
        db.execSQL("INSERT INTO ThanhVien VALUES(null,\"Nguyễn Văn Tèo\",\"0986991232\",\"044209990087\")");
        db.execSQL("INSERT INTO ThanhVien VALUES(null,\"Trần Văn Tí\",\"0999999088\",\"088090990900\")");
        db.execSQL("INSERT INTO ThanhVien VALUES(null,\"Mai Văn Kia\",\"0999999011\",\"055209990087\")");
        //Bảng Loại sách
        String CREATE_LOAISACH =
                "create table LoaiSach (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT UNIQUE NOT NULL)";
        db.execSQL(CREATE_LOAISACH);
        db.execSQL("INSERT INTO LoaiSach VALUES (NULL, \"Sách Kinh Tế\")");
        db.execSQL("INSERT INTO LoaiSach VALUES (NULL, \"Sách Lập Trình\")");
        db.execSQL("INSERT INTO LoaiSach VALUES (NULL, \"Sách Đồ Họa\")");
        //Bảng Sách
        String CREATE_SACH =
                "create table Sach (" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "tacGia TEXT NOT NULL, " +
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(CREATE_SACH);
        //Bảng Phiếu Mượn
        String CREATE_PM =
                "create table PhieuMuon (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maTT TEXT REFERENCES ThuThu(maTT), " +
                        "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                        "maSach INTEGER REFERENCES Sach(maSach), " +
                        "tienThue INTEGER NOT NULL, " +
                        "ngayMuon DATE NOT NULL, " +
                        "traSach INTEGER NOT NULL)";
        db.execSQL(CREATE_PM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa khi update phiên bản
        String dropTableThuThu = "DROP TABLE IF EXISTS ThuThu";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = "DROP TABLE IF EXISTS ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "DROP TABLE IF EXISTS LoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = "DROP TABLE IF EXISTS Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "DROP TABLE IF EXISTS PhieuMuon";
        db.execSQL(dropTablePhieuMuon);

        onCreate(db);
    }
}
