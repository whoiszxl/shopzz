package com.whoiszxl.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("mall_preferential")
public class Preferential implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消费金额
     */
    private Integer buyMoney;

    /**
     * 优惠金额
     */
    private Integer preMoney;

    /**
     * 品类ID
     */
    private Integer categoryId;

    /**
     * 活动开始日期
     */
    private LocalDate startTime;

    /**
     * 活动截至日期
     */
    private LocalDate endTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 类型1不翻倍 2翻倍
     */
    private String type;


}
