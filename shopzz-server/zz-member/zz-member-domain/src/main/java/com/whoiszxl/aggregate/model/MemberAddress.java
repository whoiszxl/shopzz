package com.whoiszxl.aggregate.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 会员收货地址聚合根
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-01
 */
@Data
@ApiModel(value = "MemberAddress聚合根", description = "会员收货地址聚合根")
public class MemberAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
      private Long id;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("收货人")
    private String reciverName;

    @ApiModelProperty("收货人电话号码")
    private String reciverPhone;

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
