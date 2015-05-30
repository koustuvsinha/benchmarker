package com.koustuvsinha.benchmarker.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.koustuvsinha.benchmarker.databases.DbSQLiteHelper;
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
    private ResultReceiver rec;

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

    private long testInsert(int dbType) {
        long startTime = System.currentTimeMillis();

        switch(dbType) {
            case Constants.DB_TYPE_DEFAULT :
                DbSQLiteHelper sqLiteHelper = new DbSQLiteHelper(appContext);
                sqLiteHelper.insertData(records);
            break;
        }

        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private void cleanData(int dbType) {
        switch(dbType) {
            case Constants.DB_TYPE_DEFAULT :
                DbSQLiteHelper sqLiteHelper = new DbSQLiteHelper(appContext);
                sqLiteHelper.deleteAllData();
                break;
        }
    }

    private void testRunner() {

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Preparing Test Data");
        prepareData();
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Test Data Prepared");

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Starting inserting " + numRecords + " data records into SQLite DB");
        long insertTime = testInsert(Constants.DB_TYPE_DEFAULT);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Insertion of " + numRecords + " data records complete");
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Insertion of " + numRecords + " data records took " + insertTime + " ms");
        sendMessage(Constants.RECEIVE_INSERT_TIME,insertTime);

        cleanData(Constants.DB_TYPE_DEFAULT);

    }

    /*
     * Message to send status messages
     */
    private void sendMessage(int messageType, String message) {
        Log.i(Constants.APP_NAME, message);
        Bundle bundle = new Bundle();

        bundle.putInt(Constants.RECEIVE_STATUS,messageType);
        bundle.putString(Constants.RECEIVE_MSG,message);

        rec.send(Activity.RESULT_OK,bundle);
    }

    /*
     * Message to send results
     */
    private void sendMessage(int messageType, long resultTime) {
        Log.i(Constants.APP_NAME, Long.toString(resultTime));
        Bundle bundle = new Bundle();

        bundle.putInt(Constants.RECEIVE_STATUS, messageType);
        bundle.putLong(Constants.RECEIVE_RESULT,resultTime);

        rec.send(Activity.RESULT_OK,bundle);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(Constants.APP_NAME,"Intent received..");
        rec = intent.getParcelableExtra("receiver");
        numRecords = intent.getIntExtra(Constants.DB_NUM_RECORDS,Constants.DB_DEFAULT_RECORDS);

        // start testing
        sendMessage(Constants.TESTING_START,"Testing started at " + new Date().toString());
        testRunner();
        sendMessage(Constants.TESTING_START,"Testing ended at " + new Date().toString());

    }
}
