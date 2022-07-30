package com.whoiszxl.controller.env;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Config;
import com.whoiszxl.entity.common.ConfigQuery;
import com.whoiszxl.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 基础配置表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/config")
@Api(tags = "配置相关接口")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取配置列表", notes = "分页获取配置列表", response = Config.class)
    public ResponseResult<IPage<Config>> list(ConfigQuery configQuery) {
        LambdaQueryWrapper<Config> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(configQuery.getKey())) {
            wrapper.like(Config::getConfigKey, "%" + configQuery.getKey() + "%");
        }
        wrapper.orderByDesc(Config::getConfigKey);
        IPage<Config> pageResult = configService.page(new Page<>(configQuery.getPage(), configQuery.getSize()), wrapper);
        return ResponseResult.buildSuccess(pageResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取基础配置信息", notes = "通过主键ID获取基础配置信息", response = Config.class)
    public ResponseResult<Config> get(@PathVariable Integer id) {
        Config config = configService.getById(id);
        return ResponseResult.buildSuccess(config);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增配置", notes = "新增配置", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody Config config) {
        boolean saveFlag = configService.save(config);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新配置", notes = "更新配置", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody Config config) {
        boolean updateFlag = configService.updateById(config);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除配置", notes = "删除配置", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        boolean removeFlag = configService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

