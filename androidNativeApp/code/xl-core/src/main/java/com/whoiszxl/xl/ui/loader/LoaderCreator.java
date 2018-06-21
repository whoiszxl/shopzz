package com.whoiszxl.xl.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * @author whoiszxl
 * loading创建者
 */
public class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();


    /**
     * 通过传入AVLoading的样式类型来创建AVLoadingIndicatorView这个对象
     * @param type 样式类型的类名
     * @param context
     * @return
     */
    static AVLoadingIndicatorView create(String type, Context context) {
        //通过context创建AVLoadingIndicatorView
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        //如果LOADING_MAP里面不存在这个类型
        if (LOADING_MAP.get(type) == null) {
            //获取到Indicator然后将其存入LOADING_MAP中做复用
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        //将Indicator设置到这个view中
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    /**
     * 通过反射创建Indicator
     * @param name
     * @return
     */
    private static Indicator getIndicator(String name) {
        //如果类型为空，直接返回null
        if (name == null || name.isEmpty()) {
            return null;
        }

        //创建一个字符串拼接
        final StringBuilder drawableClassName = new StringBuilder();
        //如果这个是一个不含“.”的简单类名
        if (!name.contains(".")) {
            //我就获取到默认的包名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            //然后在默认的包名com.wang.avi后面拼接上.indicators.
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        //然后还要拼接上这个简单类名
        drawableClassName.append(name);
        try {
            //通过反射创建这个类，然后直接返回
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
