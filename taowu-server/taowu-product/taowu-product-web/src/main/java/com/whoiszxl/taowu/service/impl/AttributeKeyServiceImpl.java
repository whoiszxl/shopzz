package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.taowu.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.taowu.entity.AttributeKey;
import com.whoiszxl.taowu.mapper.AttributeKeyMapper;
import com.whoiszxl.taowu.service.AttributeKeyService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AttributeKeyServiceImpl extends ServiceImpl<AttributeKeyMapper, AttributeKey> implements AttributeKeyService {

    private final AttributeKeyMapper attributeKeyMapper;


    @Override
    public boolean save(AttributeKeySaveCommand attributeKeySaveCommand) {
        AttributeKey attributeKey = attributeKeyMapper.selectOne(new LambdaQueryWrapper<AttributeKey>().eq(AttributeKey::getName, attributeKeySaveCommand.getName()));
        if(attributeKey != null) {
            ExceptionCatcher.catchServiceEx("属性名" + attributeKey.getName() + "已存在");
        }
        AttributeKey saveParams = BeanUtil.copyProperties(attributeKeySaveCommand, AttributeKey.class);
        return this.save(saveParams);
    }

    @Override
    public boolean update(AttributeKeyUpdateCommand attributeKeyUpdateCommand) {
        AttributeKey attributeKey = attributeKeyMapper.selectById(attributeKeyUpdateCommand.getId());
        if(attributeKey == null) {
            ExceptionCatcher.catchServiceEx("属性key不存在");
        }
        AttributeKey updateParams = BeanUtil.copyProperties(attributeKeyUpdateCommand, AttributeKey.class);
        return this.updateById(updateParams);
    }
}
