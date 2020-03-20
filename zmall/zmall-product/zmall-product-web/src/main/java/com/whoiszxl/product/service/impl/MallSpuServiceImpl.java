package com.whoiszxl.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.product.mapper.MallSpuMapper;
import com.whoiszxl.product.entity.MallSpu;
import com.whoiszxl.product.service.MallSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Slf4j
@Service
@Transactional
public class MallSpuServiceImpl extends ServiceImpl<MallSpuMapper, MallSpu> implements MallSpuService {

}
