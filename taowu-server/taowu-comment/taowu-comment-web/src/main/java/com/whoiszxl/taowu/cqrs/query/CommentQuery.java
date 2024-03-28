package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论查询参数")
public class CommentQuery extends PageQuery {

    @Schema(description = "所属模块: 1-视频 2-商品")
    private Integer module;

    @Schema(description = "对象ID: 视频 || 商品")
    private Long objId;
    
    @Schema(description = "父评论ID，为 0 则是根评论")
    private Long parentId = 0L;

}
