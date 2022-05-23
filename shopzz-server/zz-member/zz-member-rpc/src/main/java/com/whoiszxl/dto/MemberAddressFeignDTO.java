package com.whoiszxl.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户地址feign DTO
 *
 * @author whoiszxl
 * @date 2022/4/15
 */
@Data
public class MemberAddressFeignDTO implements Serializable {

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
