package com.koustuvsinha.benchmarker.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
