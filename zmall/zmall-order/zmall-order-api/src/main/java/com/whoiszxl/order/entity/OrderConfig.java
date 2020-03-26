package com.whoiszxl.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("mall_order_config")
public class OrderConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 正常订单超时时间（分）
     */
    private Integer orderTimeout;

    /**
     * 秒杀订单超时时间（分）
     */
    private Integer seckillTimeout;

    /**
     * 自动收货（天）
     */
    private Integer takeTimeout;

    /**
     * 售后期限
     */
    private Integer serviceTimeout;

    /**
     * 自动五星好评
     */
    private Integer commentTimeout;


}
