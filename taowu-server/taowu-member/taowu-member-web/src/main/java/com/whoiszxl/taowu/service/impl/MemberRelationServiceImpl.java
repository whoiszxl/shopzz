package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.AssertUtils;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.entity.MemberAttention;
import com.whoiszxl.taowu.entity.MemberFollower;
import com.whoiszxl.taowu.mapper.MemberAttentionMapper;
import com.whoiszxl.taowu.service.IMemberRelationService;
import com.whoiszxl.taowu.events.AttentionEvent;
import com.whoiszxl.taowu.mapper.MemberFollowerMapper;
import com.whoiszxl.taowu.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRelationServiceImpl implements IMemberRelationService {


    private final MemberFollowerMapper memberFollowerMapper;

    private final MemberAttentionMapper memberAttentionMapper;

    private final MemberMapper memberMapper;

    private final IdWorker idWorker;

    private final TokenHelper tokenHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean attention(Long attentionMemberId) {
        Long currentMemberId = tokenHelper.getAppMemberId();

        //1. 校验有效性
        AssertUtils.isTrue(ObjUtil.notEqual(currentMemberId, attentionMemberId), "不能关注自己");
        AssertUtils.isNotNull(memberMapper.selectById(attentionMemberId), "用户不存在");

        //2. 保存关系
        MemberAttention memberAttention = memberAttentionMapper.selectOne(Wrappers.<MemberAttention>lambdaQuery()
                .eq(MemberAttention::getMemberId, currentMemberId));
        AssertUtils.isNull(memberAttention, "已关注此用户");

        long relationId = idWorker.nextId();
        memberAttention = MemberAttention.builder().id(relationId).memberId(currentMemberId).attentionId(attentionMemberId).build();
        MemberFollower memberFollower = MemberFollower.builder().id(relationId).memberId(attentionMemberId).followerId(currentMemberId).build();
        memberAttentionMapper.insert(memberAttention);
        memberFollowerMapper.insert(memberFollower);

        //发布关注事件
        SpringUtil.getApplicationContext().publishEvent(new AttentionEvent(this, currentMemberId,  attentionMemberId));
        return true;
    }

    @Override
    public Boolean unattention(Long attentionMemberId) {
        Long currentMemberId = tokenHelper.getAppMemberId();

        //1. 校验有效性
        AssertUtils.isTrue(ObjUtil.notEqual(currentMemberId, attentionMemberId), "不能取关自己");
        AssertUtils.isNotNull(memberMapper.selectById(attentionMemberId), "用户不存在");

        memberAttentionMapper.delete(Wrappers.<MemberAttention>lambdaUpdate()
                .eq(MemberAttention::getMemberId, currentMemberId)
                .eq(MemberAttention::getAttentionId, attentionMemberId));
        memberFollowerMapper.delete(Wrappers.<MemberFollower>lambdaUpdate()
                .eq(MemberFollower::getMemberId, attentionMemberId)
                .eq(MemberFollower::getFollowerId, currentMemberId));

        return true;
    }
}
