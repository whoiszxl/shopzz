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

    @Schema(description = "计数类型: 0-获赞数 1-评论数 2-分享数 3-浏览数")
    private Integer type;

    @Schema(description = "运算类型: -1-减一 1-加一")
    private Long operator;

    @Schema(description = "视频ID")
    private Long videoId;
}
