package com.whoiszxl.taowu.im.cqrs.command;

import com.whoiszxl.taowu.im.cqrs.dto.AddMemberToGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 邀请朋友加入群组命令
 * @author whoiszxl
 */
@Data
@Schema(description = "邀请朋友加入群组命令")
public class GroupMemberAddCommand {

    @Schema(description = "群组主键ID")
    private Long groupId;

    @Schema(description = "需要添加到群里的朋友列表")
    private List<AddMemberToGroupDto> memberList;

}
