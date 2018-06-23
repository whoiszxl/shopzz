package com.whoiszxl.xlmall.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.whoiszxl.xl.activities.ProxyActivity;
import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.delegates.XlDelegate;
import com.whoiszxl.xl.ec.launcher.LauncherDelegate;
import com.whoiszxl.xl.ec.sign.ISignListener;
import com.whoiszxl.xl.ec.sign.SignInDelegate;
import com.whoiszxl.xl.ec.sign.SignUpDelegate;

public class ExampleActivity extends ProxyActivity implements ISignListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏轮播图上的bar
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Starter.getConfigurator().withActivity(this);
    }

    @Override
    public XlDelegate setRootDelegate() {
        return new SignInDelegate();
    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onSignUpSuccess() {
        //Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }
}
