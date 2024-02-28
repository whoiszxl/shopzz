package com.whoiszxl.taowu.common.utils;

import cn.hutool.core.util.ReflectUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反射工具
 * @author whoiszxl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectUtils {

    public static List<String> getNonStaticFieldsName(Class<?> beanClass) throws SecurityException {
        List<Field> nonStaticFields = getNonStaticFields(beanClass);
        return nonStaticFields.stream().map(Field::getName).collect(Collectors.toList());
    }

    public static List<Field> getNonStaticFields(Class<?> beanClass) throws SecurityException {
        Field[] fields = ReflectUtil.getFields(beanClass);
        return Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).collect(Collectors.toList());
    }
}
