package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询指定用户视频时间线列表的查询参数
 *
 * @author whoiszxl
 * @date 2022/1/26
 */
@Data
@Schema(description = "查询指定用户视频时间线列表的查询参数")
public class MemberTimelineQuery extends PageQuery {

    @Schema(description = "用户ID")
    private String memberId;
}