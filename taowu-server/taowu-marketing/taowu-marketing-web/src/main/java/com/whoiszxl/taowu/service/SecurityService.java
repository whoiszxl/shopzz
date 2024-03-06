package com.whoiszxl.taowu.service;

/**
 * 风控服务接口
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
public interface SecurityService {

    /**
     * 风控检查
     * @param memberId
     * @return
     */
    boolean inspectRisk(Long memberId);

}
