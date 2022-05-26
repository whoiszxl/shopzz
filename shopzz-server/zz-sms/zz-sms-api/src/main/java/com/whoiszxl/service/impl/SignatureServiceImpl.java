package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Signature;
import com.whoiszxl.mapper.SignatureMapper;
import com.whoiszxl.service.SignatureService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信签名表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class SignatureServiceImpl extends ServiceImpl<SignatureMapper, Signature> implements SignatureService {

}
