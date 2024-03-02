package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.cqrs.command.AttributeValueSaveCommand;
import com.whoiszxl.taowu.cqrs.command.AttributeValueUpdateCommand;
import com.whoiszxl.taowu.entity.AttributeValue;

/**
 * <p>
 * 属性值表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface AttributeValueService extends IService<AttributeValue> {

    /**
     * 保存属性值
     * @param attributeValueSaveCommand
     * @return
     */
    boolean save(AttributeValueSaveCommand attributeValueSaveCommand);

    /**
     * 更新属性值
     * @param attributeValueUpdateCommand
     * @return
     */
    boolean update(AttributeValueUpdateCommand attributeValueUpdateCommand);

}
