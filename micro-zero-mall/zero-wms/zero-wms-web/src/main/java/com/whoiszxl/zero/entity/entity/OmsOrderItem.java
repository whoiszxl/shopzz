package com.whoiszxl.zero.entity.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 订单明细表(OmsOrderItem)实体类
 *
 * @author makejava
 * @since 2021-04-27 11:43:11
 */
public class OmsOrderItem implements Serializable {
    private static final long serialVersionUID = 162994351749402015L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 订单ID
    */
    private Long orderId;
    /**
    * 订单编号
    */
    private String orderSn;
    /**
    * 商品id
    */
    private Long productId;
    /**
    * 分类id
    */
    private Long categoryId;
    /**
    * sku id
    */
    private Long skuId;
    /**
    * sku名称
    */
    private String skuName;
    /**
    * sku图片地址
    */
    private String skuPic;
    /**
    * sku价格
    */
    private Double skuPrice;
    /**
    * 购买数量
    */
    private Integer quantity;
    /**
    * 商品销售属性
    */
    private String skuAttrs;
    /**
    * 促销活动ID
    */
    private Long promotionActivityId;
    /**
    * 商品促销分解金额
    */
    private Double promotionAmount;
    /**
    * 优惠券优惠分解金额
    */
    private Double couponAmount;
    /**
    * 积分优惠分解金额
    */
    private Double pointAmount;
    /**
    * 该商品经过优惠后的分解金额
    */
    private Double realAmount;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuPic() {
        return skuPic;
    }

    public void setSkuPic(String skuPic) {
        this.skuPic = skuPic;
    }

    public Double getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Double skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSkuAttrs() {
        return skuAttrs;
    }

    public void setSkuAttrs(String skuAttrs) {
        this.skuAttrs = skuAttrs;
    }

    public Long getPromotionActivityId() {
        return promotionActivityId;
    }

    public void setPromotionActivityId(Long promotionActivityId) {
        this.promotionActivityId = promotionActivityId;
    }

    public Double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Double promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Double getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(Double pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}