package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.taowu.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.taowu.entity.AttributeKey;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 属性键表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Mapper
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
