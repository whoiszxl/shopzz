package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.ChannelSaveCommand;
import com.whoiszxl.cqrs.command.ChannelUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Channel;
import com.whoiszxl.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channel")
@Api(tags = "渠道后台相关接口")
public class ChannelAdminController {
    
    @Autowired
    private ChannelService channelService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取渠道列表", notes = "分页获取渠道列表", response = Channel.class)
    public ResponseResult<IPage<Channel>> list(@RequestBody PageQuery query) {
        Page<Channel> result = channelService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增渠道", notes = "新增渠道", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated ChannelSaveCommand channelSaveCommand) {
        boolean saveFlag = channelService.save(dozerUtils.map(channelSaveCommand, Channel.class));
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新渠道", notes = "更新渠道", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody ChannelUpdateCommand channelUpdateCommand) {
        boolean updateFlag = channelService.updateById(dozerUtils.map(channelUpdateCommand, Channel.class));
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除渠道", notes = "删除渠道", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = channelService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

