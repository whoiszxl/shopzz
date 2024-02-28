package com.whoiszxl.taowu.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author whoiszxl
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /** 属性名,与数据库中字段对应 */
    String property() default "";

    /** 查询类型 */
    Type type() default Type.EQUAL;

    /** 多字段模糊查询 */
    String blurry() default "";

    /** 查询类型 */
    enum Type {
        /**
         * 等值查询，例如：WHERE `age` = 18
         */
        EQUAL,
        /**
         * 非等值查询，例如：WHERE `age` != 18
         */
        NOT_EQUAL,
        /**
         * 大于查询，例如：WHERE `age` > 18
         */
        GREATER_THAN,
        /**
         * 小于查询，例如：WHERE `age` < 18
         */
        LESS_THAN,
        /**
         * 大于等于查询，例如：WHERE `age` >= 18
         */
        GREATER_THAN_OR_EQUAL,
        /**
         * 小于等于查询，例如：WHERE `age` <= 18
         */
        LESS_THAN_OR_EQUAL,
        /**
         * 范围查询，例如：WHERE `age` BETWEEN 10 AND 18
         */
        BETWEEN,
        /**
         * 左模糊查询，例如：WHERE `nickname` LIKE '%张'
         */
        LEFT_LIKE,
        /**
         * 中模糊查询，例如：WHERE `nickname` LIKE '%雪%'
         */
        INNER_LIKE,
        /**
         * 右模糊查询，例如：WHERE `nickname` LIKE '雪%'
         */
        RIGHT_LIKE,
        /**
         * 包含查询，例如：WHERE `age` IN (10, 20, 30)
         */
        IN,
        /**
         * 不包含查询，例如：WHERE `age` NOT IN (20, 30)
         */
        NOT_IN,
        /**
         * 空查询，例如：WHERE `email` IS NULL
         */
        IS_NULL,
        /**
         * 非空查询，例如：WHERE `email` IS NOT NULL
         */
        NOT_NULL,;
    }

}
