package com.whoiszxl.taowu.file.cqrs.query;

import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@Data
@Schema(description = "文件表")
public class FmsFileQuery extends PageQuery {

    @Schema(description = "平台类型: 1-阿里云 2-七牛云 3-百度云 4-AmazonS3")
    private Integer platform;

    @Schema(description = "业务ID")
    private String objectId;

    @Schema(description = "业务类型: 1-商品 2-会员 3-wms")
    private Integer objectType;

}
