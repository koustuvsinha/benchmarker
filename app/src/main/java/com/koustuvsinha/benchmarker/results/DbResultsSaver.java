package com.koustuvsinha.benchmarker.results;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.koustuvsinha.benchmarker.models.DbResultsSaverModel;
import com.koustuvsinha.benchmarker.utils.Constants;

/**
 * Created by koustuvsinha on 13/6/15.
 * DbResultsSaver class to save and retrieve the results of performance testings
 * Used SQLite for better handling of complex queries
 */
public class DbResultsSaver extends SQLiteOpenHelper {
    private Context mContext;
    public static final String TABLE_SAVE = "resultSave";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DB_TYPE = "dbType";
    public static final String COLUMN_TEST_DATE = "testDate";
    public static final String COLUMN_TEST_TIME = "testTime";
    public static final String COLUMN_TEST_TYPE = "testType";
    public static final String COLUMN_NUM_ROWS = "numRows";

    private static final String DATABASE_NAME = "resultsSaver.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    //Database creation SQL statement
    private static final String DATABASE_CREATE = "create table " + TABLE_SAVE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_DB_TYPE + " integer not null, "
            + COLUMN_TEST_DATE + " text not null, "
            + COLUMN_TEST_TIME + " text not null, "
            + COLUMN_TEST_TYPE + " integer not null, "
            + COLUMN_NUM_ROWS + " integer not null"
            + ");";


    public DbResultsSaver(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = mContext;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DbResultsSaver.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE);
        onCreate(db);
    }


    public void saveTest(DbResultsSaverModel saverModel) {

        openDb(Constants.DB_MODE_WRITE);

        ContentValues values;
        db.beginTransaction();

        values = new ContentValues();
        values.put(COLUMN_DB_TYPE,saverModel.getDbType());
        values.put(COLUMN_TEST_DATE,saverModel.getTestDate());
        values.put(COLUMN_TEST_TIME,saverModel.getTestTime());
        values.put(COLUMN_TEST_TYPE,saverModel.getTestType());
        values.put(COLUMN_NUM_ROWS,saverModel.getNumRows());

        db.insert(TABLE_SAVE, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();

        closeDb();
    }

    public DbResultsSaverModel getFirstTestData(int dbType,int numRows, int testType) {

        openDb(Constants.DB_MODE_READ);
        Cursor cursor = db.rawQuery("select * from "
                + TABLE_SAVE
                + " where "
                + COLUMN_DB_TYPE + " = ? "
                + "and " + COLUMN_NUM_ROWS + " = ? "
                + "and " + COLUMN_TEST_TYPE + " = ? "
                + "order by " + COLUMN_ID
                + " asc limit 1",
                new String[] {Integer.toString(dbType),Integer.toString(numRows),
                        Integer.toString(testType)});


        DbResultsSaverModel sv = extractFromCursor(cursor);
        closeDb();
        return sv;
    }

    public DbResultsSaverModel getLatestTestData(int dbType,int numRows, int testType) {
        Log.i(Constants.APP_NAME,"numRows received - " + numRows);
        openDb(Constants.DB_MODE_READ);
        Cursor cursor = db.rawQuery("select * from "
                + TABLE_SAVE
                + " where "
                + COLUMN_DB_TYPE + " = ? "
                + "and " + COLUMN_NUM_ROWS + " = ? "
                + "and " + COLUMN_TEST_TYPE + " = ? "
                + "order by " + COLUMN_ID
                + " desc limit 1",
                new String[]{Integer.toString(dbType),Integer.toString(numRows), Integer.toString(testType)});


        DbResultsSaverModel sv = extractFromCursor(cursor);
        closeDb();
        return sv;
    }

    private void openDb(int mode) {
        if(mode == Constants.DB_MODE_WRITE) {
            db = this.getWritableDatabase();
        }
        else {
            db = this.getReadableDatabase();
        }
    }

    private void closeDb() {
        if(db!=null) {
            db.close();
            db = null;
        }
    }

    private DbResultsSaverModel extractFromCursor(Cursor cursor) {

        DbResultsSaverModel saverModel = null;
        Log.i(Constants.APP_NAME,"Cursor - " + cursor.getCount());
        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            saverModel = new DbResultsSaverModel();
            saverModel.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            saverModel.setDbType(cursor.getInt(cursor.getColumnIndex(COLUMN_DB_TYPE)));
            saverModel.setTestDate(cursor.getString(cursor.getColumnIndex(COLUMN_TEST_DATE)));
            saverModel.setTestTime(cursor.getLong(cursor.getColumnIndex(COLUMN_TEST_TIME)));
            saverModel.setTestType(cursor.getInt(cursor.getColumnIndex(COLUMN_TEST_TYPE)));
            saverModel.setNumRows(cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_ROWS)));
        }

        return saverModel;
    }
}
