package com.whoiszxl.feign;

import com.whoiszxl.command.MemberAddressApplicationService;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.query.MemberAddressQueryApplicationService;
import com.whoiszxl.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/4/11
 */
@RestController
public class MemberFeignClientImpl implements MemberFeignClient {


    @Autowired
    private MemberAddressApplicationService memberAddressApplicationService;

    @Autowired
    private MemberAddressQueryApplicationService memberAddressQueryApplicationService;


    @Autowired
    private DozerUtils dozerUtils;


    @Override
    public MemberAddressDTO getMemberAddress(Long addressId) {
        Long memberId = AuthUtils.getMemberId();
        MemberAddressDTO memberAddressDTO = memberAddressQueryApplicationService.getMemberAddress(memberId, addressId);
        return memberAddressDTO;
    }
}
