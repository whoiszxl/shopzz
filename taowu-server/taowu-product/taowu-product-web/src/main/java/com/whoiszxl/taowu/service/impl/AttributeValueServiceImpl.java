package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.cqrs.command.AttributeValueSaveCommand;
import com.whoiszxl.taowu.cqrs.command.AttributeValueUpdateCommand;
import com.whoiszxl.taowu.entity.AttributeKey;
import com.whoiszxl.taowu.entity.AttributeValue;
import com.whoiszxl.taowu.mapper.AttributeValueMapper;
import com.whoiszxl.taowu.service.AttributeKeyService;
import com.whoiszxl.taowu.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 属性值表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
@RequiredArgsConstructor
public class AttributeValueServiceImpl extends ServiceImpl<AttributeValueMapper, AttributeValue> implements AttributeValueService {

    private final AttributeValueMapper attributeValueMapper;

    private final AttributeKeyService attributeKeyService;

    
    @Override
    public boolean save(AttributeValueSaveCommand attributeValueSaveCommand) {
        AttributeKey attributeKey = attributeKeyService.getById(attributeValueSaveCommand.getKeyId());
        if(attributeKey == null) {
            ExceptionCatcher.catchServiceEx("属性key不存在");
        }

        AttributeValue attributeValue = attributeValueMapper.selectOne(
                new LambdaQueryWrapper<AttributeValue>()
                        .eq(AttributeValue::getValue, attributeValueSaveCommand.getValue())
                        .eq(AttributeValue::getKeyId, attributeValueSaveCommand.getKeyId()));
        if(attributeValue != null) {
            ExceptionCatcher.catchServiceEx("属性value" + attributeValue.getValue() + "已存在");
        }
        AttributeValue saveParams = BeanUtil.copyProperties(attributeValueSaveCommand, AttributeValue.class);
        return this.save(saveParams);
    }

    @Override
    public boolean update(AttributeValueUpdateCommand attributeValueUpdateCommand) {
        AttributeKey attributeKey = attributeKeyService.getById(attributeValueUpdateCommand.getKeyId());
        if(attributeKey == null) {
            ExceptionCatcher.catchServiceEx("属性key不存在");
        }


        AttributeValue attributeValue = attributeValueMapper.selectOne(
                new LambdaQueryWrapper<AttributeValue>()
                        .eq(AttributeValue::getValue, attributeValueUpdateCommand.getValue())
                        .eq(AttributeValue::getKeyId, attributeValueUpdateCommand.getKeyId()));
        if(attributeValue != null) {
            ExceptionCatcher.catchServiceEx("属性value" + attributeValue.getValue() + "已存在");
        }

        AttributeValue updateParams = BeanUtil.copyProperties(attributeValueUpdateCommand, AttributeValue.class);
        return this.updateById(updateParams);
    }
}
