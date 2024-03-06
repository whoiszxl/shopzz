package com.whoiszxl.taowu.cqrs.response;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品专栏表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@Schema(description = "商品专栏表")
public class ProductColumnResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "专栏名称")
    private String name;

    @Schema(description = "专栏描述")
    private String descs;

    @Schema(description = "入口图片地址")
    private String enterImg;

    @Schema(description = "内部banner图片地址")
    private String bannerImg;

    @Schema(description = "上下线状态:0->下线;1->上线")
    private Integer status;

    @Schema(description = "点击数")
    private Integer clickCount;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;


}
