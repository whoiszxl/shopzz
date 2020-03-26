package com.whoiszxl.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("mall_category_report")
public class CategoryReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 1级分类
     */
    @TableId(value = "category_id1", type = IdType.ID_WORKER)
    private Integer categoryId1;

    /**
     * 2级分类
     */
    private Integer categoryId2;

    /**
     * 3级分类
     */
    private Integer categoryId3;

    /**
     * 统计日期
     */
    private LocalDate countDate;

    /**
     * 销售数量
     */
    private Integer num;

    /**
     * 销售额
     */
    private Integer money;


}
