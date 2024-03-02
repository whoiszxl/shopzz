package com.whoiszxl.taowu.im.cqrs.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 群组信息查询参数
 * @author whoiszxl
 */
@Data
@Schema(description = "群组信息查询参数")
public class GroupInfoQuery {

    @Schema(description = "群组ID")
    @NotBlank(message = "群组ID不能为空")
    private String groupId;

}
