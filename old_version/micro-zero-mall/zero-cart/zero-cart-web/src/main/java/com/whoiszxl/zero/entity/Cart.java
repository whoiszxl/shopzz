package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车表
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "oms_cart")
@Entity
public class Cart extends AbstractObject implements Serializable {

    /**
    * 主键ID
    */
    @Id
    private Long id;
    /**
     * 购物车所属用户ID
     */
    private Long memberId;
    /**
     * 商品SPU ID
     */
    private Long productId;
    /**
     * 商品SKU ID
     */
    private Long skuId;
    /**
    * SKU名称
    */
    private String skuName;
    /**
    * SKU图片
    */
    private String skuPic;
    /**
    * 购买数量
    */
    private Integer quantity;
    /**
    * 价格
    */
    private BigDecimal price;
    /**
    * 销售属性
    */
    private String saleAttr;
    /**
     * 是否选中 0未选中 1选中
     */
    private Integer checked;
    /**
     * 状态
     */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;
}