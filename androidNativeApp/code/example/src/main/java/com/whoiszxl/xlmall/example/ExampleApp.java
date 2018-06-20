package com.whoiszxl.xlmall.example;

import android.app.Application;

import com.whoiszxl.xl.app.Starter;

/**
 * @author whoiszxl
 * App入口
 */
public class ExampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Starter.init(this)
                .withApiHost("http://localhost:8888/")
                .configure();
    }
}
