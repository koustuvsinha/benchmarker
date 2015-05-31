package com.koustuvsinha.benchmarker.databases;

import com.koustuvsinha.benchmarker.models.DbTestRecordModel;

import java.util.List;

/**
 * Created by koustuv on 27/5/15.
 */
public interface DbTestInterface {

    public void insertData(List<DbTestRecordModel> modelList);
    public List<DbTestRecordModel> getData();
    public void updateData(List<DbTestRecordModel> modelList);
    public void deleteAllData();
    public void closeDb();
}
