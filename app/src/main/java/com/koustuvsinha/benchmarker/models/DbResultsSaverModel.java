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
    private long testTime;
    private int testType;
    private int numRows;

    public DbResultsSaverModel() {
        Date date = Calendar.getInstance().getTime();
        this.id = 0;
        this.testDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date);
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

    public long getTestTime() {
        return testTime;
    }

    public void setTestTime(long testTime) {
        this.testTime = testTime;
    }

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public long getTimeOps() {
        return (long)(((double)numRows/testTime)*1000);
    }

    @Override
    public String toString() {
        return "DbResultsSaverModel{" +
                "id=" + id +
                ", dbType=" + dbType +
                ", testDate='" + testDate + '\'' +
                ", testTime='" + testTime + '\'' +
                ", testType=" + testType +
                ", numRows=" + numRows +
                '}';
    }
}
