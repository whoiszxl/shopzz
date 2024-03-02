package com.whoiszxl.taowu.im.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.LoggerUtil;
import com.whoiszxl.taowu.im.constants.GroupMemberStatusEnum;
import com.whoiszxl.taowu.im.constants.GroupMemberTypeEnum;
import com.whoiszxl.taowu.im.cqrs.dto.AddMemberToGroupDto;
import com.whoiszxl.taowu.im.entity.Group;
import com.whoiszxl.taowu.im.entity.GroupMember;
import com.whoiszxl.taowu.im.mapper.GroupMapper;
import com.whoiszxl.taowu.im.mapper.GroupMemberMapper;
import com.whoiszxl.taowu.im.service.IGroupMemberService;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 群组表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember> implements IGroupMemberService {

    private final Snowflake snowflake;

    private final GroupMapper groupMapper;

    private final MemberFeignClient memberFeignClient;

    @Override
    public void addMemberToGroup(AddMemberToGroupDto addMemberToGroupDto) {
        //1. 校验添加的群成员的账号是否存在
        ResponseResult<MemberDTO> memberInfoRes = memberFeignClient.getMemberInfoById(addMemberToGroupDto.getMemberId());
        boolean existsFlag = memberInfoRes.getData() != null;
        Assert.isTrue(existsFlag, addMemberToGroupDto.getMemberId() + "群成员不存在");

        //2. 校验群组里群主只能有一个
        if(ObjUtil.equal(GroupMemberTypeEnum.OWNER.getCode(), addMemberToGroupDto.getMemberType())) {
            existsFlag = this.lambdaQuery()
                    .eq(GroupMember::getGroupId, addMemberToGroupDto.getGroupId())
                    .eq(GroupMember::getMemberType, GroupMemberTypeEnum.OWNER.getCode()).exists();
            Assert.isFalse(existsFlag, "群组已有管理员");
        }

        GroupMember groupMember = this.lambdaQuery()
                .eq(GroupMember::getGroupId, addMemberToGroupDto.getGroupId())
                .eq(GroupMember::getMemberId, addMemberToGroupDto.getMemberId()).one();

        if(groupMember == null) {
            //3. 群组中不存在用户，属于第一次加群
            GroupMember saveMember = BeanUtil.copyProperties(addMemberToGroupDto, GroupMember.class);
            saveMember.setId(snowflake.nextId());
            saveMember.setGroupId(addMemberToGroupDto.getGroupId());
            saveMember.setJoinAt(LocalDateTime.now());
            boolean saveFlag = this.save(saveMember);
            Assert.isTrue(saveFlag, "用户加群失败1");
        }else if(ObjUtil.equal(groupMember.getStatus(), GroupMemberStatusEnum.LEAVE.getCode())) {
            //4. 成员为离开状态，属于重新进群
            GroupMember saveMember = BeanUtil.copyProperties(addMemberToGroupDto, GroupMember.class);
            saveMember.setId(snowflake.nextId());
            saveMember.setGroupId(addMemberToGroupDto.getGroupId());
            saveMember.setJoinAt(LocalDateTime.now());
            boolean saveFlag = this.save(saveMember);
            Assert.isTrue(saveFlag, "用户加群失败2");

        }
    }

    @Override
    public boolean checkGroupMessageSendPermission(Long groupId, Long fromMemberId) {
        Group group = groupMapper.selectById(groupId);
        if(group == null) {
            LoggerUtil.info(log, "GroupMemberServiceImpl", "群消息发送校验失败");
            return false;
        }
        GroupMember groupMember = this.getOne(Wrappers.<GroupMember>lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getMemberId, fromMemberId));
        if(groupMember == null) {
            LoggerUtil.info(log, "GroupMemberServiceImpl", "群消息发送校验失败");
            return false;
        }
        return true;
    }
}
