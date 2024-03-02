package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.utils.LogFormatUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.command.SkuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SpuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.taowu.cqrs.dto.SpuAttrDTO;
import com.whoiszxl.taowu.cqrs.response.IndexSpuResponse;
import com.whoiszxl.taowu.cqrs.response.SpuDetailResponse;
import com.whoiszxl.taowu.entity.*;
import com.whoiszxl.taowu.enums.SpuPublishStatusEnum;
import com.whoiszxl.taowu.mapper.SpuDetailMapper;
import com.whoiszxl.taowu.mapper.SpuMapper;
import com.whoiszxl.taowu.cqrs.vo.*;
import com.whoiszxl.taowu.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品基础信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {
    
    private final SpuImagesService spuImagesService;
    
    private final SpuKeyService spuKeyService;

    private final SpuDetailMapper spuDetailMapper;
    
    private final SkuService skuService;
    
    private final SkuStockService skuStockService;
    
    private final ThreadPoolExecutor executor;

    private final SpuDetailService spuDetailService;

    private final SkuAttributeService skuAttributeService;

    private final SpuMapper spuMapper;

    private final RedisUtils redisUtils;

    Cache<String, List<IndexSpuResponse>> indexSpuListLocalCache = Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .maximumSize(100)
            .build();



    @Override
    @Transactional
    public void save(SpuSaveCommand spuSaveCommand) {
        //新增spu记录到数据库中
        Spu spu = BeanUtil.copyProperties(spuSaveCommand, Spu.class);
        this.save(spu);
        Long spuId = spu.getId();

        //新增spu的图片到数据库
        List<String> imageUrlList = spuSaveCommand.getSpuImageList();
        if(!CollectionUtils.isEmpty(imageUrlList)) {
            List<SpuImages> spuImagesList = imageUrlList.stream().map(img -> {
                SpuImages spuImages = new SpuImages();
                spuImages.setSpuId(spuId);
                spuImages.setImgUrl(img);
                return spuImages;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(spuImagesList);
        }

        //新增商详
        String detailHtml = spuSaveCommand.getDetailHtml();
        if(StringUtils.isNotBlank(detailHtml)) {
            SpuDetail spuDetail = new SpuDetail();
            spuDetail.setSpuId(spuId);
            spuDetail.setDetailHtml(detailHtml);
            spuDetailMapper.insert(spuDetail);
        }

        //新增spu的属性
        if(!CollectionUtils.isEmpty(spuSaveCommand.getAttributeKeyIdList())) {
            List<SpuKey> spuKeyList = spuSaveCommand.getAttributeKeyIdList().stream().map(keyId -> {
                SpuKey spuKey = new SpuKey();
                spuKey.setKeyId(keyId);
                spuKey.setSpuId(spuId);
                return spuKey;
            }).collect(Collectors.toList());
            spuKeyService.saveBatch(spuKeyList);
        }

    }

    @Override
    @Transactional
    public void update(SpuUpdateCommand spuUpdateCommand) {
        //更新SPU主表数据
        Spu spu = BeanUtil.copyProperties(spuUpdateCommand, Spu.class);
        this.updateById(spu);
        Long spuId = spuUpdateCommand.getId();

        //更新spu的商品图片
        spuImagesService.remove(Wrappers.<SpuImages>lambdaQuery().eq(SpuImages::getSpuId, spuId));
        if(!CollectionUtils.isEmpty(spuUpdateCommand.getSpuImageList())) {
            List<SpuImages> spuImagesList = spuUpdateCommand.getSpuImageList().stream().map(img -> {
                SpuImages spuImages = new SpuImages();
                spuImages.setSpuId(spuId);
                spuImages.setImgUrl(img);
                return spuImages;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(spuImagesList);
        }

        //更新商品详情
        String detailHtml = spuUpdateCommand.getDetailHtml();
        if(StringUtils.isNotBlank(detailHtml)) {
            SpuDetail spuDetail = new SpuDetail();
            spuDetail.setSpuId(spuId);
            spuDetail.setDetailHtml(detailHtml);
            spuDetailMapper.update(spuDetail, Wrappers.<SpuDetail>lambdaUpdate().eq(SpuDetail::getSpuId, spuId));
        }

        //更新商品属性
        spuKeyService.remove(Wrappers.<SpuKey>lambdaQuery().eq(SpuKey::getSpuId, spuId));
        if(!CollectionUtils.isEmpty(spuUpdateCommand.getAttributeKeyIdList())) {
            List<SpuKey> spuKeyList = spuUpdateCommand.getAttributeKeyIdList().stream().map(keyId -> {
                SpuKey spuKey = new SpuKey();
                spuKey.setKeyId(keyId);
                spuKey.setSpuId(spuId);
                return spuKey;
            }).collect(Collectors.toList());
            spuKeyService.saveBatch(spuKeyList);
        }

    }

    @Override
    @Transactional
    public void remove(Long id) {
        //删除spu记录
        this.removeById(id);
        //删除spu的图片记录
        spuImagesService.remove(Wrappers.<SpuImages>lambdaQuery().eq(SpuImages::getSpuId, id));

        //删除spu的商详
        spuDetailMapper.delete(Wrappers.<SpuDetail>lambdaQuery().eq(SpuDetail::getSpuId, id));

        //删除spu的属性
        spuKeyService.remove(Wrappers.<SpuKey>lambdaQuery().eq(SpuKey::getSpuId, id));

        //TODO 删除ES中的SPU
        //DeleteRequest deleteRequest = new DeleteRequest("spu", id.toString());
        
    }


    @Override
    public SpuDetailResponse detail(Long spuId) {
        SpuDetailResponse response = new SpuDetailResponse();

        //获取SPU的基本信息
        CompletableFuture<Spu> spuVOFuture = CompletableFuture.supplyAsync(() -> {
            Spu spu = super.getOne(new LambdaQueryWrapper<Spu>().eq(Spu::getId, spuId));
            SpuVO spuVO = BeanUtil.copyProperties(spu, SpuVO.class);
            response.setSpuVO(spuVO);
            return spu;
        }, executor);

        //获取SKU列表和SKU库存信息
        CompletableFuture<Void> skuVoFuture = CompletableFuture.runAsync(() -> {
            //通过SpuId获取sku列表
            List<Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));
            List<SkuVO> skuVOS = BeanUtil.copyToList(skuList, SkuVO.class);

            //通过sku的id集合获取sku库存信息
            List<Long> skuIdList = skuVOS.stream().map(SkuVO::getId).collect(Collectors.toList());
            List<SkuStock> skuStockList = skuStockService.list(Wrappers.<SkuStock>lambdaQuery().in(SkuStock::getSkuId, skuIdList));

            List<SkuStockVO> skuStockVOList = BeanUtil.copyToList(skuStockList, SkuStockVO.class);
            response.setSkus(skuVOS);
            response.setSkuStocks(skuStockVOList);
        }, executor);

        //获取SPU图片信息
        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            LambdaQueryWrapper<SpuImages> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SpuImages::getSpuId, spuId);
            queryWrapper.orderByDesc(SpuImages::getSort);
            List<SpuImages> imageList = spuImagesService.list(queryWrapper);
            List<SpuImagesVO> spuImagesVOList = BeanUtil.copyToList(imageList, SpuImagesVO.class);
            response.setImages(spuImagesVOList);
        }, executor);

        //获取SPU的detail信息
        CompletableFuture<Void> detailFuture = CompletableFuture.runAsync(() -> {
            SpuDetail spuDetail = spuDetailMapper.selectOne(Wrappers.<SpuDetail>lambdaQuery().eq(SpuDetail::getSpuId, spuId));
            SpuDetailVO spuDetailVO = BeanUtil.copyProperties(spuDetail, SpuDetailVO.class);
            response.setSpuDetailVO(spuDetailVO);
        }, executor);

        //获取SPU的销售属性组
        CompletableFuture<Void> spuAttrGroupFuture = CompletableFuture.runAsync(() -> {
            List<SpuAttrDTO> spuAttrDTOList = spuKeyService.listAttributes(spuId);

            Map<Long, List<SpuAttrDTO>> attrGroupList = spuAttrDTOList.stream()
                    .collect(Collectors.groupingBy(
                            SpuAttrDTO::getKeyId, Collectors.toList()));

            List<SpuAttributeGroupVO> groupVOList = new ArrayList<>();
            for (List<SpuAttrDTO> value : attrGroupList.values()) {
                groupVOList.add(
                        new SpuAttributeGroupVO(
                                value.get(0).getKeyId(),
                                value.get(0).getKey(),
                                0,
                                value));
            }
            response.setSpuAttributeGroupVOList(groupVOList);
        }, executor);

        try {
            CompletableFuture.allOf(spuVOFuture, skuVoFuture, imagesFuture, detailFuture, spuAttrGroupFuture).get();
        } catch (Exception e) {
            log.error("获取SPU详情线程池运行失败, SPU ID: " + spuId, e);
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void skuSave(SkuSaveCommand skuSaveCommand) {
        //1. 创建SKU实体，生成skuCode
        Long spuId = skuSaveCommand.getSpuId();
        Sku sku = BeanUtil.copyProperties(skuSaveCommand, Sku.class);
        String skuCode = buildSkuCode(spuId, skuSaveCommand.getSkuAttributeList());
        sku.setSkuCode(skuCode);

        //2. 保存sku
        skuService.save(sku);
        Long skuId = sku.getId();

        //3. 保存sku的属性到库中
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

    @Override
    public List<IndexSpuResponse> indexSpuList(Integer page, int size) {
        //1. 参数校验
        if(page < 0 || page > 10) {
            ExceptionCatcher.catchServiceEx("page参数不合法");
        }

        Page<Spu> result = spuMapper.selectPage(new Page<>(page, size), Wrappers.<Spu>lambdaQuery()
                .eq(Spu::getPublishStatus, SpuPublishStatusEnum.PUBLISHED.getCode())
                .orderByDesc(Spu::getCreatedAt));
        List<IndexSpuResponse> indexSpuResponseList = BeanUtil.copyToList(result.getRecords(), IndexSpuResponse.class);
        return indexSpuResponseList;

    }
}
