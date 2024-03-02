package com.whoiszxl.taowu.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息内容表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Data
@TableName("im_message_content")
@Schema(description = "消息内容表")
public class MessageContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "实际消息体")
    private String messageContent;

    @Schema(description = "扩展信息")
    private String extra;

    @Schema(description = "撤回状态: 1-已撤回 0-未撤回")
    private Integer removeStatus;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
