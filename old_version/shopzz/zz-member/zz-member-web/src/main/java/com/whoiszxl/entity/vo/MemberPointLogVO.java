package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 会员积分变动日志表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MemberPointLog对象", description="会员积分变动日志表")
public class MemberPointLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员积分ID")
    private Long memberPointId;

    @ApiModelProperty(value = "变动前的会员积分")
    private Long beforePoint;

    @ApiModelProperty(value = "变动的会员积分")
    private Long changePoint;

    @ApiModelProperty(value = "变动后的会员积分")
    private Long afterPoint;

    @ApiModelProperty(value = "本次积分变动的原因")
    private String updateReason;

}
