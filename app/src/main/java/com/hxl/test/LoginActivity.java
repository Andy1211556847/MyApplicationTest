package com.hxl.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hxl.test.presenter.LoginPresenter;
import com.hxl.test.presenter.LoginPresenterImpl;
import com.hxl.test.views.LoginView;

/**
 * Created by huaxianlian on 2017/10/27
 */

public class LoginActivity extends Activity implements LoginView{
    private EditText mLoginName;
    private EditText mPasswrod;

    LoginPresenter mLoginPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_channel);

        findViewById(R.id.layout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用presenter中转方法 调用 Model层的登录方法
                mLoginPresenter.login(mLoginName.getText().toString(),mPasswrod.getText().toString());
            }
        });

        mLoginPresenter = new LoginPresenterImpl(this);

    }

    @Override
    public void showLoading() {
        new ProgressDialog(this).show();
    }

    @Override
    public void hideLogin() {
        new ProgressDialog(this).hide();
    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void showErrotMessage() {
        Toast.makeText(this,"登录异常啦",Toast.LENGTH_SHORT);
    }
}
