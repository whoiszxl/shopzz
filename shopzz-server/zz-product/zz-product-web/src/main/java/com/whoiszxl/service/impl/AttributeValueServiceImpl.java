package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.AttributeValueSaveCommand;
import com.whoiszxl.cqrs.command.AttributeValueUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.AttributeKey;
import com.whoiszxl.entity.AttributeValue;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.AttributeValueMapper;
import com.whoiszxl.service.AttributeKeyService;
import com.whoiszxl.service.AttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AttributeValueServiceImpl extends ServiceImpl<AttributeValueMapper, AttributeValue> implements AttributeValueService {

    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Autowired
    private AttributeKeyService attributeKeyService;

    @Autowired
    private DozerUtils dozerUtils;
    
    @Override
    public boolean save(AttributeValueSaveCommand attributeValueSaveCommand) {
        AttributeKey attributeKey = attributeKeyService.getById(attributeValueSaveCommand.getKeyId());
        if(attributeKey == null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("属性key不存在"));
        }

        AttributeValue attributeValue = attributeValueMapper.selectOne(
                new LambdaQueryWrapper<AttributeValue>()
                        .eq(AttributeValue::getValue, attributeValueSaveCommand.getValue())
                        .eq(AttributeValue::getKeyId, attributeValueSaveCommand.getKeyId()));
        if(attributeValue != null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("属性value" + attributeValue.getValue() + "已存在"));
        }
        AttributeValue saveParams = dozerUtils.map(attributeValueSaveCommand, AttributeValue.class);
        return this.save(saveParams);
    }

    @Override
    public boolean update(AttributeValueUpdateCommand attributeValueUpdateCommand) {
        AttributeKey attributeKey = attributeKeyService.getById(attributeValueUpdateCommand.getKeyId());
        if(attributeKey == null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("属性key不存在"));
        }


        AttributeValue attributeValue = attributeValueMapper.selectOne(
                new LambdaQueryWrapper<AttributeValue>()
                        .eq(AttributeValue::getValue, attributeValueUpdateCommand.getValue())
                        .eq(AttributeValue::getKeyId, attributeValueUpdateCommand.getKeyId()));
        if(attributeValue != null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("属性value" + attributeValue.getValue() + "已存在"));
        }

        AttributeValue updateParams = dozerUtils.map(attributeValueUpdateCommand, AttributeValue.class);
        return this.updateById(updateParams);
    }
}
