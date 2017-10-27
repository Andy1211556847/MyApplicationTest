package com.hxl.test.listeners;

/**
 * Created by huaxianlian on 2017/10/27
 */

public interface OnLoginListener {
    /**
     * 业务成功
     * @param json
     */
    void onSucceed(String json);

    /***
     * 业务失败
     * @param errorMessage
     */
    void onFail(String errorMessage);
}
