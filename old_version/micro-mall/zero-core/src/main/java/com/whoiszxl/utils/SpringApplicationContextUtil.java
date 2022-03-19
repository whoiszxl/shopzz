package com.whoiszxl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring容器工具
 */
@Component
public class SpringApplicationContextUtil {

    /**
     * spring容器
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取bean
     * @param clazz bean类型
     * @return bean实例
     */
    public <T> T getBean(Class<? extends T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
