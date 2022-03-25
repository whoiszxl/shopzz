package com.whoiszxl.cqrs.query;

import com.baomidou.mybatisplus.annotation.*;
import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@Getter
@Setter
@ApiModel(value = "File对象", description = "文件表")
public class FmsFileQuery extends PageQuery {

    @ApiModelProperty("平台类型: 1-阿里云 2-七牛云 3-百度云 4-AmazonS3")
    private Integer platformType;

    @ApiModelProperty("业务ID")
    private String bizId;

    @ApiModelProperty("业务类型: 1-商品 2-会员 3-wms")
    private Integer bizType;

    @ApiModelProperty("数据类型: 1-目录 2-图片 3-视频 4-音频 5-文档 6-其他")
    private Integer dataType;

}
