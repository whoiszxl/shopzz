package com.whoiszxl.service.impl;

import com.whoiszxl.entity.MemberAddress;
import com.whoiszxl.mapper.MemberAddressMapper;
import com.whoiszxl.service.MemberAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@Service
public class MemberAddressServiceImpl extends ServiceImpl<MemberAddressMapper, MemberAddress> implements MemberAddressService {

}
