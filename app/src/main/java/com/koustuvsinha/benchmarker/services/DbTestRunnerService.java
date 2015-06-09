package com.koustuvsinha.benchmarker.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.koustuvsinha.benchmarker.databases.DbRealmHelper;
import com.koustuvsinha.benchmarker.databases.DbSQLiteHelper;
import com.koustuvsinha.benchmarker.databases.DbTestInterface;
import com.koustuvsinha.benchmarker.models.DbTestRecordModel;
import com.koustuvsinha.benchmarker.utils.Constants;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koustuv on 28/5/15.
 */
public class DbTestRunnerService extends IntentService {

    private List<DbTestRecordModel> records;
    private Context appContext;
    private int numRecords;
    private int dbType;
    private DbTestInterface dbTestInterface;

    public DbTestRunnerService() {
        super(Constants.DB_TEST_SERVICE);
    }

    private void prepareData() {

        int num = numRecords;

        records = new ArrayList<DbTestRecordModel>(num);

        while(num-- > 0) {
            DbTestRecordModel record = new DbTestRecordModel();
            DataFactory person = new DataFactory();
            record.setName(person.getName());
            record.setAge(21);
            record.setAddress(person.getAddress());
            records.add(record);
        }
    }

    private long testInsert(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();

        testInterface.insertData(records);

        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private long testRead(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();

        testInterface.getData();

        long endTime = System.currentTimeMillis();

        return endTime - startTime;

    }

    private long testUpdate(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();
        testInterface.updateData();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private long testDelete(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();
        testInterface.deleteAllData();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private void cleanData(DbTestInterface testInterface) {
        testInterface.removeDbFile();
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Cleaned database file");
    }

    private void testRunner() {

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Preparing Test Data");
        prepareData();
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Test Data Prepared");

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Testing " + Constants.DB_LIST.get(dbType).getDbName());

        switch(dbType) {
            case Constants.DB_TYPE_DEFAULT :
                dbTestInterface = new DbSQLiteHelper(appContext);
                break;
            case Constants.DB_TYPE_REALM :
                dbTestInterface = new DbRealmHelper(appContext);
                break;
        }

        String dbName = Constants.DB_LIST.get(dbType).getDbName();

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Starting inserting " + numRecords + " data records into " + dbName);
        long insertTime = testInsert(dbTestInterface);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Insertion of " + numRecords + " data records complete");
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Insertion of " + numRecords + " data records took " + insertTime + " ms");
        sendMessage(Constants.RECEIVE_INSERT_TIME,Long.toString(insertTime));

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Starting reading " + numRecords + " data records from " + dbName);
        long readTime = testRead(dbTestInterface);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Reading of " + numRecords + " data records complete");
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Reading of " + numRecords + " data records took " + readTime + " ms");
        sendMessage(Constants.RECEIVE_READ_TIME, Long.toString(readTime));

        sendMessage(Constants.RECEIVE_STATUS_MSG, "Starting updating " + numRecords + " data records of " + dbName);
        long updateTime = testUpdate(dbTestInterface);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Updating of " + numRecords + " data records complete");
        sendMessage(Constants.RECEIVE_STATUS_MSG, "Updating of " + numRecords + " data records took " + updateTime + " ms");
        sendMessage(Constants.RECEIVE_UPDATE_TIME,Long.toString(updateTime));

        sendMessage(Constants.RECEIVE_STATUS_MSG, "Starting deleting " + numRecords + " data records");
        long deleteTime = testDelete(dbTestInterface);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Deleting of " + numRecords + " data records complete");
        sendMessage(Constants.RECEIVE_STATUS_MSG, "Deleting of " + numRecords + " data records took " + deleteTime + " ms");
        sendMessage(Constants.RECEIVE_DELETE_TIME,Long.toString(deleteTime));

        cleanData(dbTestInterface);

    }

    /*
     * Message to send status messages and results
     */
    private void sendMessage(int messageType, String message) {
        Log.i(Constants.APP_NAME, message);

        Intent packet = new Intent(Constants.INTENT_FILTER);
        packet.putExtra("resultCode", Activity.RESULT_OK);
        packet.putExtra(Constants.RECEIVE_STATUS,messageType);
        packet.putExtra(Constants.RECEIVE_MSG,message);

        LocalBroadcastManager.getInstance(this).sendBroadcast(packet);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dbTestInterface != null) {
            dbTestInterface.closeDb();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(Constants.APP_NAME,"Intent received..");

        dbType = intent.getIntExtra(Constants.DB_TYPE,Constants.DB_TYPE_DEFAULT);
        numRecords = intent.getIntExtra(Constants.DB_NUM_RECORDS,Constants.DB_DEFAULT_RECORDS);

        // start testing
        sendMessage(Constants.TESTING_START,"Testing started at " + new Date().toString());
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Testing started ...");
        testRunner();
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Testing complete");
        sendMessage(Constants.TESTING_END,"Testing ended at " + new Date().toString());

        stopSelf();
    }
}
