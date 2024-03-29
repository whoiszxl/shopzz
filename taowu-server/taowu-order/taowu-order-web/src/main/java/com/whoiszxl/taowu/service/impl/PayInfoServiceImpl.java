package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.entity.PayInfo;
import com.whoiszxl.taowu.mapper.PayInfoMapper;
import com.whoiszxl.taowu.service.PayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements PayInfoService {

}
