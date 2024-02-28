package com.whoiszxl.taowu.common.base;

import com.baomidou.mybatisplus.extension.toolkit.Db;

import java.util.Collection;

/**
 * 基础mapper类，继承mybatis plus的BaseMapper
 * @author whoiszxl
 * @param <T>
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量新增
     * @param list 记录列表
     * @return 是否成功
     */
    default boolean insertBatch(Collection<T> list) {
        return Db.saveBatch(list);
    }
}
