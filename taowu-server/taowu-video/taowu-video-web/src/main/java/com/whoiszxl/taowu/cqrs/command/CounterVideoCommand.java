package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员计数命令
 * @author whoiszxl
 */
@Data
@Schema(description = "视频计数命令")
public class CounterVideoCommand {

    @Schema(description = "计数类型: 1:观看量 2:转发量 3:评论量 4:点赞量")
    private Integer type;

    @Schema(description = "运算类型: -1-减一 1-加一")
    private Long operator;

    @Schema(description = "视频ID")
    private Long videoId;
}
