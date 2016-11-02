package com.example.firdaus.tugaskecil1;

/**
 * Created by firdaus on 01/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBadapter {

    private static final String TAG = "DBAdapter"; //used for logging database version changes

    // Field Names:
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DESKRIPSI = "deskripsi";
    public static final String KEY_YEAR = "thn";
    public static final String KEY_MONTH = "bln";
    public static final String KEY_DAY = "day";
    //public static final String KEY_DATE = "date";

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_TASK,KEY_DESKRIPSI,KEY_YEAR,KEY_MONTH,KEY_DAY};

    // Column Numbers for each Field Name:
    public static final int COL_ROWID = 0;
    public static final int COL_TASK = 1;
    public static final int COL_Deskripsi = 2;
    public static final int COL_Year = 3;
    public static final int COL_Month = 4;
    public static final int COL_Day = 5;



    // public static final int COL_DATE = 2;

    // Info database:
    public static final String DATABASE_NAME = "DBTugas";
    public static final String DATABASE_TABLE = "Tugas";
    public static final int DATABASE_VERSION = 1; // The version number must be incremented each time a change to DB structure occurs.

    //SQL untuk membuat database
    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_TASK + " TEXT NOT NULL, "
                    + KEY_DESKRIPSI + " TEXT NOT NULL, "
                    + KEY_YEAR + " INT NOT NULL, "
                    + KEY_MONTH + " INT NOT NULL, "
                    + KEY_DAY + " INT NOT NULL "
                    + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBadapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Membuka koneksi ke database.
    public DBadapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Menutup koneksi ke database
    public void close() {
        myDBHelper.close();
    }

    // Insert ke database.
    public long insertRow(String place,String desk, int tahun, int bulan, int hari) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TASK, place);
        newValues.put(KEY_DESKRIPSI, desk);
        newValues.put(KEY_YEAR, tahun);
        newValues.put(KEY_MONTH, bulan);
        newValues.put(KEY_DAY, hari);
        //initialValues.put(KEY_DATE, date);

        // Menambah data ke database
        return db.insert(DATABASE_TABLE, null, newValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    // Mengambil semua data.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }



    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String task,String desk, int tahun, int bulan, int hari) {
        String where = KEY_ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TASK, task);
        newValues.put(KEY_DESKRIPSI, desk);
        newValues.put(KEY_YEAR, tahun);
        newValues.put(KEY_MONTH, bulan);
        newValues.put(KEY_DAY, hari);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }

}
