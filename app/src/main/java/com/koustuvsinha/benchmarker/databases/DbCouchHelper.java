package com.koustuvsinha.benchmarker.databases;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koustuvsinha.benchmarker.models.DbCouchRecordModel;
import com.koustuvsinha.benchmarker.models.DbTestRecordModel;
import com.koustuvsinha.benchmarker.utils.AlertProvider;
import com.koustuvsinha.benchmarker.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by koustuv on 20/6/15.
 */
public class DbCouchHelper implements DbTestInterface {
    private Context mContext;
    private Manager manager;
    private Database database;
    private String docId;

    private final String DB_NAME = "couchtestdb";

    public DbCouchHelper(Context mContext) {
        this.mContext = mContext;
        try {
            manager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
            database = manager.getDatabase(DB_NAME);
        } catch(IOException e) {
            new AlertProvider(mContext).displayErrorMessage("Something is wrong with the CouchBase Lite db");
            Log.e(Constants.APP_NAME, "CouchBase manager exception");
        } catch(CouchbaseLiteException e) {
            Log.e(Constants.APP_NAME,e.getMessage());
        }
    }

    private void openDb() {
        database.open();
    }

    @Override
    public void insertData(List<DbTestRecordModel> modelList) {
        openDb();

        String KEY_HEADER = "couchHeader";
        Iterator it = modelList.iterator();
        int count = 0;
        Document document = database.createDocument();
        Map<String,Object> record = new HashMap<>();

        while(it.hasNext()) {
            String KEY = KEY_HEADER + count;
            count++;
            DbTestRecordModel recordModel = (DbTestRecordModel)it.next();
            DbCouchRecordModel couchRecordModel = new DbCouchRecordModel();

            couchRecordModel.setNewId(KEY);
            couchRecordModel.setName(recordModel.getName());
            couchRecordModel.setAge(recordModel.getAge());
            couchRecordModel.setAddress(recordModel.getAddress());

            record.put(KEY,couchRecordModel);
        }

        try {
            document.putProperties(record);
            docId = document.getId();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        closeDb();
    }

    @Override
    public void getData() {
        openDb();

        Document retrieveDoc = database.getExistingDocument(docId);

        Map<String ,Object> record = retrieveDoc.getProperties();
        Iterator<Map.Entry<String,Object>> it = record.entrySet().iterator();
        ArrayList<DbTestRecordModel> modelList = new ArrayList<>();

        while(it.hasNext()) {
            Map.Entry<String,Object> rec = it.next();
            Log.i(Constants.APP_NAME, rec.getKey());
            //modelList.add((DbTestRecordModel) rec.getValue());
        }

        closeDb();
    }

    @Override
    public void updateData() {

        openDb();

        try {

            Document retrieveDoc = database.getExistingDocument(docId);
            Map<String, Object> record = new HashMap<>();
            //record.putAll(retrieveDoc.getProperties());
            Log.i(Constants.APP_NAME, "_rev : " + retrieveDoc.getProperty("_rev"));
            record.put("_rev", retrieveDoc.getProperty("_rev"));
            Iterator<Map.Entry<String, Object>> it = record.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> rec = it.next();
                String key = rec.getKey();
                DbCouchRecordModel recordModel = new DbCouchRecordModel();
                recordModel.setAddress("na");
                recordModel.setName("na");
                recordModel.setAge(0);

                record.put(key, recordModel);
            }

            retrieveDoc.putProperties(record);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }
    }

    @Override
    public void deleteAllData() {
        openDb();
        Document retrievedDoc = database.getDocument(docId);
        try {
            retrievedDoc.delete();
        } catch(CouchbaseLiteException e) {
            Log.e(Constants.APP_NAME,"Error in deleting doc");
        }
        closeDb();
    }


    @Override
    public void closeDb() {
        database.close();
    }

    @Override
    public void removeDbFile() {
        manager.close();
        File file = new File(manager.getDirectory().getAbsolutePath());
        file.delete();
    }
}
