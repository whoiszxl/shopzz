package com.whoiszxl.zero.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("会员详情信息VO")
@Data
public class MemberDetailVO {

    @ApiModelProperty("会员基础信息")
    private MemberVO memberVO;

    @ApiModelProperty("会员扩展信息")
    private MemberInfoVO memberInfoVO;

}
