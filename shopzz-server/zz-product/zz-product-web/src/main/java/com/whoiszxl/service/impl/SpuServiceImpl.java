package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.cqrs.command.SpuSaveCommand;
import com.whoiszxl.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.cqrs.response.SpuDetailResponse;
import com.whoiszxl.cqrs.vo.SkuStockVO;
import com.whoiszxl.cqrs.vo.SkuVO;
import com.whoiszxl.cqrs.vo.SpuImagesVO;
import com.whoiszxl.cqrs.vo.SpuVO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.*;
import com.whoiszxl.mapper.SpuDetailMapper;
import com.whoiszxl.mapper.SpuMapper;
import com.whoiszxl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品基础信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private SpuKeyService spuKeyService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuStockService skuStockService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    @Transactional
    public void save(SpuSaveCommand spuSaveCommand) {
        Spu spu = dozerUtils.map(spuSaveCommand, Spu.class);
        this.save(spu);
        Long spuId = spu.getId();

        List<String> imageUrlList = spuSaveCommand.getSpuImageList();
        List<SpuImages> spuImagesList = imageUrlList.stream().map(img -> {
            SpuImages spuImages = new SpuImages();
            spuImages.setSpuId(spuId);
            spuImages.setImgUrl(img);
            return spuImages;
        }).collect(Collectors.toList());
        spuImagesService.saveBatch(spuImagesList);

        String detailHtml = spuSaveCommand.getDetailHtml();
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuId);
        spuDetail.setDetailHtml(detailHtml);
        spuDetailMapper.insert(spuDetail);

        List<SpuKey> spuKeyList = spuSaveCommand.getAttributeKeyIdList().stream().map(keyId -> {
            SpuKey spuKey = new SpuKey();
            spuKey.setKeyId(keyId);
            spuKey.setSpuId(spuId);
            return spuKey;
        }).collect(Collectors.toList());
        spuKeyService.saveBatch(spuKeyList);
    }

    @Override
    @Transactional
    public void update(SpuUpdateCommand spuUpdateCommand) {
        Spu spu = dozerUtils.map(spuUpdateCommand, Spu.class);
        this.updateById(spu);
        Long spuId = spuUpdateCommand.getId();

        spuImagesService.remove(Wrappers.<SpuImages>lambdaQuery().eq(SpuImages::getSpuId, spuId));
        List<SpuImages> spuImagesList = spuUpdateCommand.getSpuImageList().stream().map(img -> {
            SpuImages spuImages = new SpuImages();
            spuImages.setSpuId(spuId);
            spuImages.setImgUrl(img);
            return spuImages;
        }).collect(Collectors.toList());
        spuImagesService.saveBatch(spuImagesList);

        String detailHtml = spuUpdateCommand.getDetailHtml();
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuId);
        spuDetail.setDetailHtml(detailHtml);
        spuDetailMapper.update(spuDetail, Wrappers.<SpuDetail>lambdaUpdate().eq(SpuDetail::getSpuId, spuId));

        spuKeyService.remove(Wrappers.<SpuKey>lambdaQuery().eq(SpuKey::getSpuId, spuId));
        List<SpuKey> spuKeyList = spuUpdateCommand.getAttributeKeyIdList().stream().map(keyId -> {
            SpuKey spuKey = new SpuKey();
            spuKey.setKeyId(keyId);
            spuKey.setSpuId(spuId);
            return spuKey;
        }).collect(Collectors.toList());
        spuKeyService.saveBatch(spuKeyList);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        this.removeById(id);
        spuImagesService.remove(Wrappers.<SpuImages>lambdaQuery().eq(SpuImages::getSpuId, id));
        spuDetailMapper.delete(Wrappers.<SpuDetail>lambdaQuery().eq(SpuDetail::getSpuId, id));
        spuKeyService.remove(Wrappers.<SpuKey>lambdaQuery().eq(SpuKey::getSpuId, id));
    }


    @Override
    public SpuDetailResponse detail(Long spuId) {
        SpuDetailResponse response = new SpuDetailResponse();

        //获取SPU的基本信息
        CompletableFuture<Spu> spuVOFuture = CompletableFuture.supplyAsync(() -> {
            Spu spu = super.getOne(new LambdaQueryWrapper<Spu>().eq(Spu::getId, spuId));
            SpuVO spuVO = dozerUtils.map(spu, SpuVO.class);
            response.setSpuVO(spuVO);
            return spu;
        }, executor);

        //获取SKU列表和SKU库存信息
        CompletableFuture<Void> skuVoFuture = CompletableFuture.runAsync(() -> {
            //通过SpuId获取sku列表
            List<Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));
            List<SkuVO> skuVOS = dozerUtils.mapList(skuList, SkuVO.class);

            //通过sku的id集合获取sku库存信息
            List<Long> skuIdList = skuVOS.stream().map(SkuVO::getId).collect(Collectors.toList());
            List<SkuStock> skuStockList = skuStockService.list(Wrappers.<SkuStock>lambdaQuery().in(SkuStock::getSkuId, skuIdList));

            List<SkuStockVO> skuStockVOList = dozerUtils.mapList(skuStockList, SkuStockVO.class);
            response.setSkus(skuVOS);
            response.setSkuStocks(skuStockVOList);
        }, executor);

        //获取SPU图片信息
        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            LambdaQueryWrapper<SpuImages> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SpuImages::getSpuId, spuId);
            queryWrapper.orderByDesc(SpuImages::getSort);
            List<SpuImages> imageList = spuImagesService.list(queryWrapper);
            List<SpuImagesVO> spuImagesVOList = dozerUtils.mapList(imageList, SpuImagesVO.class);
            response.setImages(spuImagesVOList);
        }, executor);


        try {
            CompletableFuture.allOf(spuVOFuture, skuVoFuture, imagesFuture).get();
        } catch (Exception e) {
            log.error("获取SPU详情线程池运行失败, SPU ID: " + spuId, e);
        }
        return response;
    }
}
