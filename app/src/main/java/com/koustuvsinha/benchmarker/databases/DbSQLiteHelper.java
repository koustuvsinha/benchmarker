package com.koustuvsinha.benchmarker.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.koustuvsinha.benchmarker.models.DbTestRecordModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by koustuv on 27/5/15.
 */
public class DbSQLiteHelper extends SQLiteOpenHelper implements DbTestInterface {

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

    public void insertData(List<DbTestRecordModel> modelList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        db.beginTransaction();

        Iterator<DbTestRecordModel> it = modelList.iterator();
        while(it.hasNext()) {
            DbTestRecordModel model = it.next();
            values = new ContentValues();
            values.put(COLUMN_NAME,model.getName());
            values.put(COLUMN_ADDRESS,model.getAddress());
            values.put(COLUMN_AGE,model.getAge());
            db.insert(TABLE_TEST,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    public List<DbTestRecordModel> getData() {

        ArrayList<DbTestRecordModel> dataList;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TEST,new String[]
                { COLUMN_ID, COLUMN_NAME, COLUMN_AGE, COLUMN_ADDRESS},null,null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();

        dataList  = new ArrayList<DbTestRecordModel>();

        while(cursor.moveToNext()) {
            DbTestRecordModel rec = new DbTestRecordModel();
            rec.setId(cursor.getLong(0));
            rec.setName(cursor.getString(1));
            rec.setAddress(cursor.getString(2));
            rec.setAge(cursor.getInt(3));
            dataList.add(rec);
        }

        cursor.close();

        return dataList;
    }

    public void updateData(List<DbTestRecordModel> modelList) {

    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEST,"1 = 1",null);
    }
}
