package com.whoiszxl.client;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.entity.MemberAddress;
import com.whoiszxl.entity.vo.MemberAddressVO;
import com.whoiszxl.feign.MemberFeignClient;
import com.whoiszxl.service.MemberAddressService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public List<MemberAddressDTO> listMemberAddress() {
        LambdaQueryWrapper<MemberAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddress::getMemberId, StpUtil.getLoginIdAsLong());
        List<MemberAddress> memberAddressList = memberAddressService.list(queryWrapper);
        return BeanCopierUtils.copyListProperties(memberAddressList, MemberAddressDTO::new);
    }
}
