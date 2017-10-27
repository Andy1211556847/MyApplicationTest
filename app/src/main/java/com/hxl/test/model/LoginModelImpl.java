package com.hxl.test.model;

import android.os.Handler;

import com.hxl.test.listeners.OnLoginListener;

/**
 * Created by huaxianlian on 2017/10/27
 */

public class LoginModelImpl implements LoginModel{

    @Override
    public void login(String name, String password, final OnLoginListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onSucceed("{name:124,headImg:\"http://www.baidu.com/124.jpg\"}");
                }
            }
        },3000);
    }

    @Override
    public void outLogin() {
        System.out.println("退出成功");
    }
}
