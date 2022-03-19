package com.whoiszxl.logger.annotation;

import java.lang.annotation.*;

/**
 * 日志记录注解
 *
 * @author whoiszxl
 * @date 2021/12/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SSLog {

    /**
     * 描述
     * @return
     */
    String value();

    /**
     * 记录请求参数，默认记录
     * @return
     */
    boolean recordRequestParams() default true;

    /**
     * 记录返回参数，默认记录
     * @return
     */
    boolean recordResponseParams() default true;
}

