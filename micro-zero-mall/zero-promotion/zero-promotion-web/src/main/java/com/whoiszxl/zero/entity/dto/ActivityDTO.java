package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
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
public class ActivityDTO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = -26601356313134396L;
    /**
    * 自增主键ID
    */
    private Integer id;
    /**
    * 促销活动名称
    */
    private String name;
    /**
    * 促销活动开始时间
    */
    private Date startTime;
    /**
    * 促销活动结束时间
    */
    private Date endTime;
    /**
    * 促销活动说明备注
    */
    private String remark;
    /**
    * 促销活动的状态，1：启用，2：停用
    */
    private Integer status;
    /**
    * 促销活动的规则
    */
    private String rule;
    /**
    * 促销活动的类型，1：满减；2：多买优惠；3：单品促销；4：满赠促销；5：赠品促销
    */
    private Integer type;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}