package com.koustuvsinha.benchmarker.utils;

import com.squareup.otto.Bus;

/**
 * Created by koustuvsinha on 5/6/15.
 * Singleton class to provide the Bus instance of Otto
 */
public class BusProvider {

    private static BusProvider instance;
    private Bus bus;

    public static void initInstance() {
        if(instance == null) {
            instance = new BusProvider();
        }
    }

    public static BusProvider getInstance() {
        if(instance == null) {
            initInstance();
        }
        return instance;
    }

    private BusProvider() {
        bus = new Bus();
    }

    public Bus getBus() {
        return bus;
    }
}
