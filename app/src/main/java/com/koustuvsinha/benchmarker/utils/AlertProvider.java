package com.koustuvsinha.benchmarker.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by koustuvsinha on 14/6/15.
 * Class to provide a common platform to display alert messages
 */
public class AlertProvider {
    private Context mContext;

    public AlertProvider(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Method to display error message in a dialog
     * @param message
     */
    public void displayErrorMessage(String message) {
        new MaterialDialog.Builder(mContext)
                .title("Oops!")
                .content(message)
                .positiveText("OK")
                .show();
    }
}
