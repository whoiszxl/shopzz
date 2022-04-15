package com.whoiszxl.query.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.aggregate.repository.MemberAddressRepository;
import com.whoiszxl.db.mapper.MemberAddressMapper;
import com.whoiszxl.db.model.MemberAddressPO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.MemberAddressFeignDTO;
import com.whoiszxl.query.MemberAddressQueryApplicationService;
import com.whoiszxl.query.model.response.MemberAddressListResponse;
import com.whoiszxl.query.model.response.MemberAddressVO;
import com.whoiszxl.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户地址查询应用服务接口实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Service
public class MemberAddressQueryApplicationServiceImpl implements MemberAddressQueryApplicationService {

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private MemberAddressMapper memberAddressMapper;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public MemberAddressListResponse list() {
        Long memberId = AuthUtils.getMemberId();
        LambdaQueryWrapper<MemberAddressPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddressPO::getMemberId, memberId);
        queryWrapper.orderByDesc(MemberAddressPO::getUpdatedAt);
        queryWrapper.orderByDesc(MemberAddressPO::getIsDefault);
        List<MemberAddressPO> memberAddressPOList = memberAddressMapper.selectList(queryWrapper);
        List<MemberAddressVO> memberAddressVOList = dozerUtils.mapList(memberAddressPOList, MemberAddressVO.class);

        MemberAddressListResponse response = new MemberAddressListResponse();
        response.setMainAddress(memberAddressVOList.get(0));
        memberAddressVOList.remove(0);
        response.setAddressList(memberAddressVOList);
        return response;
    }

    @Override
    public MemberAddressFeignDTO getMemberAddress(Long memberId, Long addressId) {
        LambdaQueryWrapper<MemberAddressPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddressPO::getMemberId, memberId);
        queryWrapper.eq(MemberAddressPO::getId, addressId);
        MemberAddressPO memberAddressPO = memberAddressMapper.selectOne(queryWrapper);

        MemberAddressFeignDTO memberAddressFeignDTO = dozerUtils.map(memberAddressPO, MemberAddressFeignDTO.class);
        return memberAddressFeignDTO;
    }
}
