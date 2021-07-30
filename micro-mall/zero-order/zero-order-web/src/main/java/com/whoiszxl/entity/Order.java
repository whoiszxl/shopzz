package com.whoiszxl.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@TableName("oms_order")
@ApiModel(value="Order对象", description="订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）")
    private Integer orderStatus;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty(value = "支付方式: [1:支付宝 2:微信 3:数字货币]")
    private Integer payType;

    @ApiModelProperty(value = "订单总额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "运费金额")
    private BigDecimal freightAmount;

    @ApiModelProperty(value = "促销折扣金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal pointAmount;

    @ApiModelProperty(value = "优惠券抵扣金额")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "后台调整订单使用的折扣金额")
    private BigDecimal adminDiscountAmount;

    @ApiModelProperty(value = "应付总额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer invoiceType;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceHeader;

    @ApiModelProperty(value = "发票内容")
    private String invoiceContent;

    @ApiModelProperty(value = "收票人电话")
    private String invoiceReceiverPhone;

    @ApiModelProperty(value = "收票人邮箱")
    private String invoiceReceiverEmail;

    @ApiModelProperty(value = "收票人收货地址")
    private String invoiceReceiverAddress;

    @ApiModelProperty(value = "订单备注")
    private String orderComment;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "确认收货时间")
    private Date receiveTime;

    @ApiModelProperty(value = "评价时间")
    private Date commentTime;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(BigDecimal pointAmount) {
        this.pointAmount = pointAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getAdminDiscountAmount() {
        return adminDiscountAmount;
    }

    public void setAdminDiscountAmount(BigDecimal adminDiscountAmount) {
        this.adminDiscountAmount = adminDiscountAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceReceiverPhone() {
        return invoiceReceiverPhone;
    }

    public void setInvoiceReceiverPhone(String invoiceReceiverPhone) {
        this.invoiceReceiverPhone = invoiceReceiverPhone;
    }

    public String getInvoiceReceiverEmail() {
        return invoiceReceiverEmail;
    }

    public void setInvoiceReceiverEmail(String invoiceReceiverEmail) {
        this.invoiceReceiverEmail = invoiceReceiverEmail;
    }

    public String getInvoiceReceiverAddress() {
        return invoiceReceiverAddress;
    }

    public void setInvoiceReceiverAddress(String invoiceReceiverAddress) {
        this.invoiceReceiverAddress = invoiceReceiverAddress;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    @Override
    public String toString() {
        return "Order{" +
        "id=" + id +
        ", orderSn=" + orderSn +
        ", memberId=" + memberId +
        ", username=" + username +
        ", orderStatus=" + orderStatus +
        ", receiverName=" + receiverName +
        ", receiverPhone=" + receiverPhone +
        ", receiverPostCode=" + receiverPostCode +
        ", receiverProvince=" + receiverProvince +
        ", receiverCity=" + receiverCity +
        ", receiverRegion=" + receiverRegion +
        ", receiverDetailAddress=" + receiverDetailAddress +
        ", payType=" + payType +
        ", totalAmount=" + totalAmount +
        ", freightAmount=" + freightAmount +
        ", promotionAmount=" + promotionAmount +
        ", pointAmount=" + pointAmount +
        ", couponAmount=" + couponAmount +
        ", adminDiscountAmount=" + adminDiscountAmount +
        ", payAmount=" + payAmount +
        ", invoiceType=" + invoiceType +
        ", invoiceHeader=" + invoiceHeader +
        ", invoiceContent=" + invoiceContent +
        ", invoiceReceiverPhone=" + invoiceReceiverPhone +
        ", invoiceReceiverEmail=" + invoiceReceiverEmail +
        ", invoiceReceiverAddress=" + invoiceReceiverAddress +
        ", orderComment=" + orderComment +
        ", paymentTime=" + paymentTime +
        ", deliveryTime=" + deliveryTime +
        ", receiveTime=" + receiveTime +
        ", commentTime=" + commentTime +
        ", version=" + version +
        ", isDeleted=" + isDeleted +
        ", createdBy=" + createdBy +
        ", updatedBy=" + updatedBy +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
