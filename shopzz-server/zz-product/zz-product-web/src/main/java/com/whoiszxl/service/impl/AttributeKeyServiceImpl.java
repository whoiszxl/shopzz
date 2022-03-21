package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.AttributeKey;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.AttributeKeyMapper;
import com.whoiszxl.service.AttributeKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 属性键表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class AttributeKeyServiceImpl extends ServiceImpl<AttributeKeyMapper, AttributeKey> implements AttributeKeyService {

    @Autowired
    private AttributeKeyMapper attributeKeyMapper;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public boolean save(AttributeKeySaveCommand attributeKeySaveCommand) {
        AttributeKey attributeKey = attributeKeyMapper.selectOne(new LambdaQueryWrapper<AttributeKey>().eq(AttributeKey::getName, attributeKeySaveCommand.getName()));
        if(attributeKey != null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("属性名" + attributeKey.getName() + "已存在"));
        }
        AttributeKey saveParams = dozerUtils.map(attributeKeySaveCommand, AttributeKey.class);
        return this.save(saveParams);
    }

    @Override
    public boolean update(AttributeKeyUpdateCommand attributeKeyUpdateCommand) {
        AttributeKey attributeKey = attributeKeyMapper.selectById(attributeKeyUpdateCommand.getId());
        if(attributeKey == null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("属性key不存在"));
        }
        AttributeKey updateParams = dozerUtils.map(attributeKeyUpdateCommand, AttributeKey.class);
        return this.updateById(updateParams);
    }
}
