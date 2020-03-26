package com.whoiszxl.product.entity.vo;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 品牌搜索Vo
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Data
@ApiModel(value="BrandSearchVo", description="品牌搜索Vo")
public class BrandSearchVo implements Serializable {

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌首字母")
    private String letter;

    @ApiModelProperty(value = "页码")
    private Long page;

    @ApiModelProperty(value = "页数")
    private Long size;

}
