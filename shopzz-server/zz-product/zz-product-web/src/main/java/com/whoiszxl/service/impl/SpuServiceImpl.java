package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.cqrs.command.SpuSaveCommand;
import com.whoiszxl.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Spu;
import com.whoiszxl.entity.SpuDetail;
import com.whoiszxl.entity.SpuImages;
import com.whoiszxl.entity.SpuKey;
import com.whoiszxl.mapper.SpuDetailMapper;
import com.whoiszxl.mapper.SpuMapper;
import com.whoiszxl.service.SpuImagesService;
import com.whoiszxl.service.SpuKeyService;
import com.whoiszxl.service.SpuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
