package com.whoiszxl.zero.entity.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 数字货币支付信息表(OmsPayDcInfo)实体类
 *
 * @author makejava
 * @since 2021-04-27 11:43:12
 */
public class OmsPayDcInfo implements Serializable {
    private static final long serialVersionUID = 800808281920994448L;
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
    * 币种ID
    */
    private Integer currencyId;
    /**
    * 货币名称
    */
    private String currencyName;
    /**
    * 交易hash
    */
    private String txHash;
    /**
    * 订单总支付金额
    */
    private Double totalAmount;
    /**
    * 用户的出币地址
    */
    private String fromAddress;
    /**
    * 关联的充值地址
    */
    private String toAddress;
    /**
    * 上链时间
    */
    private Date upchainAt;
    /**
    * 上链成功时间
    */
    private Date upchainSuccessAt;
    /**
    * 上链状态，1：上链并确认成功 2：等待确认中 3：未上链
    */
    private Object upchainStatus;
    /**
    * 当前交易确认数
    */
    private Integer currentConfirm;
    /**
    * 当前交易所处区块的高度
    */
    private Integer height;
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

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Date getUpchainAt() {
        return upchainAt;
    }

    public void setUpchainAt(Date upchainAt) {
        this.upchainAt = upchainAt;
    }

    public Date getUpchainSuccessAt() {
        return upchainSuccessAt;
    }

    public void setUpchainSuccessAt(Date upchainSuccessAt) {
        this.upchainSuccessAt = upchainSuccessAt;
    }

    public Object getUpchainStatus() {
        return upchainStatus;
    }

    public void setUpchainStatus(Object upchainStatus) {
        this.upchainStatus = upchainStatus;
    }

    public Integer getCurrentConfirm() {
        return currentConfirm;
    }

    public void setCurrentConfirm(Integer currentConfirm) {
        this.currentConfirm = currentConfirm;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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