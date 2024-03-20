package com.whoiszxl.taowu.im.controller;


import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.im.cqrs.command.GroupCreateCommand;
import com.whoiszxl.taowu.im.cqrs.command.GroupMemberAddCommand;
import com.whoiszxl.taowu.im.cqrs.query.GroupInfoQuery;
import com.whoiszxl.taowu.im.cqrs.response.FriendImportMultiResultResponse;
import com.whoiszxl.taowu.im.cqrs.response.GroupInfoResponse;
import com.whoiszxl.taowu.im.cqrs.response.GroupJoinedListResponse;
import com.whoiszxl.taowu.im.service.IGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 群组表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Slf4j
@Tag(name = "IM群组 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final IGroupService groupService;

    /**
     * 创建群组
     * 参考:<a href="https://cloud.tencent.com/document/product/269/1615">创建群组</a>
     * @return 创建结果
     */
    @PostMapping("/create")
    @Operation(summary = "创建群组", description = "创建群组")
    public ResponseResult<FriendImportMultiResultResponse> groupCreate(@Validated @RequestBody GroupCreateCommand command) {
        Boolean flag = groupService.groupCreate(command);
        return ResponseResult.buildByFlag(flag);
    }

    @PostMapping("/info")
    @Operation(summary = "获取群组信息", description = "获取群组信息")
    public ResponseResult<GroupInfoResponse> groupInfo(@Validated @RequestBody GroupInfoQuery query) {
        GroupInfoResponse response = groupService.groupInfo(query);
        return ResponseResult.buildSuccess(response);
    }

    @PostMapping("/joined/list")
    @Operation(summary = "获取已加入的群组列表", description = "获取已加入的群组列表")
    public ResponseResult<GroupJoinedListResponse> groupJoinedList() {
        GroupJoinedListResponse groupJoinedListResponse = groupService.groupJoinedList();
        return ResponseResult.buildSuccess(groupJoinedListResponse);
    }

    @PostMapping("/member/add")
    @Operation(summary = "邀请朋友加入群组", description = "邀请朋友加入群组")
    public ResponseResult<GroupInfoResponse> groupMemberAdd(@Validated @RequestBody GroupMemberAddCommand command) {
        Boolean flag = groupService.groupMemberAdd(command);
        return ResponseResult.buildByFlag(flag);
    }




}

