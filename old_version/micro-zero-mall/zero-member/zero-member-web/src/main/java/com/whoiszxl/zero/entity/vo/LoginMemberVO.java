package com.whoiszxl.zero.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录成功后返回的会员参数")
public class LoginMemberVO {

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "token的过期时间")
    private Long expire;

    @ApiModelProperty(value = "访问token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
}
