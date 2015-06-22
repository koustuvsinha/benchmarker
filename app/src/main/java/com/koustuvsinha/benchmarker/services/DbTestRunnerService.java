package com.koustuvsinha.benchmarker.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.databases.DbCouchHelper;
import com.koustuvsinha.benchmarker.databases.DbRealmHelper;
import com.koustuvsinha.benchmarker.databases.DbSQLiteHelper;
import com.koustuvsinha.benchmarker.databases.DbSnappyHelper;
import com.koustuvsinha.benchmarker.databases.DbTestInterface;
import com.koustuvsinha.benchmarker.models.DbResultsSaverModel;
import com.koustuvsinha.benchmarker.models.DbTestRecordModel;
import com.koustuvsinha.benchmarker.results.DbResultsSaver;
import com.koustuvsinha.benchmarker.utils.AppUtils;
import com.koustuvsinha.benchmarker.utils.Constants;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koustuvsinha on 28/5/15.
 * DbTestRunnerService is the main service which runs the testing process
 */
public class DbTestRunnerService extends IntentService {

    private List<DbTestRecordModel> records;
    private Context appContext;
    private int numRecords;
    private int dbType;
    private DbTestInterface dbTestInterface;
    private DbResultsSaverModel saverModel;
    private DbResultsSaver resultsSaver;

    public DbTestRunnerService() {
        super(Constants.DB_TEST_SERVICE);
    }

    /**
     * Method to generate mock data for insertion
     * Uses third party library https://github.com/andygibson/datafactory
     */
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

    /**
     * Method to calculate the running time of insert operation
     * @param testInterface the database instance to test
     * @return the time taken to test
     */
    private long testInsert(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();

        testInterface.insertData(records);

        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    /**
     * Method to calculate the running time of read operation
     * @param testInterface the database instance to test
     * @return the time taken to test
     */
    private long testRead(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();

        testInterface.getData();

        long endTime = System.currentTimeMillis();

        return endTime - startTime;

    }

    /**
     * Method to calculate the running time of update operation
     * @param testInterface the database instance to test
     * @return the time taken to test
     */
    private long testUpdate(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();
        testInterface.updateData();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    /**
     * Method to calculate the running time of delete operation
     * @param testInterface the database instance to test
     * @return the time taken to test
     */
    private long testDelete(DbTestInterface testInterface) {
        long startTime = System.currentTimeMillis();
        testInterface.deleteAllData();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    /**
     * Method to clean the database file after testing is over
     * @param testInterface the database instance to test
     */
    private void cleanData(DbTestInterface testInterface) {
        testInterface.removeDbFile();
        sendMessage(Constants.RECEIVE_STATUS_MSG, "Cleaned database file");
    }

    /**
     * Method to orchestrate the testing process
     * After testing is over, put a Notification that the testing is over
     * including the total time taken to test
     */
    private void testRunner() {

        saverModel = new DbResultsSaverModel();
        saverModel.setDbType(dbType);
        saverModel.setNumRows(numRecords);
        long testTime = System.currentTimeMillis();
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
            case Constants.DB_TYPE_SNAPPY :
                dbTestInterface = new DbSnappyHelper(appContext);
                break;
            case Constants.DB_TYPE_COUCHBASE :
                dbTestInterface = new DbCouchHelper(appContext);
                break;
        }

        String dbName = Constants.DB_LIST.get(dbType).getDbName();

        executeTest(Constants.DB_TEST_NAME_INSERT,Constants.DB_TEST_TYPE_INSERT,dbName);
        executeTest(Constants.DB_TEST_NAME_READ,Constants.DB_TEST_TYPE_READ,dbName);
        executeTest(Constants.DB_TEST_NAME_UPDATE,Constants.DB_TEST_TYPE_UPDATE,dbName);
        executeTest(Constants.DB_TEST_NAME_DELETE,Constants.DB_TEST_TYPE_DELETE,dbName);

        cleanData(dbTestInterface);
        testTime = System.currentTimeMillis() - testTime;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appContext)
                        .setSmallIcon(R.drawable.ic_assignment_turned_in_black_24dp)
                        .setContentTitle("Testing Complete")
                        .setContentText("Testing " + Constants.DB_LIST.get(dbType).getDbName() + " took " + testTime + " ms" );
        NotificationManager mNotificationManager =
                (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(AppUtils.getInstance().getNextNotificationId(), mBuilder.build());

        Log.i(Constants.APP_NAME, "Proceeding to save test data");

    }

    /**
     * Method to execute individual test,send the results back and save them
     * @param testName name of the test taken
     * @param testType type of the test taken
     * @param dbName name of the db
     */
    private void executeTest(String testName,int testType, String dbName) {

        resultsSaver = new DbResultsSaver(appContext);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Starting test : " + testName
                + ", Records : " + numRecords
                + ", DB :  " + dbName);
        long testTime = 0;
        int receive_flag = 0;
        switch(testType) {
            case Constants.DB_TEST_TYPE_INSERT :
                testTime = testInsert(dbTestInterface);
                receive_flag = Constants.RECEIVE_INSERT_TIME;
                break;
            case Constants.DB_TEST_TYPE_READ :
                testTime = testRead(dbTestInterface);
                receive_flag = Constants.RECEIVE_READ_TIME;
                break;
            case Constants.DB_TEST_TYPE_UPDATE :
                testTime = testUpdate(dbTestInterface);
                receive_flag = Constants.RECEIVE_UPDATE_TIME;
                break;
            case Constants.DB_TEST_TYPE_DELETE :
                testTime = testDelete(dbTestInterface);
                receive_flag = Constants.RECEIVE_DELETE_TIME;
                break;
            default:
                break;
        }

        sendMessage(Constants.RECEIVE_STATUS_MSG,"Done Test : " + testName + ", Records : " + numRecords);
        sendMessage(Constants.RECEIVE_STATUS_MSG,"Time taken by " + numRecords + " data records : " + testTime + " ms");
        sendMessage(receive_flag, Long.toString(testTime));

        saverModel.setTestTime(testTime);
        saverModel.setTestType(testType);
        resultsSaver.saveTest(saverModel);
        Log.i(Constants.APP_NAME, "Saved test data");
        
    }

    /**
     * Method to send status messages and results back to Activity
     * by using LocalBroadCastManager
     * @param messageType defines the type of Message
     * @param message defines the body of the Message
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

    /**
     * Method to handle the intent received from Activity
     * @param intent
     */
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
