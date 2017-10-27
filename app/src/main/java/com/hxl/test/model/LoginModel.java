package com.hxl.test.model;

import com.hxl.test.listeners.OnLoginListener;

/**
 * Created by huaxianlian on 2017/10/27
 */

public interface LoginModel {
    void login(String name, String password, OnLoginListener listener);
    void outLogin();
}
