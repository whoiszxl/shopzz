package com.whoiszxl.aggregate.model;

import com.whoiszxl.utils.AuthUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员聚合根
 * 暂不做聚合，一般来说，会聚合到address和info信息，再通过repository来统一查询，但是这样粒度不好控制。
 * @author whoiszxl
 * @date 2022/4/1
 */
@Data
@ApiModel("会员聚合根")
public class Member {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("会员名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("支付密码")
    private String payPassword;

    @ApiModelProperty("谷歌验证码")
    private String googleKey;

    @ApiModelProperty("谷歌验证码是否开启,默认0不开启, 1开启")
    private Integer googleStatus;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

    /**
     * 登录行为
     */
    public void login() {
        AuthUtils.login(this.id);
    }

    /**
     * 获取当前token
     * @return
     */
    public String getCurrentToken() {
        return AuthUtils.getToken();
    }

    /**
     * 绑定会员ID
     * @param memberId
     */
    public void bindMemberId(Long memberId) {
        this.id = memberId;
    }


}
