package com.koustuvsinha.benchmarker.databases;

import android.content.Context;
import android.util.Log;

import com.koustuvsinha.benchmarker.models.DbTestRecordModel;
import com.koustuvsinha.benchmarker.utils.Constants;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.KeyIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by koustuv on 10/6/15.
 */
public class DbSnappyHelper implements DbTestInterface {

    private Context mContext;
    private DB snappyDb;
    private final String KEY_HEADER = "snappy";
    private ArrayList<DbTestRecordModel> cachedData;

    public DbSnappyHelper(Context mContext) {
        this.mContext = mContext;
    }

    private void openDb() {
        try {
            snappyDb = DBFactory.open(mContext);
        } catch(Exception e) {
            Log.i(Constants.APP_NAME, "Error occurred while opening snappyDB");
        }
    }

    @Override
    public void insertData(List<DbTestRecordModel> modelList) {

        openDb();

        int id = 0;
        Iterator it = modelList.iterator();
        try {
            while(it.hasNext()) {
                DbTestRecordModel record = (DbTestRecordModel)it.next();
                String key = KEY_HEADER + id;
                snappyDb.put(key, record);
                id ++;
            }
        } catch (Exception e) {
            Log.e(Constants.APP_NAME,"Error inserting snappyDb record");
        }

        closeDb();
    }

    @Override
    public void getData() {

        openDb();

        ArrayList<DbTestRecordModel> recordList = new ArrayList<>();
        try {
            KeyIterator it = snappyDb.allKeysIterator();
            while(it.hasNext()) {
                String key = it.next(1)[0];
                DbTestRecordModel record = snappyDb.getObject(key, DbTestRecordModel.class);
                record.setNewId(key);
                recordList.add(record);
            }
        } catch (Exception e) {
            Log.e(Constants.APP_NAME,"Error retrieving snappyDb data");
        }
        cachedData = recordList;

        closeDb();
    }

    @Override
    public void updateData() {
        openDb();

        try {

            Iterator it = cachedData.iterator();
            while(it.hasNext()) {
                DbTestRecordModel record = (DbTestRecordModel)it.next();
                String key = record.getNewId();
                record.setName("na");
                record.setAge(0);
                record.setAddress("na");
                snappyDb.put(key,record);
            }

        } catch (Exception e) {
            Log.e(Constants.APP_NAME,"Error updating snappyDb data");
        }

        closeDb();
    }

    @Override
    public void deleteAllData() {

        openDb();

        try {

            Iterator it = cachedData.iterator();
            while(it.hasNext()) {
                snappyDb.del(((DbTestRecordModel)it.next()).getNewId());
            }

        } catch (Exception e) {
            Log.e(Constants.APP_NAME,"Error deleting snappyDb data");
        }

        closeDb();
    }

    @Override
    public void closeDb() {
        if(snappyDb!=null) {
            try {
                snappyDb.close();
            } catch (Exception e) {
                Log.i(Constants.APP_NAME,"Error closing Snappy DB instance");
            }
        }
    }

    @Override
    public void removeDbFile() {
        try {
            snappyDb.destroy();
        } catch (Exception e) {
            Log.i(Constants.APP_NAME,"Error destroying Snappy DB instance");
        }

    }
}
