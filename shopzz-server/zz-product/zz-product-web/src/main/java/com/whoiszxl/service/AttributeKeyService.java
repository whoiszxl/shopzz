package com.whoiszxl.service;

import com.whoiszxl.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.entity.AttributeKey;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 属性键表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface AttributeKeyService extends IService<AttributeKey> {

    /**
     * 保存属性key
     * @param attributeKeySaveCommand
     * @return
     */
    boolean save(AttributeKeySaveCommand attributeKeySaveCommand);

    /**
     * 更新属性key
     * @param attributeKeyUpdateCommand
     * @return
     */
    boolean update(AttributeKeyUpdateCommand attributeKeyUpdateCommand);
}
