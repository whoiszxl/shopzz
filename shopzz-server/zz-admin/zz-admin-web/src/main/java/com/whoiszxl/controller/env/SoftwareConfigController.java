package com.whoiszxl.controller.env;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Software;
import com.whoiszxl.entity.SoftwareConfig;
import com.whoiszxl.entity.common.SoftwareConfigQuery;
import com.whoiszxl.service.SoftwareConfigService;
import com.whoiszxl.service.SoftwareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 基础组件配置表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
@CrossOrigin
@RestController
@RequestMapping("/software-config")
@Api(tags = "服务器配置相关接口")
public class SoftwareConfigController {

    @Autowired
    private SoftwareConfigService softwareConfigService;

    @Autowired
    private SoftwareService softwareService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取配置列表", notes = "分页获取配置列表", response = SoftwareConfig.class)
    public ResponseResult<IPage<SoftwareConfig>> list(SoftwareConfigQuery query) {
        LambdaQueryWrapper<SoftwareConfig> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getSoftwareName())) {
            wrapper.like(SoftwareConfig::getSoftwareName, "%" + query.getSoftwareName() + "%");
        }

        if(query.getSoftwareName() != null) {
            wrapper.eq(SoftwareConfig::getSoftwareName, query.getSoftwareName());
        }

        IPage<SoftwareConfig> pageResult = softwareConfigService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(pageResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取组件配置信息", notes = "通过主键ID获取组件配置信息", response = SoftwareConfig.class)
    public ResponseResult<SoftwareConfig> get(@PathVariable Integer id) {
        SoftwareConfig softwareConfig = softwareConfigService.getById(id);
        return ResponseResult.buildSuccess(softwareConfig);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增组件配置", notes = "新增组件配置", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody SoftwareConfig softwareConfig) {
        //校验组件是否存在
        Software software = softwareService.getBySoftwareName(softwareConfig.getSoftwareName());
        if(software == null) {
            return ResponseResult.buildError("组件不存在");
        }

        boolean saveFlag = softwareConfigService.save(softwareConfig);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新组件配置", notes = "更新组件配置", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody SoftwareConfig softwareConfig) {
        //校验组件是否存在
        Software software = softwareService.getBySoftwareName(softwareConfig.getSoftwareName());
        if(software == null) {
            return ResponseResult.buildError("组件不存在");
        }
        boolean updateFlag = softwareConfigService.updateById(softwareConfig);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除组件配置", notes = "删除组件配置", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        boolean removeFlag = softwareConfigService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

