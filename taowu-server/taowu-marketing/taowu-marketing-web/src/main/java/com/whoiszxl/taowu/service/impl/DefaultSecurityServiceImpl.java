package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 默认风控服务实现
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
@Service
@RequiredArgsConstructor
public class DefaultSecurityServiceImpl implements SecurityService {

    @Override
    public boolean inspectRisk(Long memberId) {
        //TODO 风控实现
        return true;
    }
}
