package com.whoiszxl.feign;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.dto.MemberDTO;
import com.whoiszxl.dto.MemberDetailDTO;
import com.whoiszxl.dto.MemberInfoDTO;
import com.whoiszxl.entity.MemberAddress;
import com.whoiszxl.entity.vo.MemberDetailVO;
import com.whoiszxl.service.MemberAddressService;
import com.whoiszxl.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@RestController
public class MemberFeignClientImpl implements MemberFeignClient {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAddressService memberAddressService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public ResponseResult<MemberDetailDTO> getMemberInfo() {
        MemberDetailVO memberDetailVO = memberService.memberInfo();
        MemberDTO memberDTO = dozerUtils.map(memberDetailVO.getMember(), MemberDTO.class);
        MemberInfoDTO memberInfoDTO = dozerUtils.map(memberDetailVO.getMemberInfo(), MemberInfoDTO.class);

        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMember(memberDTO);
        memberDetailDTO.setMemberInfo(memberInfoDTO);

        return ResponseResult.buildSuccess(memberDetailDTO);
    }

    @Override
    public MemberAddressDTO getMemberAddress(String addressId) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<MemberAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddress::getMemberId, loginIdAsLong);
        queryWrapper.eq(MemberAddress::getId, addressId);
        MemberAddress memberAddress = memberAddressService.getOne(queryWrapper);
        return dozerUtils.map(memberAddress, MemberAddressDTO.class);
    }

}
