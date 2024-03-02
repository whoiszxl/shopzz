package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.SpuDetail;
import com.whoiszxl.taowu.mapper.SpuDetailMapper;
import com.whoiszxl.taowu.service.SpuDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品详情页数据表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
@RequiredArgsConstructor
public class SpuDetailServiceImpl extends ServiceImpl<SpuDetailMapper, SpuDetail> implements SpuDetailService {

}
