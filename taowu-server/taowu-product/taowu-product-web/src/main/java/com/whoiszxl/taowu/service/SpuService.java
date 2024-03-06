package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.cqrs.command.SkuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SpuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.taowu.cqrs.response.IndexSpuResponse;
import com.whoiszxl.taowu.cqrs.response.SpuDetailResponse;
import com.whoiszxl.taowu.entity.Spu;

import java.util.List;

/**
 * <p>
 * 商品基础信息表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface SpuService extends IService<Spu> {

    /**
     * 保存SPU
     * @param spuSaveCommand
     */
    void save(SpuSaveCommand spuSaveCommand);

    /**
     * 更新SPU
     * @param spuUpdateCommand
     */
    void update(SpuUpdateCommand spuUpdateCommand);

    /**
     * 删除SPU
     * @param id
     */
    void remove(Long id);

    /**
     * C端通过spuId获取SPU详情
     * @param spuId
     * @return
     */
    SpuDetailResponse detail(Long spuId);

    /**
     * 创建SKU
     * @param skuSaveCommand
     */
    void skuSave(SkuSaveCommand skuSaveCommand);

    /**
     * 获取首页最新商品列表
     * @param page 页码
     * @param size 每一页多少条
     * @return 首页商品列表
     */
    PageResponse<IndexSpuResponse> indexSpuList(Integer page, int size);
}
