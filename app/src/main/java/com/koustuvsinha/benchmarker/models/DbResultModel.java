package com.koustuvsinha.benchmarker.models;

import android.text.format.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by koustuv on 30/5/15.
 */
public class DbResultModel {

    private String resultMessage;
    private String resultTime;

    public DbResultModel(String resultMessage) {
        this.resultMessage = resultMessage;
        this.resultTime = new SimpleDateFormat("hh:mm:ss.SSS").format(Calendar.getInstance().getTime());
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultTime() {
        return resultTime;
    }
}
