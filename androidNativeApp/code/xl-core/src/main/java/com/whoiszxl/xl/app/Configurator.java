package com.whoiszxl.xl.app;

import android.os.Handler;

import java.util.WeakHashMap;

/**
 * @author whoiszxl
 * 配置文件存儲和獲取
 */
public class Configurator {

    /**
     * 使用weakhashmap，键值对在不使用的时候会尽最大限度的去回收，防止内存爆满
     */
    private static final WeakHashMap<Object, Object> XL_CONFIGS = new WeakHashMap<>();
    private static final Handler HANDLER = new Handler();

    /**
     * 构造函数，创建当前对象的时候传入标志，表示还未初始化好
     */
    private Configurator() {
        //表示配置还没有完成
        XL_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        XL_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    /**
     * 静态内部类方式  单例加载当前的配置获取类
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 获取到静态内部类的单例对象
     * @return 当前类的实例
     */
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取到配置的hashMap对象
     * @return 配置的hashMap对象
     */
    final WeakHashMap<Object, Object> getXlConfigs() {
        return XL_CONFIGS;
    }

    /**
     * 调用其表示配置已经初始化好了
     */
    public final void configure() {
        XL_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    /**
     * 配置API请求的host地址，配置后返回当前对象，可以链式调用
     * @param host API host地址
     * @return 当前配置实例
     */
    public final Configurator withApiHost(String host) {
        XL_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    /**
     * 检测配置是否就绪
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) XL_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady) {
            throw new RuntimeException("Configuration is not ready, call configuration");
        }
    }

    /**
     * 配置AVLoading的显示延迟时间
     * @param delayed 延迟时间，毫秒计算
     * @return
     */
    public final Configurator withLoaderDelayed(long delayed) {
        XL_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    /**
     * 传入键的枚举类，获取到实际的配置value
     * @param key @ConfigKeys 枚举类
     * @param <T> 返回对象的泛型
     * @return 配置的value
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        //直接通过Object获取到判断是否为空
        final Object value = XL_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        //不为空直接返回
        return (T) XL_CONFIGS.get(key);
    }


}
