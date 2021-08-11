package com.whoiszxl.client;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.dto.MemberDTO;
import com.whoiszxl.dto.MemberDetailDTO;
import com.whoiszxl.dto.MemberInfoDTO;
import com.whoiszxl.entity.MemberAddress;
import com.whoiszxl.entity.MemberInfo;
import com.whoiszxl.entity.MemberPoint;
import com.whoiszxl.entity.vo.MemberDetailVO;
import com.whoiszxl.enums.CloneDirectionEnum;
import com.whoiszxl.feign.MemberFeignClient;
import com.whoiszxl.service.MemberAddressService;
import com.whoiszxl.service.MemberPointLogService;
import com.whoiszxl.service.MemberPointService;
import com.whoiszxl.service.MemberService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员feign实现
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@RestController
public class MemberFeignClientImpl implements MemberFeignClient {

    @Autowired
    private MemberAddressService memberAddressService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberPointService memberPointService;

    @Autowired
    private MemberPointLogService memberPointLogService;

    @Override
    public List<MemberAddressDTO> listMemberAddress() {
        LambdaQueryWrapper<MemberAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddress::getMemberId, StpUtil.getLoginIdAsLong());
        List<MemberAddress> memberAddressList = memberAddressService.list(queryWrapper);
        return BeanCopierUtils.copyListProperties(memberAddressList, MemberAddressDTO::new);
    }

    @Override
    public MemberDetailDTO getMemberInfo() {
        MemberDetailVO memberDetailVO = memberService.memberInfo();
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();

        MemberDTO memberDTO = memberDetailVO.getMember().clone(MemberDTO.class);
        MemberInfoDTO memberInfoDTO = memberDetailVO.getMemberInfo().clone(MemberInfoDTO.class);
        memberDetailDTO.setMember(memberDTO);
        memberDetailDTO.setMemberInfo(memberInfoDTO);
        return memberDetailDTO;
    }

    @Override
    public MemberAddressDTO getMemberAddress(String addressId) {
        LambdaQueryWrapper<MemberAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddress::getMemberId, StpUtil.getLoginIdAsLong());
        queryWrapper.eq(MemberAddress::getId, addressId);
        MemberAddress memberAddress = memberAddressService.getOne(queryWrapper);
        return memberAddress.clone(MemberAddressDTO.class);
    }


    @Override
    public ResponseResult<Boolean> notifyPayOrderSuccess(Long memberId, BigDecimal totalAmount) {
        //TODO 暂定更新积分为totalAmount*10

        return null;
    }
}
