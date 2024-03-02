package com.whoiszxl.taowu.im.service;

import com.whoiszxl.taowu.im.cqrs.dto.AddMemberToGroupDto;
import com.whoiszxl.taowu.im.entity.GroupMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 群组表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
public interface IGroupMemberService extends IService<GroupMember> {

    /**
     * 添加成员到群组
     * @param addMemberToGroupDto 添加参数
     */
    void addMemberToGroup(AddMemberToGroupDto addMemberToGroupDto);


    /**
     * 校验用户是否具有发送消息到群里的权限
     * @param groupId 群组ID
     * @param fromMemberId 发送用户ID
     * @return
     */
    boolean checkGroupMessageSendPermission(Long groupId, Long fromMemberId);
}
