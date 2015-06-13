package com.koustuvsinha.benchmarker.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by koustuv on 13/6/15.
 */
public class DbResultsSaverModel {

    private long id;
    private int dbType;
    private String testDate;
    private String testTime;
    private int readTime;
    private int insertTime;
    private int updateTime;
    private int deleteTime;

    public DbResultsSaverModel() {
        Date date = Calendar.getInstance().getTime();
        this.testDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date);
        this.testTime = new SimpleDateFormat("hh:mm",Locale.ENGLISH).format(date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(int insertTime) {
        this.insertTime = insertTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(int deleteTime) {
        this.deleteTime = deleteTime;
    }
}
