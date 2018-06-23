package com.whoiszxl.xlmall.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.ec.database.DatabaseManager;
import com.whoiszxl.xl.ec.icon.FontEcModule;
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
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
                .withApiHost("http://localhost:8888/")
                .withInterceptor(new DebugInterceptor("helllllo", 1))
                .withWeChatAppId("app_id")
                .withWeChatAppSecret("app_secret")
                .configure();

        DatabaseManager.getInstance().init(this);
    }
}
