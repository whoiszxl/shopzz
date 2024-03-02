package com.whoiszxl.cqrs.admin.query;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.whoiszxl.core.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Data
@Schema(description = "第三方支付信息查询条件")
public class PayInfoQuery implements Serializable {

    @Query
    @Schema(description = "订单编号")
    private String orderNo;

    @Query
    @Schema(description = "用户ID")
    private Long memberId;

    @Query
    @Schema(description = "交易渠道，1：支付宝，2：微信")
    private Integer tradeChannel;

    @Query
    @Schema(description = "支付状态，1：待支付，2：支付成功，3：支付失败，4：交易关闭；5：退款")
    private Integer status;

}
