package com.whoiszxl.entity.order;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 第三方支付信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Getter
@Setter
@TableName("oms_pay_info")
@ApiModel(value = "PayInfo对象", description = "第三方支付信息表")
public class PayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("订单总支付金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("交易渠道，1：支付宝，2：微信")
    private Integer tradeChannel;

    @ApiModelProperty("交易流水号，第三方支付平台生成")
    private String tradeNo;

    @ApiModelProperty("支付状态，1：待支付，2：支付成功，3：支付失败，4：交易关闭；5：退款")
    private Integer status;

    @ApiModelProperty("完成第三方支付的时间")
    private LocalDateTime complatedTime;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
