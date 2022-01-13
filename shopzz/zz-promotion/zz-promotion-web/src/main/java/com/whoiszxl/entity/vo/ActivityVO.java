package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 促销活动表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Activity对象", description="促销活动表")
public class ActivityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键ID")
    private Integer id;

    @ApiModelProperty(value = "促销活动名称")
    private String name;

    @ApiModelProperty(value = "促销活动开始时间")
    private Date startTime;

    @ApiModelProperty(value = "促销活动结束时间")
    private Date endTime;

    @ApiModelProperty(value = "促销活动说明备注")
    private String remark;

    @ApiModelProperty(value = "促销活动的状态，1：启用，2：停用")
    private Integer status;

    @ApiModelProperty(value = "促销活动的规则")
    private String rule;

    @ApiModelProperty(value = "促销活动的类型，1：满减；2：多买优惠；3：单品促销；4：满赠促销；5：赠品促销")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;


}
