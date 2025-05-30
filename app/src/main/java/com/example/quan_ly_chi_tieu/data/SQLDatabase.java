package com.example.quan_ly_chi_tieu.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLDatabase extends SQLiteOpenHelper {
    private static SQLDatabase instance;
    private static final String DATABASE_NAME = "QuanLyChiTieu.sqlite";
    private static final int DATABASE_VERSION = 1;

//    Table area
    private static final String CREATE_TABLE_LoaiThu = "CREATE TABLE IF NOT EXISTS LoaiThu ("
        + "ID INTEGER PRIMARY KEY,"
        + "TenHoaDon TEXT)";
    private static final String CREATE_TABLE_LoaiChi = "CREATE TABLE IF NOT EXISTS LoaiChi ("
            + "ID INTEGER PRIMARY KEY,"
            + "TenHoaDon TEXT)";
    private static final String CREATE_TABLE_BanGhiThu = "CREATE TABLE IF NOT EXISTS BanGhiThu ("
            + "STT INTEGER,"
            + "Ngay TEXT,"
            + "ID INTEGER,"
            + "Money REAL,"
            + "Note TEXT,"
            + "FOREIGN KEY (ID) REFERENCES LoaiThu(ID),"
            + "PRIMARY KEY (STT, Ngay))";
    private static final String CREATE_TABLE_BanGhiChi = "CREATE TABLE IF NOT EXISTS BanGhiChi ("
            + "STT INTEGER,"
            + "Ngay TEXT,"
            + "ID INTEGER,"
            + "Money REAL,"
            + "Note TEXT,"
            + "FOREIGN KEY (ID) REFERENCES LoaiChi(ID),"
            + "PRIMARY KEY (STT, Ngay))";
    private SQLDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized SQLDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new SQLDatabase(context.getApplicationContext());

        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LoaiThu);
        db.execSQL(CREATE_TABLE_LoaiChi);
        db.execSQL(CREATE_TABLE_BanGhiThu);
        db.execSQL(CREATE_TABLE_BanGhiChi);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertLoaiThu(int id, String tenHoaDon) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if(instance!=null)
        {
            try {
                ContentValues values = new ContentValues();
                values.put("ID", id); // Specify the ID value
                values.put("TenHoaDon", tenHoaDon);

                // Insert the data into the table
                result = db.insert("LoaiThu", null, values);
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                db.close();
            }
        }
        else
        {
            Log.e("SQL insert section","not found instance");
        }

        return result;
    }
    public long insertLoaiChi(int id, String tenHoaDon) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("ID", id); // Specify the ID value
            values.put("TenHoaDon", tenHoaDon);

            // Insert the data into the table
            result = db.insert("LoaiChi", null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return result;
    }
    public Cursor Query(String command,String[] args)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(command,args);
    }
    public long insertBanGhiThu(String Ngay,int id,double tien,String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if(instance!=null)
        {
            try {
                String query = "SELECT MAX(STT) FROM BanGhiThu WHERE Ngay = ?";

                Cursor cursor = this.Query(query,new String[]{Ngay});
                int maxSTT=0;
                if(cursor!=null && cursor.moveToFirst())
                {
                    maxSTT=cursor.getInt(0);
                }
                int newSTT = maxSTT + 1;

                ContentValues values = new ContentValues();
                values.put("STT", newSTT);
                values.put("Ngay", Ngay);
                values.put("ID", id);
                values.put("Money", tien);
                values.put("Note", note);


                // Insert the data into the table
                result = db.insert("BanGhiThu", null, values);
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                db.close();
            }
        }
        else
        {
            Log.e("SQL insert section","not found instance");
        }

        return result;
    }
    public long insertBanGhiChi(String Ngay,int id,double tien,String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if(instance!=null)
        {
            try {
                String query = "SELECT MAX(STT) FROM BanGhiChi WHERE Ngay = ?";

                Cursor cursor = this.Query(query,new String[]{Ngay});
                int maxSTT=0;
                if(cursor!=null && cursor.moveToFirst())
                {
                    maxSTT=cursor.getInt(0);
                }
                int newSTT = maxSTT + 1;

                ContentValues values = new ContentValues();
                values.put("STT", newSTT);
                values.put("Ngay", Ngay);
                values.put("ID", id);
                values.put("Money", tien);
                values.put("Note", note);


                // Insert the data into the table
                result = db.insert("BanGhiChi", null, values);
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                db.close();
            }
        }
        else
        {
            Log.e("SQL insert section","not found instance");
        }

        return result;
    }
    public long deleteBanGhiChi(int Stt,String Ngay) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if(instance!=null)
        {
            try {

                String whereClause = "STT = ? AND Ngay = ?";

                // Define the whereArgs for the placeholders
                String[] whereArgs = { String.valueOf(Stt), Ngay };

                // Insert the data into the table
                result = db.delete("BanGhiChi",whereClause,whereArgs);
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                db.close();
            }
        }
        else
        {
            Log.e("SQL delete section","not found instance");
        }

        return result;
    }
    public long deleteBanGhiThu(int Stt,String Ngay) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if(instance!=null)
        {
            try {

                String whereClause = "STT = ? AND Ngay = ?";

                // Define the whereArgs for the placeholders
                String[] whereArgs = { String.valueOf(Stt), Ngay };

                // Insert the data into the table
                result = db.delete("BanGhiThu",whereClause,whereArgs);
            } catch (SQLException e) {
                e.getMessage();
            } finally {
                db.close();
            }
        }
        else
        {
            Log.e("SQL delete section","not found instance");
        }

        return result;
    }
    public long updateBanGhiChi(int stt, String ngay,float money,String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if (instance != null) {
            try {
                ContentValues values = new ContentValues();
                values.put("Money", money);
                values.put("Note", note);

                String whereClause = "STT = ? AND Ngay = ?";
                String[] whereArgs = { String.valueOf(stt), ngay };

                result = db.update("BanGhiChi", values, whereClause, whereArgs);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        } else {
            Log.e("SQL update section", "Instance not found");
        }

        return result;
    }
    public long updateBanGhiThu(int stt, String ngay,float money,String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if (instance != null) {
            try {
                ContentValues values = new ContentValues();
                values.put("Money", money);
                values.put("Note", note);

                String whereClause = "STT = ? AND Ngay = ?";
                String[] whereArgs = { String.valueOf(stt), ngay };

                result = db.update("BanGhiThu", values, whereClause, whereArgs);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        } else {
            Log.e("SQL update section", "Instance not found");
        }

        return result;
    }

}
