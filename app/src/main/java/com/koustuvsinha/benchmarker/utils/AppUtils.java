package com.koustuvsinha.benchmarker.utils;

import java.util.Random;

/**
 * Created by koustuv on 8/6/15.
 */
public class AppUtils {

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
