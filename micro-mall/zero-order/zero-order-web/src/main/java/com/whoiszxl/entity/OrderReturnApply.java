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
 * 订单退货表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@TableName("oms_order_return_apply")
@ApiModel(value="OrderReturnApply对象", description="订单退货表")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单明细项ID")
    private Long orderItemId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "订单中SKU的ID")
    private Long skuId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "退货数量")
    private Integer returnCount;

    @ApiModelProperty(value = "退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货")
    private Integer returnReason;

    @ApiModelProperty(value = "退货备注")
    private String returnComment;

    @ApiModelProperty(value = "退货图片备注，逗号分割")
    private String returnPic;

    @ApiModelProperty(value = "退货记录状态，1：待审核，2：审核不通过，3：审核通过")
    private Integer returnApplyStatus;

    @ApiModelProperty(value = "退货快递单号")
    private String returnLogisticCode;

    @ApiModelProperty(value = "收货人")
    private String returnReceiveName;

    @ApiModelProperty(value = "收货备注")
    private String returnReceiveNote;

    @ApiModelProperty(value = "收货电话")
    private String returnReceivePhone;

    @ApiModelProperty(value = "公司收货地址")
    private String returnCompanyAddress;

    @ApiModelProperty(value = "收货时间")
    private Date returnReceiveTime;

    @ApiModelProperty(value = "处理备注")
    private String handleNote;

    @ApiModelProperty(value = "处理人员")
    private String handleBy;

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

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(Integer returnReason) {
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

    public Integer getReturnApplyStatus() {
        return returnApplyStatus;
    }

    public void setReturnApplyStatus(Integer returnApplyStatus) {
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
        return "OrderReturnApply{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", orderItemId=" + orderItemId +
        ", orderSn=" + orderSn +
        ", skuId=" + skuId +
        ", username=" + username +
        ", freight=" + freight +
        ", returnCount=" + returnCount +
        ", returnReason=" + returnReason +
        ", returnComment=" + returnComment +
        ", returnPic=" + returnPic +
        ", returnApplyStatus=" + returnApplyStatus +
        ", returnLogisticCode=" + returnLogisticCode +
        ", returnReceiveName=" + returnReceiveName +
        ", returnReceiveNote=" + returnReceiveNote +
        ", returnReceivePhone=" + returnReceivePhone +
        ", returnCompanyAddress=" + returnCompanyAddress +
        ", returnReceiveTime=" + returnReceiveTime +
        ", handleNote=" + handleNote +
        ", handleBy=" + handleBy +
        ", version=" + version +
        ", isDeleted=" + isDeleted +
        ", createdBy=" + createdBy +
        ", updatedBy=" + updatedBy +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
