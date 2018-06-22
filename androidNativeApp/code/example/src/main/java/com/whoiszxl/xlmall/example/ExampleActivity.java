package com.whoiszxl.xlmall.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.whoiszxl.xl.activities.ProxyActivity;
import com.whoiszxl.xl.delegates.XlDelegate;
import com.whoiszxl.xl.ec.launcher.LauncherDelegate;
import com.whoiszxl.xl.ec.sign.SignUpDelegate;

public class ExampleActivity extends ProxyActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏轮播图上的bar
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public XlDelegate setRootDelegate() {
        return new SignUpDelegate();
    }
}
