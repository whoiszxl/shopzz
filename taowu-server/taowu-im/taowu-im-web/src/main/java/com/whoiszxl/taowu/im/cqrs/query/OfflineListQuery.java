package com.whoiszxl.taowu.im.cqrs.query;

import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 离线消息列表查询参数
 * @author whoiszxl
 */
@Data
@Schema(description = "对话查询参数")
public class OfflineListQuery extends PageQuery {

    @Schema(description = "客户端当前离线消息最大的序列号")
    private String clientSequence;


}
