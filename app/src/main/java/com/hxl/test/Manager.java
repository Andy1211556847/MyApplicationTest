package com.hxl.test;

import android.content.Context;

/**
 * Created by huaxianlian on 2017/7/5
 */

public class Manager {
    private static Manager mInstance = null;

    private Manager() {
    }

    public static synchronized Manager create() {
        if(mInstance == null) {
            mInstance = new Manager();
        } else {
        }
        return mInstance;
    }
}
