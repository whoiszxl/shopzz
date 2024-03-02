package com.whoiszxl.taowu.im.cqrs.command;

import com.whoiszxl.taowu.im.cqrs.dto.AddMemberToGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 群组创建命令
 * @author whoiszxl
 */
@Data
@Schema(description = "群组创建命令")
public class GroupCreateCommand {

    @Schema(description = "群组名称")
    @NotBlank(message = "群组名称不能为空")
    private String groupName;

    @Schema(description = "群组类型")
    @NotNull(message = "群组类型不能为空")
    private Integer groupType;

    @Schema(description = "是否禁言: 1-不禁言 2-禁言")
    private Integer isMute;

    @Schema(description = "群组头像")
    private String portrait;

    @Schema(description = "加群审核类型: 1-任何人可加入 2-需要群主审批 3-禁止任何人加入")
    private Integer joinVerificationType;

    @Schema(description = "扩展信息")
    private String extra;

    @Schema(description = "群组创建时需要添加到群组的成员列表")
    private List<AddMemberToGroupDto> memberList;

}
