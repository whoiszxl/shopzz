package com.whoiszxl.service;

import com.whoiszxl.cqrs.command.SpuSaveCommand;
import com.whoiszxl.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.entity.Spu;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
