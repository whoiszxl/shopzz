package com.whoiszxl.controller.env;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Script;
import com.whoiszxl.entity.common.ScriptQuery;
import com.whoiszxl.service.ScriptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * SH脚本表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/script")
@Api(tags = "脚本相关接口")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取配置列表", notes = "分页获取配置列表", response = Script.class)
    public ResponseResult<IPage<Script>> list(ScriptQuery serverQuery) {
        LambdaQueryWrapper<Script> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(serverQuery.getScriptName())) {
            wrapper.like(Script::getScriptName, "%" + serverQuery.getScriptName() + "%");
        }
        wrapper.orderByDesc(Script::getScriptName);
        IPage<Script> pageResult = scriptService.page(new Page<>(serverQuery.getPage(), serverQuery.getSize()), wrapper);
        return ResponseResult.buildSuccess(pageResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取脚本信息", notes = "通过主键ID获取脚本信息", response = Script.class)
    public ResponseResult<Script> get(@PathVariable Integer id) {
        Script server = scriptService.getById(id);
        return ResponseResult.buildSuccess(server);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增脚本", notes = "新增脚本", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody Script server) {
        boolean saveFlag = scriptService.save(server);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新脚本", notes = "更新脚本", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody Script server) {
        boolean updateFlag = scriptService.updateById(server);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除脚本", notes = "删除脚本", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        boolean removeFlag = scriptService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }
}

