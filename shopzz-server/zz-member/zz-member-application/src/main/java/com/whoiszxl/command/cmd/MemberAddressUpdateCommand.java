package com.whoiszxl.command.cmd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 会员收货地址表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MemberAddress对象", description="会员收货地址表")
public class MemberAddressUpdateCommand implements Serializable {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("收货人")
    private String receiverName;

    @ApiModelProperty("收货人电话号码")
    private String receiverPhone;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String district;

    @ApiModelProperty("收货地址")
    private String detailAddress;

    @ApiModelProperty("是否默认 1:默认 2:非默认")
    private Integer isDefault;

}
