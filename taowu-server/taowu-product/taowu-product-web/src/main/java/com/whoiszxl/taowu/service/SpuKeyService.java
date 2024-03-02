package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.cqrs.dto.SpuAttrDTO;
import com.whoiszxl.taowu.entity.SpuKey;

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
