package com.whoiszxl.xl.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @author whoiszxl
 */
public abstract class BaseWXActivity extends AppCompatActivity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //必须写在onCreate中
        XLWechat.getInstance().getWXAPI().handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //这里也写一下，避免某些手机出问题
        setIntent(intent);
        XLWechat.getInstance().getWXAPI().handleIntent(getIntent(), this);
    }
}
