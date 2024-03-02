package com.whoiszxl.taowu.im.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.im.constants.GroupMemberTypeEnum;
import com.whoiszxl.taowu.im.cqrs.command.GroupCreateCommand;
import com.whoiszxl.taowu.im.cqrs.command.GroupMemberAddCommand;
import com.whoiszxl.taowu.im.cqrs.dto.AddMemberToGroupDto;
import com.whoiszxl.taowu.im.cqrs.query.GroupInfoQuery;
import com.whoiszxl.taowu.im.cqrs.response.GroupInfoResponse;
import com.whoiszxl.taowu.im.cqrs.response.GroupJoinedListResponse;
import com.whoiszxl.taowu.im.entity.Group;
import com.whoiszxl.taowu.im.entity.GroupMember;
import com.whoiszxl.taowu.im.mapper.GroupMapper;
import com.whoiszxl.taowu.im.service.IGroupMemberService;
import com.whoiszxl.taowu.im.service.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 群组表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    private final IGroupMemberService groupMemberService;

    private final TokenHelper tokenHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean groupCreate(GroupCreateCommand command) {
        Long currentMemberId = tokenHelper.getAppMemberId();
        //1. 创建群组
        Group group = BeanUtil.copyProperties(command, Group.class);
        group.setId(IdUtil.getSnowflakeNextId());
        group.setMemberCount(command.getMemberList().size() + 1);
        group.setMaxMemberCount(500);
        group.setGroupOwnerId(currentMemberId);
        boolean saveFlag = this.save(group);
        Assert.isTrue(saveFlag, "创建群组失败");

        //2. 将群主添加到群组里
        AddMemberToGroupDto addMemberToGroupDto = AddMemberToGroupDto.builder()
                .memberId(currentMemberId)
                .groupId(group.getId())
                .memberType(GroupMemberTypeEnum.OWNER.getCode())
                .build();
        groupMemberService.addMemberToGroup(addMemberToGroupDto);

        //3. 将其他成员添加到群组里
        for (AddMemberToGroupDto memberToGroupDto : command.getMemberList()) {
            groupMemberService.addMemberToGroup(memberToGroupDto);
        }

        return true;
    }

    @Override
    public GroupInfoResponse groupInfo(GroupInfoQuery query) {
        Group group = this.lambdaQuery().eq(Group::getId, query.getGroupId()).one();
        Assert.isTrue(group != null, "群组不存在");

        GroupInfoResponse groupInfoResponse = BeanUtil.copyProperties(group, GroupInfoResponse.class);

        List<GroupMember> memberList = groupMemberService.lambdaQuery()
                .eq(GroupMember::getGroupId, query.getGroupId())
                .list();
        List<AddMemberToGroupDto> addMemberToGroupDtoList = BeanUtil.copyToList(memberList, AddMemberToGroupDto.class);
        groupInfoResponse.setMemberList(addMemberToGroupDtoList);
        return groupInfoResponse;
    }

    @Override
    public GroupJoinedListResponse groupJoinedList() {
        Long memberId = tokenHelper.getAppMemberId();
        List<GroupMember> memberList = groupMemberService.lambdaQuery()
                .eq(GroupMember::getMemberId, memberId).list();

        if(CollUtil.isEmpty(memberList)) {
            return GroupJoinedListResponse.builder()
                    .groupList(CollUtil.newArrayList())
                    .total(0).build();
        }

        List<Long> groupIdList = memberList.stream()
                .map(GroupMember::getGroupId).collect(Collectors.toList());

        List<Group> groupList = this.lambdaQuery()
                .in(Group::getId, groupIdList).list();

        return GroupJoinedListResponse.builder()
                .groupList(groupList)
                .total(groupList.size()).build();
    }


    @Override
    public Boolean groupMemberAdd(GroupMemberAddCommand command) {
        Long memberId = tokenHelper.getAppMemberId();
        Group group = this.getOne(Wrappers.<Group>lambdaQuery()
                .eq(Group::getGroupOwnerId, memberId)
                .eq(Group::getId, command.getGroupId()));
        Assert.isTrue(group != null, "群组不存在");

        List<AddMemberToGroupDto> memberList = command.getMemberList();
        for (AddMemberToGroupDto addMemberToGroupDto : memberList) {
            groupMemberService.addMemberToGroup(addMemberToGroupDto);
        }
        return true;
    }
}
