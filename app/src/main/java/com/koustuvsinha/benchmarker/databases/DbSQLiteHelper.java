package com.koustuvsinha.benchmarker.databases;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by koustuv on 27/5/15.
 */
public class DbSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TEST = "testing";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_AGE = "age";

    private static final String DATABASE_NAME = "testing.db";
    private static final int DATABASE_VERSION = 1;

    //Database creation SQL statement
    private static final String DATABASE_CREATE = "create table " + TABLE_TEST
            + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text not null, "
                + COLUMN_ADDRESS + " text not null, "
                + COLUMN_AGE + " integer not null"
            + ");";



    public DbSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
        onCreate(db);
    }
}
