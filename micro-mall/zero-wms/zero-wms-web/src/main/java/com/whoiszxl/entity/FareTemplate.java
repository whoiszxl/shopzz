package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 运费模板表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@TableName("wms_fare_template")
@ApiModel(value="FareTemplate对象", description="运费模板表")
public class FareTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "运费模板名称")
    private String templateName;

    @ApiModelProperty(value = "运费模板类型，1：固定运费，2：满X元包邮，3：自定义规则")
    private Integer templateType;

    @ApiModelProperty(value = "运费模板的规则")
    private String templateRule;

    @ApiModelProperty(value = "运费模板的说明备注")
    private String templateComment;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getTemplateRule() {
        return templateRule;
    }

    public void setTemplateRule(String templateRule) {
        this.templateRule = templateRule;
    }

    public String getTemplateComment() {
        return templateComment;
    }

    public void setTemplateComment(String templateComment) {
        this.templateComment = templateComment;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "FareTemplate{" +
        "id=" + id +
        ", templateName=" + templateName +
        ", templateType=" + templateType +
        ", templateRule=" + templateRule +
        ", templateComment=" + templateComment +
        ", version=" + version +
        ", isDeleted=" + isDeleted +
        ", createdBy=" + createdBy +
        ", updatedBy=" + updatedBy +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
