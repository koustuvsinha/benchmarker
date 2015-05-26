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
    }};
}
