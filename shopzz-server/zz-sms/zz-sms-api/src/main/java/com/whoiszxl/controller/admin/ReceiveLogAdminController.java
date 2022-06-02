package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.ReceiveLog;
import com.whoiszxl.service.ReceiveLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receivelog")
@Api(tags = "短信接收日志后台相关接口")
public class ReceiveLogAdminController {
    
    @Autowired
    private ReceiveLogService receiveLogService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取短信接收日志列表", notes = "分页获取短信接收日志列表", response = ReceiveLog.class)
    public ResponseResult<IPage<ReceiveLog>> list(@RequestBody PageQuery query) {
        Page<ReceiveLog> result = receiveLogService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除短信接收日志", notes = "删除短信接收日志", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = receiveLogService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

