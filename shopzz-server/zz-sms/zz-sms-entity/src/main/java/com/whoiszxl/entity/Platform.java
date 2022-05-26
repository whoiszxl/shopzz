package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 短信平台表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Getter
@Setter
@TableName("sms_platform")
@ApiModel(value = "Platform对象", description = "短信平台表")
public class Platform implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("平台名称")
    private String name;

    @ApiModelProperty("秘钥id")
    private String accessKeyId;

    @ApiModelProperty("秘钥密码")
    private String accessKeySecret;

    @ApiModelProperty("IP绑定,多个以逗号分隔")
    private String ipAddress;

    @ApiModelProperty("是否需要鉴权: 0-不需要 1-需要")
    private Integer needAuth;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
