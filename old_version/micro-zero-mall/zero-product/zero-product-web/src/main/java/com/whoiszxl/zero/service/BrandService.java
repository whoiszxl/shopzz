package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.dto.BrandDTO;

import java.util.List;

public interface BrandService {

    /**
     * 查找所有品牌
     * @return
     */
    List<BrandDTO> findAll();
}
