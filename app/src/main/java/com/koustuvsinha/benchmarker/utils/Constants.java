package com.koustuvsinha.benchmarker.utils;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.models.DbFactoryModel;

import java.util.ArrayList;

/**
 * Created by koustuv on 24/5/15.
 */
public class Constants {

    public final static String APP_NAME = "Benchmarker";

    public final static int DB_TYPE_DEFAULT = 0;
    public final static int DB_TYPE_REALM = 1;
    public final static int DB_TYPE_SNAPPY = 2;
    public final static int DB_TYPE_COUCHBASE = 3;
    public final static int DB_TYPE_ORMLITE = 4;
    public final static int DB_TYPE_SUGARORM = 5;
    public final static int DB_TYPE_GREENDAO = 6;
    public final static int DB_TYPE_ACTIVEANDROID = 7;
    public final static int DB_TYPE_DBFLOW = 8;

    public final static ArrayList<DbFactoryModel> DB_LIST = new ArrayList<DbFactoryModel>() {{
        add(new DbFactoryModel(DB_TYPE_DEFAULT,"Android SQLite","v1.0","Android",true, R.color.db_android_sqlite,true));
        add(new DbFactoryModel(DB_TYPE_REALM,"Realm","v0.83.3","Realm",true,R.color.db_realm,true));
        add(new DbFactoryModel(DB_TYPE_SNAPPY,"SnappyDB","v0.1.0","nhachicha",true,R.color.db_snappy,true));
        add(new DbFactoryModel(DB_TYPE_COUCHBASE,"Couchbase Lite","v1.1.0","Couchbase Inc",true,R.color.db_couchbase,true));
        add(new DbFactoryModel(DB_TYPE_ORMLITE,"ORM Lite Android","v4.48","ORM Lite",true,R.color.db_orm_lite,false));
        add(new DbFactoryModel(DB_TYPE_SUGARORM,"Sugar ORM","v1.4","Satya Narayan",true,R.color.db_sugar_orm,false));
        add(new DbFactoryModel(DB_TYPE_GREENDAO,"Green DAO","v1.3","Green DAO",true,R.color.db_green_dao,false));
        add(new DbFactoryModel(DB_TYPE_ACTIVEANDROID,"Active Android","v3.0","Michael Pardo",true,R.color.db_active_android,false));
        add(new DbFactoryModel(DB_TYPE_DBFLOW,"DBFlow","v2.0.0","Raizlabs",true,R.color.db_dbflow,false));
    }};

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
    public final static int RECEIVE_UPDATE_TIME = 104;
    public final static int RECEIVE_DELETE_TIME = 105;
    public final static int TESTING_START = 111;
    public final static int TESTING_END = 112;

    public final static String RECEIVER_INTENT = "receiver";

    public final static int DB_MODE_READ = 113;
    public final static int DB_MODE_WRITE = 114;

    public final static String[] TEST_LIMIT = {"1000", "10000", "100000" };
    public final static int[] TEST_LIMIT_VAL = {1000,10000,100000};
    public final static Integer[] TEST_LIMIT_VAL_OBJ = {1000,10000,100000};
    public final static String TEST_LIMIT_SELECTED = "selectedTestLimit";

    public final static int PERCENT_TOTAL = 24;
    public final static int PERCENT_STATUS = 120;

    public final static String SELECTED_DB_TEST = "selectedDb";
    public final static String INTENT_FILTER = "com.koustuvsinha.benchmarker.services.DbTestRunnerService";

    public final static int RESULT_FIRST_DATA = 130;
    public final static int RESULT_LAST_DATA = 131;

    public final static int DB_TEST_TYPE_READ = 140;
    public final static int DB_TEST_TYPE_INSERT = 141;
    public final static int DB_TEST_TYPE_UPDATE = 142;
    public final static int DB_TEST_TYPE_DELETE = 143;

    public final static String DB_TEST_NAME_READ = "Read";
    public final static String DB_TEST_NAME_INSERT = "Insert";
    public final static String DB_TEST_NAME_UPDATE = "Update";
    public final static String DB_TEST_NAME_DELETE = "Delete";


}
