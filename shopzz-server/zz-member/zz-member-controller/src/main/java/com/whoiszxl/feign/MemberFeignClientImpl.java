package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.command.MemberAddressApplicationService;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.MemberAddressFeignDTO;
import com.whoiszxl.query.MemberAddressQueryApplicationService;
import com.whoiszxl.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员feign接口实现
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
    public ResponseResult<MemberAddressFeignDTO> getMemberAddress(Long addressId) {
        Long memberId = AuthUtils.getMemberId();
        MemberAddressFeignDTO memberAddressFeignDTO = memberAddressQueryApplicationService.getMemberAddress(memberId, addressId);
        return ResponseResult.buildSuccess(memberAddressFeignDTO);
    }
}
