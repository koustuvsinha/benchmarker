package com.koustuvsinha.benchmarker.databases;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.android.AndroidContext;
import com.koustuvsinha.benchmarker.models.DbTestRecordModel;
import com.koustuvsinha.benchmarker.utils.AlertProvider;
import com.koustuvsinha.benchmarker.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private List<DbTestRecordModel> cachedData;

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

        while(it.hasNext()) {
            Document document = database.createDocument();
            Map<String,Object> record = new HashMap<>();

            String KEY = KEY_HEADER + count;
            count++;

            DbTestRecordModel recordModel = (DbTestRecordModel)it.next();

            record.put("name",recordModel.getName());
            record.put("address",recordModel.getAddress());
            record.put("age",recordModel.getAge());
            record.put("stringId",KEY);

            try {
                document.putProperties(record);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }

        closeDb();
    }

    @Override
    public void getData() {
        openDb();

        ArrayList<DbTestRecordModel> modelList = new ArrayList<>();
        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);

        try {
            QueryEnumerator result = query.run();
            for(Iterator<QueryRow> it = result; it.hasNext();) {
                QueryRow row = it.next();
                Document record = database.getDocument(row.getDocumentId());
                DbTestRecordModel recordModel = new DbTestRecordModel();
                recordModel.setNewId(record.getId());
                recordModel.setName((String) record.getProperty("name"));
                recordModel.setAge(21);
                recordModel.setAddress((String) record.getProperty("address"));
                modelList.add(recordModel);
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        cachedData = modelList;

        closeDb();
    }

    @Override
    public void updateData() {

        openDb();

        Iterator it = cachedData.iterator();
        while(it.hasNext()) {
            DbTestRecordModel recordModel = (DbTestRecordModel)it.next();
            Document record = database.getDocument(recordModel.getNewId());
            Map<String, Object> properties = new HashMap<>();
            properties.putAll(record.getProperties());
            properties.put("name", "na");
            properties.put("age", 0);
            properties.put("address", "na");
            try {
                record.putProperties(properties);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }

        closeDb();
    }

    @Override
    public void deleteAllData() {
        openDb();

        Iterator it = cachedData.iterator();
        while(it.hasNext()) {
            DbTestRecordModel recordModel = (DbTestRecordModel)it.next();
            Document record = database.getDocument(recordModel.getNewId());
            try {
                record.delete();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
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
