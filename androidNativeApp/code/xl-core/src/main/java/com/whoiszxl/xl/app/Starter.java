package com.whoiszxl.xl.app;

import android.content.Context;
import android.os.Handler;

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
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取到Configurator的单例实例
     * @return
     */
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 通过枚举对象直接获取到这个玩意
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 直接获取到ApplicationContext
     * @return
     */
    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT);
    }

    /**
     * 获取到WeakHashMap的全局单例配置
     * @return WeakHashMap的全局单例配置
     */
    public static WeakHashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getXlConfigs();
    }

    /**
     * 获取到全局的handler对象
     * @return handler
     */
    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }
}
