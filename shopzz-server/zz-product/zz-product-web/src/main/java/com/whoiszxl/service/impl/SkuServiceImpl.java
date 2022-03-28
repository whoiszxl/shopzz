package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.cqrs.command.SkuSaveCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.entity.SkuAttribute;
import com.whoiszxl.entity.Spu;
import com.whoiszxl.mapper.SkuMapper;
import com.whoiszxl.service.SkuAttributeService;
import com.whoiszxl.service.SkuService;
import com.whoiszxl.service.SpuService;
import com.whoiszxl.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sku信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuAttributeService skuAttributeService;

    @Override
    @Transactional
    public void save(SkuSaveCommand skuSaveCommand) {
        //1. 创建SKU实体，生成skuCode
        Long spuId = skuSaveCommand.getSpuId();
        Sku sku = dozerUtils.map(skuSaveCommand, Sku.class);
        String skuCode = buildSkuCode(spuId, skuSaveCommand.getSkuAttributeList());
        sku.setSkuCode(skuCode);

        //2. 设置sku的销售属性json
        String attributeJson = JsonUtil.toJson(skuSaveCommand.getSkuAttributeList());
        sku.setSaleAttr(attributeJson);

        //3. 配置sku的上两级分类
        Spu spu = spuService.getById(spuId);
        sku.setCategoryId(spu.getCategoryId());
        sku.setParentCategoryId(spu.getParentCategoryId());

        //4. 保存sku
        this.save(sku);
        Long skuId = sku.getId();

        //5. 保存sku的属性到库中
        List<SkuAttribute> skuAttributeList = new ArrayList<>();
        skuSaveCommand.getSkuAttributeList().forEach(skuAttr -> {
            SkuAttribute skuAttribute = new SkuAttribute();
            skuAttribute.setSpuId(spuId);
            skuAttribute.setSkuId(skuId);
            skuAttribute.setKeyId(skuAttr.getKeyId());
            skuAttribute.setValueId(skuAttr.getValueId());
            skuAttributeList.add(skuAttribute);
        });
        skuAttributeService.saveBatch(skuAttributeList);

    }

    /**
     * 构建SKU编码
     * @param spuId SPU ID
     * @param skuAttributeList 属于SKU的属性列表
     * @return spuId$keyId-valueId#keyId=valueId   1$101-201#102-202
     */
    private String buildSkuCode(Long spuId, List<SkuSaveCommand.SkuAttribute> skuAttributeList) {
        StringBuilder sb = new StringBuilder("");

        sb.append(spuId).append("$");
        skuAttributeList.forEach(attr -> {
            sb.append(attr.getKeyId()).append("-");
            sb.append(attr.getValueId()).append("#");
        });

        return sb.substring(0, sb.length() - 1);
    }

}
