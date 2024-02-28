package com.whoiszxl.taowu.common.entity;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 排序查询条件
 *
 * @author whoiszxl
 */
@Data
@ParameterObject
@Schema(description = "排序查询条件")
public class SortQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排序条件
     */
    @Schema(description = "排序条件", example = "sort=published,desc&sort=title,asc")
    private String[] sort;

    /**
     * 解析排序条件为 Spring 分页排序实体
     *
     * @return Spring 分页排序实体
     */
    public Sort getSort() {
        if (ArrayUtil.isEmpty(sort)) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>(sort.length);
        if (StrUtil.contains(sort[0], CharPool.COMMA)) {
            // e.g "sort=published,desc&sort=title,asc"
            for (String s : sort) {
                List<String> sortList = StrUtil.split(s, CharPool.COMMA);
                Sort.Order order =
                    new Sort.Order(Sort.Direction.valueOf(sortList.get(1).toUpperCase()), sortList.get(0));
                orders.add(order);
            }
        } else {
            // e.g "sort=published,desc"
            Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sort[1].toUpperCase()), sort[0]);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
