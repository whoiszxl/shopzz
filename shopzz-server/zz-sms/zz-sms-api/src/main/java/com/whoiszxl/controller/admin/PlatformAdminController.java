package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.PlatformSaveCommand;
import com.whoiszxl.cqrs.command.PlatformUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Platform;
import com.whoiszxl.service.PlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platform")
@Api(tags = "平台后台相关接口")
public class PlatformAdminController {
    
    @Autowired
    private PlatformService platformService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取平台列表", notes = "分页获取平台列表", response = Platform.class)
    public ResponseResult<IPage<Platform>> list(@RequestBody PageQuery query) {
        Page<Platform> result = platformService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增平台", notes = "新增平台", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated PlatformSaveCommand platformSaveCommand) {
        boolean saveFlag = platformService.save(dozerUtils.map(platformSaveCommand, Platform.class));
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新平台", notes = "更新平台", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody PlatformUpdateCommand platformUpdateCommand) {
        boolean updateFlag = platformService.updateById(dozerUtils.map(platformUpdateCommand, Platform.class));
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除平台", notes = "删除平台", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = platformService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

