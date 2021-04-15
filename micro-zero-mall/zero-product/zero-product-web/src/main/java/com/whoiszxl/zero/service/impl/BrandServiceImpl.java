package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.BrandDao;
import com.whoiszxl.zero.entity.Brand;
import com.whoiszxl.zero.entity.dto.BrandDTO;
import com.whoiszxl.zero.service.BrandService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public List<BrandDTO> findAll() {
        List<Brand> brands = brandDao.findAll();
        return BeanCopierUtils.copyListProperties(brands, BrandDTO::new);
    }
}
