package com.whoiszxl.zero.entity.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 第三方支付信息表(OmsPayInfo)实体类
 *
 * @author makejava
 * @since 2021-04-27 11:43:12
 */
public class OmsPayInfo implements Serializable {
    private static final long serialVersionUID = 943996572393496242L;
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
    * 用户ID
    */
    private Long memberId;
    /**
    * 订单总支付金额
    */
    private Double totalAmount;
    /**
    * 交易渠道，1：支付宝，2：微信
    */
    private Object tradeChannel;
    /**
    * 交易流水号，第三方支付平台生成
    */
    private String tradeNo;
    /**
    * 支付状态，1：待支付，2：支付成功，3：支付失败，4：交易关闭；5：退款
    */
    private String status;
    /**
    * 完成第三方支付的时间
    */
    private Date complatedTime;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Object getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(Object tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getComplatedTime() {
        return complatedTime;
    }

    public void setComplatedTime(Date complatedTime) {
        this.complatedTime = complatedTime;
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