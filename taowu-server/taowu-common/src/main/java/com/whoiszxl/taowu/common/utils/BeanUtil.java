package com.whoiszxl.taowu.common.utils;

import cn.hutool.core.bean.copier.CopyOptions;

import java.util.Collection;
import java.util.List;

public class BeanUtil {


    /**
     * 按照Bean对象属性创建对应的Class对象，并忽略某些属性
     *
     * @param <T>              对象类型
     * @param source           源Bean对象
     * @param tClass           目标Class
     * @param ignoreProperties 不拷贝的的属性列表
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> tClass, String... ignoreProperties) {
        return cn.hutool.core.bean.BeanUtil.copyProperties(source, tClass, ignoreProperties);
    }

    /**
     * 复制Bean对象属性<br>
     * 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source           源Bean对象
     * @param target           目标Bean对象
     * @param ignoreProperties 不拷贝的的属性列表
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        cn.hutool.core.bean.BeanUtil.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 复制Bean对象属性<br>
     *
     * @param source     源Bean对象
     * @param target     目标Bean对象
     * @param ignoreCase 是否忽略大小写
     */
    public static void copyProperties(Object source, Object target, boolean ignoreCase) {
        cn.hutool.core.bean.BeanUtil.copyProperties(source, target, ignoreCase);
    }

    /**
     * 复制Bean对象属性<br>
     * 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source      源Bean对象
     * @param target      目标Bean对象
     * @param copyOptions 拷贝选项，见 {@link CopyOptions}
     */
    public static void copyProperties(Object source, Object target, CopyOptions copyOptions) {
        cn.hutool.core.bean.BeanUtil.copyProperties(source, target, copyOptions);
    }

    /**
     * 复制集合中的Bean属性<br>
     * 此方法遍历集合中每个Bean，复制其属性后加入一个新的{@link List}中。
     *
     * @param collection  原Bean集合
     * @param targetType  目标Bean类型
     * @param copyOptions 拷贝选项
     * @param <T>         Bean类型
     * @return 复制后的List
     * @since 5.6.4
     */
    public static <T> List<T> copyToList(Collection<?> collection, Class<T> targetType, CopyOptions copyOptions) {
        return cn.hutool.core.bean.BeanUtil.copyToList(collection, targetType, copyOptions);
    }

    /**
     * 复制集合中的Bean属性<br>
     * 此方法遍历集合中每个Bean，复制其属性后加入一个新的{@link List}中。
     *
     * @param collection 原Bean集合
     * @param targetType 目标Bean类型
     * @param <T>        Bean类型
     * @return 复制后的List
     * @since 5.6.6
     */
    public static <T> List<T> copyToList(Collection<?> collection, Class<T> targetType) {
        return cn.hutool.core.bean.BeanUtil.copyToList(collection, targetType);

    }

}
