package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Signature;

/**
 * <p>
 * 短信签名表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
public interface SignatureService extends IService<Signature> {

    /**
     * 通过缓存获取签名内容
     * @param signatureCode
     * @return
     */
    Signature getByCode(String signatureCode);
}
