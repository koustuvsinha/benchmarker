package com.koustuvsinha.benchmarker.models;

/**
 * Created by koustuv on 6/6/15.
 */
public class DbStatusMessageModel {

    private int statusCode;
    private String statusMessage;
    private int statusPercent;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusPercent() {
        return statusPercent;
    }

    public void setStatusPercent(int statusPercent) {
        this.statusPercent = statusPercent;
    }
}
