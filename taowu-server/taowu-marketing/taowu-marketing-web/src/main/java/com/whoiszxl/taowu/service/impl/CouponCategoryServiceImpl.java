package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.CouponCategory;
import com.whoiszxl.taowu.mapper.CouponCategoryMapper;
import com.whoiszxl.taowu.service.CouponCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券跟分类的关联关系表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
@RequiredArgsConstructor
public class CouponCategoryServiceImpl extends ServiceImpl<CouponCategoryMapper, CouponCategory> implements CouponCategoryService {

}
