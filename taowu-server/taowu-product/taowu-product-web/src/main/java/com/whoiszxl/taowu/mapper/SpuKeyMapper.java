package com.whoiszxl.taowu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.taowu.cqrs.dto.SpuAttrDTO;
import com.whoiszxl.taowu.entity.SpuKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * SPU与属性key关联表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface SpuKeyMapper extends BaseMapper<SpuKey> {

    @Select("select pav.key_id as keyId, pak.`name` as `key`, pav.id as valueId, pav.`value` as `value` " +
            "from pms_spu as ps " +
            "left join pms_spu_key as psk on ps.id = psk.spu_id " +
            "left join pms_attribute_value as pav on psk.key_id = pav.key_id " +
            "left join pms_attribute_key as pak on psk.key_id = pak.id " +
            "where ps.id = #{spuId}")
    List<SpuAttrDTO> listAttributes(Long spuId);
}
