package com.whoiszxl.zero.entity.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 订单退货表(OmsOrderReturnApply)实体类
 *
 * @author makejava
 * @since 2021-04-27 11:43:12
 */
public class OmsOrderReturnApply implements Serializable {
    private static final long serialVersionUID = 696108530166247755L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 订单ID
    */
    private Long orderId;
    /**
    * 订单明细项ID
    */
    private Long orderItemId;
    /**
    * 订单编号
    */
    private String orderSn;
    /**
    * 订单中SKU的ID
    */
    private Long skuId;
    /**
    * 用户名
    */
    private String username;
    /**
    * 退货数量
    */
    private Integer returnCount;
    /**
    * 退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货
    */
    private Object returnReason;
    /**
    * 退货备注
    */
    private String returnComment;
    /**
    * 退货图片备注，逗号分割
    */
    private String returnPic;
    /**
    * 退货记录状态，1：待审核，2：审核不通过，3：审核通过
    */
    private Object returnApplyStatus;
    /**
    * 退货快递单号
    */
    private String returnLogisticCode;
    /**
    * 收货人
    */
    private String returnReceiveName;
    /**
    * 收货备注
    */
    private String returnReceiveNote;
    /**
    * 收货电话
    */
    private String returnReceivePhone;
    /**
    * 公司收货地址
    */
    private String returnCompanyAddress;
    /**
    * 收货时间
    */
    private Date returnReceiveTime;
    /**
    * 处理备注
    */
    private String handleNote;
    /**
    * 处理人员
    */
    private String handleBy;
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

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Object getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(Object returnReason) {
        this.returnReason = returnReason;
    }

    public String getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(String returnComment) {
        this.returnComment = returnComment;
    }

    public String getReturnPic() {
        return returnPic;
    }

    public void setReturnPic(String returnPic) {
        this.returnPic = returnPic;
    }

    public Object getReturnApplyStatus() {
        return returnApplyStatus;
    }

    public void setReturnApplyStatus(Object returnApplyStatus) {
        this.returnApplyStatus = returnApplyStatus;
    }

    public String getReturnLogisticCode() {
        return returnLogisticCode;
    }

    public void setReturnLogisticCode(String returnLogisticCode) {
        this.returnLogisticCode = returnLogisticCode;
    }

    public String getReturnReceiveName() {
        return returnReceiveName;
    }

    public void setReturnReceiveName(String returnReceiveName) {
        this.returnReceiveName = returnReceiveName;
    }

    public String getReturnReceiveNote() {
        return returnReceiveNote;
    }

    public void setReturnReceiveNote(String returnReceiveNote) {
        this.returnReceiveNote = returnReceiveNote;
    }

    public String getReturnReceivePhone() {
        return returnReceivePhone;
    }

    public void setReturnReceivePhone(String returnReceivePhone) {
        this.returnReceivePhone = returnReceivePhone;
    }

    public String getReturnCompanyAddress() {
        return returnCompanyAddress;
    }

    public void setReturnCompanyAddress(String returnCompanyAddress) {
        this.returnCompanyAddress = returnCompanyAddress;
    }

    public Date getReturnReceiveTime() {
        return returnReceiveTime;
    }

    public void setReturnReceiveTime(Date returnReceiveTime) {
        this.returnReceiveTime = returnReceiveTime;
    }

    public String getHandleNote() {
        return handleNote;
    }

    public void setHandleNote(String handleNote) {
        this.handleNote = handleNote;
    }

    public String getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(String handleBy) {
        this.handleBy = handleBy;
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