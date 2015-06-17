package com.koustuvsinha.benchmarker.utils;

import com.koustuvsinha.benchmarker.models.DbFactoryModel;

import java.util.Iterator;

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

    public DbFactoryModel getDbListItem(int dbType) {
        Iterator it = Constants.DB_LIST.iterator();
        while(it.hasNext()) {
            DbFactoryModel factoryModel = (DbFactoryModel)it.next();
            if(factoryModel.getDbType() == dbType) {
                return factoryModel;
            }
        }
        return null;
    }
    
}
