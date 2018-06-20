package com.whoiszxl.xl.app;

import android.content.Context;

import java.util.WeakHashMap;

/**
 * @author whoiszxl
 */
public final class Starter {

    /**
     * 初始化方法，初始化配置类里的全局ApplicationContext
     * @param context ApplicationContext
     * @return 返回配置类对象的实例
     */
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取到WeakHashMap的全局单例配置
     * @return WeakHashMap的全局单例配置
     */
    private static WeakHashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getXlConfigs();
    }
}
