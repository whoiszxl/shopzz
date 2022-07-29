package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Script;
import com.whoiszxl.mapper.ScriptMapper;
import com.whoiszxl.service.ScriptService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * SH脚本表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-14
 */
@Service
public class ScriptServiceImpl extends ServiceImpl<ScriptMapper, Script> implements ScriptService {

    @Override
    public Script getByScriptName(String scriptName) {
        LambdaQueryWrapper<Script> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Script::getScriptName, scriptName);
        return this.getOne(queryWrapper);
    }
}
