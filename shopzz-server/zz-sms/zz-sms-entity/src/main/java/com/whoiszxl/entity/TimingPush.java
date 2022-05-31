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
 * 短信定时发送表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@TableName("sms_timing_push")
@ApiModel(value = "TimingPush对象", description = "短信定时发送表")
public class TimingPush implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("模板")
    private String template;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("模板类型: 1-验证码 2-营销")
    private String request;

    @ApiModelProperty("发送时间")
    private LocalDateTime timing;

    @ApiModelProperty("状态: 0-未处理 1-已处理")
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
