package com.whoiszxl.cqrs.query;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 秒杀item表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Getter
@Setter
@TableName("spms_seckill_item")
@ApiModel(value = "SeckillItem对象", description = "秒杀item表")
public class SeckillItemQuery extends PageQuery {

    @ApiModelProperty("关联秒杀表的主键ID")
    private Long seckillId;

    @ApiModelProperty("秒杀SKU名称")
    private String skuName;

    @ApiModelProperty("秒杀SKU是否启动: 0-未启动 1-已启动")
    private Integer status;


}
