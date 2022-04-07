package com.whoiszxl.service.impl;

import com.whoiszxl.cqrs.dto.SpuAttrDTO;
import com.whoiszxl.entity.SpuKey;
import com.whoiszxl.mapper.SpuKeyMapper;
import com.whoiszxl.service.SpuKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * SPU与属性key关联表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class SpuKeyServiceImpl extends ServiceImpl<SpuKeyMapper, SpuKey> implements SpuKeyService {

    @Autowired
    private SpuKeyMapper spuKeyMapper;

    @Override
    public List<SpuAttrDTO> listAttributes(Long spuId) {
        return spuKeyMapper.listAttributes(spuId);
    }
}
