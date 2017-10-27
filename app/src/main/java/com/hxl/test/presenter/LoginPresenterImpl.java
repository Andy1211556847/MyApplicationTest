package com.hxl.test.presenter;

import com.hxl.test.listeners.OnLoginListener;
import com.hxl.test.model.LoginModel;
import com.hxl.test.model.LoginModelImpl;
import com.hxl.test.views.LoginView;

/**
 * Created by huaxianlian on 2017/10/27
 */

public class LoginPresenterImpl implements LoginPresenter {

    LoginView mLoginView;
    LoginModel mLoginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.mLoginView = loginView;
        mLoginModel = new LoginModelImpl();
    }

    @Override
    public void login(String name, String pwd) {
        mLoginView.showLoading();
        mLoginModel.login(name, pwd, new OnLoginListener() {
            @Override
            public void onSucceed(String json) {
                mLoginView.toMainActivity();
            }

            @Override
            public void onFail(String errorMessage) {
                mLoginView.showErrotMessage();
            }
        });
    }
}
