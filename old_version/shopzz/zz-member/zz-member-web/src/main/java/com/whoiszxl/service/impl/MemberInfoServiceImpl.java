package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.whoiszxl.entity.MemberInfo;
import com.whoiszxl.mapper.MemberInfoMapper;
import com.whoiszxl.service.MemberInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员详情表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements MemberInfoService {

    @Override
    public MemberInfo getByMemberId(long memberId) {
        LambdaQueryWrapper<MemberInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberInfo::getMemberId, memberId);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateByMemberId(MemberInfo memberInfo) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id", memberInfo.getMemberId());
        return this.update(memberInfo, updateWrapper);
    }
}
