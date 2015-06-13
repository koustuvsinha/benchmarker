package com.koustuvsinha.benchmarker.utils;

import java.util.Random;

/**
 * Created by koustuv on 8/6/15.
 */
public class AppUtils {

    private static AppUtils instance;
    private int notificationId;

    private AppUtils() {
        notificationId = 0;
    }

    public static void initInstance() {
        if(instance == null) {
            instance = new AppUtils();
        }
    }

    public static AppUtils getInstance() {
        if(instance == null) {
            initInstance();
        }
        return instance;
    }

    public int getNextNotificationId() {
        return ++ notificationId;
    }
    
}
