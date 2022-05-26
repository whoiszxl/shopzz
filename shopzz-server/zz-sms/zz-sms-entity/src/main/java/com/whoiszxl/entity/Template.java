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
 * 短信模板表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Getter
@Setter
@TableName("sms_template")
@ApiModel(value = "Template对象", description = "短信模板表")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板编码")
    private String code;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("模板类型: 1-验证码 2-营销")
    private Integer type;

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
