package com.koustuvsinha.benchmarker.databases;

import android.content.Context;

import com.koustuvsinha.benchmarker.models.DbRealmTestRecordModel;
import com.koustuvsinha.benchmarker.models.DbTestRecordModel;
import com.koustuvsinha.benchmarker.utils.AppUtils;

import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by koustuv on 8/6/15.
 */
public class DbRealmHelper implements DbTestInterface {

    private Realm realm;
    private Context context;
    private RealmResults<DbRealmTestRecordModel> cachedRead;

    public DbRealmHelper(Context context) {
        this.context = context;
    }

    private void openDb() {
        realm = Realm.getInstance(context);
    }

    @Override
    public void insertData(List<DbTestRecordModel> modelList) {
        openDb();
        realm.beginTransaction();

        Iterator<DbTestRecordModel> it = modelList.iterator();

        int id = 10;

        while(it.hasNext()) {
            id ++;
            DbTestRecordModel dbTestRecordModel = it.next();
            DbRealmTestRecordModel realmTestRecordModel = realm.createObject(DbRealmTestRecordModel.class);
            realmTestRecordModel.setId(id);
            realmTestRecordModel.setName(dbTestRecordModel.getName());
            realmTestRecordModel.setAddress(dbTestRecordModel.getAddress());
            realmTestRecordModel.setAge(dbTestRecordModel.getAge());
        }

        realm.commitTransaction();
        closeDb();
    }

    @Override
    public void getData() {
        openDb();
        RealmQuery<DbRealmTestRecordModel> query = realm.where(DbRealmTestRecordModel.class);
        RealmResults<DbRealmTestRecordModel> result = query.findAll();
        cachedRead = result;
    }

    @Override
    public void updateData() {

        realm.beginTransaction();

        for(int i = cachedRead.size() - 1; i >= 0; i--) {
            cachedRead.get(i).setName("na");
            cachedRead.get(i).setAge(0);
            cachedRead.get(i).setAddress("na");
        }

        realm.commitTransaction();
        closeDb();
    }

    @Override
    public void deleteAllData() {

        openDb();
        realm.beginTransaction();
        realm.clear(DbRealmTestRecordModel.class);
        realm.commitTransaction();
        closeDb();
    }

    @Override
    public void closeDb() {
        if(realm!=null) {
            realm.close();
        }
    }

    @Override
    public void removeDbFile() {
        Realm.deleteRealmFile(context);
    }
}
