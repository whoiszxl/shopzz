package com.whoiszxl.service.impl;

import com.whoiszxl.service.SecurityService;
import org.springframework.stereotype.Service;

/**
 * 默认风控服务实现
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
@Service
public class DefaultSecurityServiceImpl implements SecurityService {

    @Override
    public boolean inspectRisk(Long memberId) {
        //TODO 风控实现
        return true;
    }
}
