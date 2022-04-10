package com.whoiszxl.service.impl;

import com.whoiszxl.entity.PayInfo;
import com.whoiszxl.mapper.PayInfoMapper;
import com.whoiszxl.service.PayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方支付信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements PayInfoService {

}
