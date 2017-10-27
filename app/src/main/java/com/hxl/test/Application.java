package com.hxl.test;

/**
 * Created by huaxianlian on 2017/8/4
 */

public class Application extends android.app.Application {
    public static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

    }

    public static Application shareInstance(){
        return mApplication;
    }

    public Application currentApplication(){
        return mApplication;
    }


}
