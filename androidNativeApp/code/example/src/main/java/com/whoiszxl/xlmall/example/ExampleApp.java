package com.whoiszxl.xlmall.example;

import android.app.Application;

import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.net.interceptors.DebugInterceptor;

/**
 * @author whoiszxl
 * App入口
 */
public class ExampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Starter.init(this)
                .withLoaderDelayed(1000)
                .withApiHost("http://localhost:8888/")
                .withInterceptor(new DebugInterceptor("helllllo", 1))
                .configure();
    }
}
