package com.koustuvsinha.benchmarker.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by koustuv on 14/6/15.
 */
public class AlertProvider {
    private Context mContext;

    public AlertProvider(Context mContext) {
        this.mContext = mContext;
    }

    public void displayErrorMessage(String message) {
        new MaterialDialog.Builder(mContext)
                .title("Oops!")
                .content(message)
                .positiveText("OK")
                .show();
    }
}
