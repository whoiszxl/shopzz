package com.whoiszxl.taowu.im.service;

import com.whoiszxl.taowu.im.cqrs.command.GroupCreateCommand;
import com.whoiszxl.taowu.im.cqrs.command.GroupMemberAddCommand;
import com.whoiszxl.taowu.im.cqrs.query.GroupInfoQuery;
import com.whoiszxl.taowu.im.cqrs.response.GroupInfoResponse;
import com.whoiszxl.taowu.im.cqrs.response.GroupJoinedListResponse;
import com.whoiszxl.taowu.im.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 群组表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
public interface IGroupService extends IService<Group> {

    /**
     * 创建群组
     * @param command 创建命令
     * @return 是否创建成功
     */
    Boolean groupCreate(GroupCreateCommand command);

    /**
     * 获取群组信息
     * @param query 查询参数
     * @return 群组信息
     */
    GroupInfoResponse groupInfo(GroupInfoQuery query);

    /**
     * 成员加入群组列表查询
     * @return 成员加入群组列表
     */
    GroupJoinedListResponse groupJoinedList();

    /**
     * 邀请朋友加入群组
     * @param command 邀请命令
     * @return 是否邀请成功
     */
    Boolean groupMemberAdd(GroupMemberAddCommand command);
}
