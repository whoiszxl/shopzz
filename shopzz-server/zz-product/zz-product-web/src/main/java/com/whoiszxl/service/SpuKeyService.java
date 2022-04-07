package com.whoiszxl.service;

import com.whoiszxl.cqrs.dto.SpuAttrDTO;
import com.whoiszxl.entity.SpuKey;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SPU与属性key关联表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface SpuKeyService extends IService<SpuKey> {


    List<SpuAttrDTO> listAttributes(Long spuId);
}
