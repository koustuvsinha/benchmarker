package com.koustuvsinha.benchmarker.utils;

/**
 * Created by koustuvsinha on 8/6/15.
 * Utility class for the app
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

    /**
     * Return a new notification id for the Notification Builder
     * @return
     */
    public int getNextNotificationId() {
        return ++ notificationId;
    }
    
}
