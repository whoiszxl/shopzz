package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 短信通道表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@TableName("sms_channel")
@ApiModel(value = "Channel对象", description = "短信通道表")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("秘钥id")
    private String accessKeyId;

    @ApiModelProperty("秘钥密码")
    private String accessKeySecret;

    @ApiModelProperty("其他配置")
    private String otherConfig;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("类型: 1-文字 2-语音 3-推送")
    private Integer channelType;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
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
