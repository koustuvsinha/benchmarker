package com.koustuvsinha.benchmarker.utils;

import com.koustuvsinha.benchmarker.models.DbFactoryModel;

import java.util.ArrayList;

/**
 * Created by koustuv on 24/5/15.
 */
public class Constants {

    public final static ArrayList<DbFactoryModel> DB_LIST = new ArrayList<DbFactoryModel>() {{
        add(new DbFactoryModel("Realm","v0.83.3","Realm",true));
        add(new DbFactoryModel("ORM Lite Android","v4.48","ORM Lite",true));
        add(new DbFactoryModel("Sugar ORM","v1.4","Satya Narayan",true));
        add(new DbFactoryModel("Green DAO","v1.3","Green DAO",true));
        add(new DbFactoryModel("Active Android","v3.0","Michael Pardo",true));
        add(new DbFactoryModel("Android SQLite","v1.0","Android",true));
    }};

    public final static String APP_NAME = "Benchmarker";

    public final static int DB_TYPE_REALM = 0;
    public final static int DB_TYPE_ORMLITE = 1;
    public final static int DB_TYPE_SUGARORM = 2;
    public final static int DB_TYPE_GREENDAO = 3;
    public final static int DB_TYPE_ACTIVEANDROID = 4;
    public final static int DB_TYPE_DEFAULT = 5;

    public final static String DB_TYPE = "dbType";

    public final static String DB_TEST_SERVICE = "db_performance_testing_service";
    public final static String DB_NUM_RECORDS = "numRecords";
    public final static int DB_DEFAULT_RECORDS = 10;

    public final static String RECEIVE_STATUS = "RecieveStatus";
    public final static String RECEIVE_MSG = "ReceiveMsg";
    public final static String RECEIVE_RESULT = "ReceiveResult";

    public final static int RECEIVE_STATUS_MSG = 101;
    public final static int RECEIVE_INSERT_TIME = 102;
    public final static int RECEIVE_READ_TIME = 103;
    public final static int TESTING_START = 111;
    public final static int TESTING_END = 112;

    public final static String RECEIVER_INTENT = "receiver";

    public final static int DB_MODE_READ = 113;
    public final static int DB_MODE_WRITE = 114;

    public final static String[] TEST_LIMIT = {"1000", "10000", "100000" };
    public final static int[] TEST_LIMIT_VAL = {1000,10000,100000};
    public final static String TEST_LIMIT_SELECTED = "selectedTestLimit";
}
