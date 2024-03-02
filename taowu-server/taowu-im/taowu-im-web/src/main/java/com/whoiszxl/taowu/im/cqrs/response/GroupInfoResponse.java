package com.whoiszxl.taowu.im.cqrs.response;

import com.whoiszxl.taowu.im.cqrs.dto.AddMemberToGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 群组信息返回实体
 * @author whoiszxl
 */
@Data
@AllArgsConstructor
@Schema(description = "群组信息返回实体")
public class GroupInfoResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "群主ID")
    private String groupOwnerId;

    @Schema(description = "群组名称")
    private String groupName;

    @Schema(description = "群组类型")
    private Integer groupType;

    @Schema(description = "是否禁言: 1-不禁言 2-禁言")
    private Integer isMute;

    @Schema(description = "群组头像")
    private String portrait;

    @Schema(description = "当前群成员数")
    private Long memberCount;

    @Schema(description = "最大群成员数")
    private Long maxMemberCount;

    @Schema(description = "群公告")
    private String notice;

    @Schema(description = "加群审核类型: 1-任何人可加入 2-需要群主审批 3-禁止任何人加入")
    private Integer joinVerificationType;

    @Schema(description = "扩展信息")
    private String extra;

    @Schema(description = "序列号")
    private Long sequence;

    @Schema(description = "群组状态 1-正常 2-解散")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "更新时间")
    private List<AddMemberToGroupDto> memberList;

}
