package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 营销推荐表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-15
 */
@Getter
@Setter
@TableName("rms_recommend")
@ApiModel(value = "Recommend对象", description = "营销推荐表")
public class Recommend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("推荐商品ID")
    private Long productId;

    @ApiModelProperty("推荐商品名称")
    private String productName;

    @ApiModelProperty("默认图片")
    private String defaultPic;

    @ApiModelProperty("默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty("推荐商品类型 1:热门商品 2:精选商品")
    private Boolean type;

    @ApiModelProperty("上下线状态：0->下线；1->上线")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
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
