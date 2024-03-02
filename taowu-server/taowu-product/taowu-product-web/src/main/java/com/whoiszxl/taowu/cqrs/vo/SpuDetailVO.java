package com.whoiszxl.taowu.cqrs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 商品详情页数据表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "SpuDetail VO")
public class SpuDetailVO implements Serializable {

    @Schema(description = "PC商品详情富文本内容")
    private String detailHtml;

    @Schema(description = "移动端商品详情富文本内容")
    private String detailMobile;


}
