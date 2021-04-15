package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 促销活动表(PromotionActivity)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class ActivityVO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = -26601356313134396L;

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

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

}