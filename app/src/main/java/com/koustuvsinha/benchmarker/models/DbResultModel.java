package com.koustuvsinha.benchmarker.models;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by koustuv on 30/5/15.
 */
public class DbResultModel {

    private String resultMessage;
    private String resultTime;

    public DbResultModel(String resultMessage) {
        this.resultMessage = resultMessage;
        this.resultTime = DateFormat.getDateTimeInstance().format(new Date());
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
