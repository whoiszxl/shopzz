package com.whoiszxl.zero.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Bean拷贝工具类
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class BeanCopierUtils {

    //缓存map
    static Map<String, BeanCopier> beanCopierCacheMap = new HashMap<>();

    /**
     * 将对象从source拷贝到target中
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        //获取缓存名称
        String cacheKey = source.getClass().toString() + target.getClass().toString();

        BeanCopier beanCopier = null;
        if(!beanCopierCacheMap.containsKey(cacheKey)) {
            synchronized (BeanCopierUtils.class) {
                if(!beanCopierCacheMap.containsKey(cacheKey)) {
                    beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
                    beanCopierCacheMap.put(cacheKey, beanCopier);
                }else{
                    beanCopier = beanCopierCacheMap.get(cacheKey);
                }
            }
        }else{
            beanCopier = beanCopierCacheMap.get(cacheKey);
        }
        beanCopier.copy(source, target, null);
    }


    /**
     * 拷贝 List
     * @param sources list源
     * @param target list目标
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }

    private BeanCopierUtils() {}

}
