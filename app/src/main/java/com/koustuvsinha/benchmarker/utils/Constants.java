package com.koustuvsinha.benchmarker.utils;

import com.koustuvsinha.benchmarker.models.DbFactoryModel;

import java.util.ArrayList;

/**
 * Created by koustuv on 24/5/15.
 */
public class Constants {

    public static ArrayList<DbFactoryModel> DB_LIST = new ArrayList<DbFactoryModel>() {{
        add(new DbFactoryModel("Realm","v0.83.3","Realm",true));
        add(new DbFactoryModel("ORM Lite Android","v4.48","ORM Lite",true));
        add(new DbFactoryModel("Sugar ORM","v1.4","Satya Narayan",true));
        add(new DbFactoryModel("Green DAO","v1.3","Green DAO",true));
        add(new DbFactoryModel("Active Android","v3.0","Michael Pardo",true));
        add(new DbFactoryModel("Android SQLite","v1.0","Android",true));
    }};

    public static int DB_TYPE_REALM = 1;
    public static int DB_TYPE_ORMLITE = 2;
    public static int DB_TYPE_SUGARORM = 3;
    public static int DB_TYPE_GREENDAO = 4;
    public static int DB_TYPE_ACTIVEANDROID = 5;
    public static int DB_TYPE_DEFAULT = 6;

}
