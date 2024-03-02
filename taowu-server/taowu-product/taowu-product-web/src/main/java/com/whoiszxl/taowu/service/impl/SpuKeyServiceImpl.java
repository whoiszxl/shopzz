package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.cqrs.dto.SpuAttrDTO;
import com.whoiszxl.taowu.entity.SpuKey;
import com.whoiszxl.taowu.mapper.SpuKeyMapper;
import com.whoiszxl.taowu.service.SpuKeyService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SpuKeyServiceImpl extends ServiceImpl<SpuKeyMapper, SpuKey> implements SpuKeyService {

    private final SpuKeyMapper spuKeyMapper;

    @Override
    public List<SpuAttrDTO> listAttributes(Long spuId) {
        return spuKeyMapper.listAttributes(spuId);
    }
}
