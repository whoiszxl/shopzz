package com.whoiszxl.taowu.common.entity.response;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义分页返回实体
 * @author whoiszxl
 */
@Data
@Schema(description = "自定义分页")
public class PageResponse<T> {

    @Schema(description = "分页列表数据")
    private List<T> list;

    @Schema(description = "总记录数")
    private Long total;


    /**
     * 将mybatis-plus的IPage转换为自定义分页实体
     * @param page mybatis plus的分页对象
     * @param targetClass 转换的class
     * @return自定义分页实体
     * @param <K> 原始列表数据类型
     * @param <V> 目标列表数据类型
     */
    public static <K, V> PageResponse<V> convert(IPage<K> page, Class<V> targetClass) {
        if (page == null) {
            return null;
        }
        PageResponse<V> pageResponse = new PageResponse<>();
        pageResponse.setList(BeanUtil.copyToList(page.getRecords(), targetClass));
        pageResponse.setTotal(page.getTotal());
        return pageResponse;
    }

    /**
     * 对list进行分页
     * @param page 页码
     * @param size 数量
     * @param list 列表
     * @return 自定义分页实体
     * @param <V> 数据类型
     */
    public static <V> PageResponse<V> convert(int page, int size, List<V> list) {
        PageResponse<V> pageResponse = new PageResponse<>();
        if (CollUtil.isEmpty(list)) {
            return pageResponse;
        }

        pageResponse.setTotal((long)list.size());
        int fromIndex = (page - 1) * size;
        int toIndex = page * size + size;
        if (fromIndex > list.size()) {
            pageResponse.setList(new ArrayList<>());
        } else if (toIndex >= list.size()) {
            pageResponse.setList(list.subList(fromIndex, list.size()));
        } else {
            pageResponse.setList(list.subList(fromIndex, toIndex));
        }
        return pageResponse;
    }
}
