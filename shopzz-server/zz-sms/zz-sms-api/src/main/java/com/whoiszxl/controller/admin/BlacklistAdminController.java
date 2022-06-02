package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.BlacklistSaveCommand;
import com.whoiszxl.cqrs.command.BlacklistUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Blacklist;
import com.whoiszxl.service.BlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blacklist")
@Api(tags = "黑名单后台相关接口")
public class BlacklistAdminController {
    
    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取黑名单列表", notes = "分页获取黑名单列表", response = Blacklist.class)
    public ResponseResult<IPage<Blacklist>> list(@RequestBody PageQuery query) {
        Page<Blacklist> result = blacklistService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增黑名单", notes = "新增黑名单", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated BlacklistSaveCommand blacklistSaveCommand) {
        boolean saveFlag = blacklistService.save(dozerUtils.map(blacklistSaveCommand, Blacklist.class));
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新黑名单", notes = "更新黑名单", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody BlacklistUpdateCommand blacklistUpdateCommand) {
        boolean updateFlag = blacklistService.updateById(dozerUtils.map(blacklistUpdateCommand, Blacklist.class));
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除黑名单", notes = "删除黑名单", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = blacklistService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

