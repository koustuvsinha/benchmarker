package com.koustuvsinha.benchmarker.models;

/**
 * Created by koustuv on 26/5/15.
 */
public class DbFactoryModel {
    private int dbType;
    private String dbName;
    private String dbVersion;
    private String dbOwner;
    private boolean openSourced;
    private int chartColor;

    public DbFactoryModel(int dbType,String dbName, String dbVersion, String dbOwner, boolean openSourced, int chartColor) {
        this.dbType = dbType;
        this.dbName = dbName;
        this.dbVersion = dbVersion;
        this.dbOwner = dbOwner;
        this.openSourced = openSourced;
        this.chartColor = chartColor;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getDbOwner() {
        return dbOwner;
    }

    public void setDbOwner(String dbOwner) {
        this.dbOwner = dbOwner;
    }

    public boolean isOpenSourced() {
        return openSourced;
    }

    public void setOpenSourced(boolean openSourced) {
        this.openSourced = openSourced;
    }

    public int getChartColor() {
        return chartColor;
    }

    public void setChartColor(int chartColor) {
        this.chartColor = chartColor;
    }
}
