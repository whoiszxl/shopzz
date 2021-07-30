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
 * 数字货币支付信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@TableName("oms_dc_pay_info")
@ApiModel(value="DcPayInfo对象", description="数字货币支付信息表")
public class DcPayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "币种ID")
    private Integer currencyId;

    @ApiModelProperty(value = "货币名称")
    private String currencyName;

    @ApiModelProperty(value = "交易hash")
    private String txHash;

    @ApiModelProperty(value = "订单总支付金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "用户的出币地址")
    private String fromAddress;

    @ApiModelProperty(value = "关联的充值地址")
    private String toAddress;

    @ApiModelProperty(value = "上链时间")
    private Date upchainAt;

    @ApiModelProperty(value = "上链成功时间")
    private Date upchainSuccessAt;

    @ApiModelProperty(value = "上链状态，1：上链并确认成功 2：等待确认中 3：未上链")
    private Boolean upchainStatus;

    @ApiModelProperty(value = "当前交易确认数")
    private Long currentConfirm;

    @ApiModelProperty(value = "当前交易所处区块的高度")
    private Long height;

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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
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

    public Boolean getUpchainStatus() {
        return upchainStatus;
    }

    public void setUpchainStatus(Boolean upchainStatus) {
        this.upchainStatus = upchainStatus;
    }

    public Long getCurrentConfirm() {
        return currentConfirm;
    }

    public void setCurrentConfirm(Long currentConfirm) {
        this.currentConfirm = currentConfirm;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
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
        return "DcPayInfo{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", orderSn=" + orderSn +
        ", memberId=" + memberId +
        ", currencyId=" + currencyId +
        ", currencyName=" + currencyName +
        ", txHash=" + txHash +
        ", totalAmount=" + totalAmount +
        ", fromAddress=" + fromAddress +
        ", toAddress=" + toAddress +
        ", upchainAt=" + upchainAt +
        ", upchainSuccessAt=" + upchainSuccessAt +
        ", upchainStatus=" + upchainStatus +
        ", currentConfirm=" + currentConfirm +
        ", height=" + height +
        ", version=" + version +
        ", isDeleted=" + isDeleted +
        ", createdBy=" + createdBy +
        ", updatedBy=" + updatedBy +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
