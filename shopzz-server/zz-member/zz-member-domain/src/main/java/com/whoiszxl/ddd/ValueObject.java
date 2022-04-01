package com.whoiszxl.ddd;

/**
 * 值对象标记接口
 */
public interface ValueObject<T> extends MarkerInterface {

    /**
     * 值对象通过属性比较
     *
     * @param other
     * @return
     */
    boolean sameValueAs(T other);

}
