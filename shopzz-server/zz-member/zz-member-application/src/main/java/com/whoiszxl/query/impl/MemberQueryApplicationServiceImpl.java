package com.whoiszxl.query.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.db.mapper.MemberInfoMapper;
import com.whoiszxl.db.mapper.MemberMapper;
import com.whoiszxl.db.model.MemberInfoPO;
import com.whoiszxl.db.model.MemberPO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.query.MemberQueryApplicationService;
import com.whoiszxl.query.model.qry.MemberQuery;
import com.whoiszxl.query.model.response.MemberDetailResponse;
import com.whoiszxl.query.model.response.MemberResponse;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户查询应用服务接口实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Service
public class MemberQueryApplicationServiceImpl implements MemberQueryApplicationService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public MemberResponse memberInfo() {
        Long memberId = AuthUtils.getMemberId();
        AssertUtils.isTrue(Objects.nonNull(memberId), "未登录");

        MemberPO memberPO = memberMapper.selectById(memberId);
        MemberInfoPO memberInfoPO = memberInfoMapper.selectOne(Wrappers.<MemberInfoPO>lambdaQuery().eq(MemberInfoPO::getMemberId, memberId));

        MemberResponse memberResponse = dozerUtils.map(memberPO, MemberResponse.class);
        dozerUtils.map(memberInfoPO, memberResponse);
        return memberResponse;
    }


    @Override
    public MemberDetailResponse memberDetailById(String memberId) {
        MemberPO memberPO = memberMapper.selectById(memberId);
        MemberInfoPO memberInfoPO = memberInfoMapper.selectOne(Wrappers.<MemberInfoPO>lambdaQuery().eq(MemberInfoPO::getMemberId, memberId));

        MemberDetailResponse memberDetailResponse = dozerUtils.map(memberPO, MemberDetailResponse.class);
        dozerUtils.map(memberInfoPO, memberDetailResponse);
        return memberDetailResponse;
    }

    @Override
    public IPage<MemberResponse> list(MemberQuery query) {
        LambdaQueryWrapper<MemberPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getUsername())) {
            lambdaQueryWrapper.eq(MemberPO::getUsername, query.getUsername());
        }
        lambdaQueryWrapper.eq(MemberPO::getStatus, StatusEnum.OPEN.getCode());
        Page<MemberPO> memberPOPage = memberMapper.selectPage(new Page<>(query.getPage(), query.getSize()), lambdaQueryWrapper);
        return memberPOPage.convert(e -> dozerUtils.map(e, MemberResponse.class));
    }
}
