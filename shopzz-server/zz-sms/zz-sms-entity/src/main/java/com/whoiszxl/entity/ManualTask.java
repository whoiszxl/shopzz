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
 * 短信人工处理任务表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Getter
@Setter
@TableName("sms_manual_task")
@ApiModel(value = "ManualTask对象", description = "短信人工处理任务表")
public class ManualTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("模板")
    private String template;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("请求参数")
    private String request;

    @ApiModelProperty("渠道ID集合")
    private String channelIds;

    @ApiModelProperty("状态: 0-新建 1-处理中 2-处理成功 3-处理失败")
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
