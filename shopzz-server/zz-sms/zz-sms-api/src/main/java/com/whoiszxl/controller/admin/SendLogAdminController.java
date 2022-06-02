package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.SendLog;
import com.whoiszxl.service.SendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendlog")
@Api(tags = "发送日志后台相关接口")
public class SendLogAdminController {
    
    @Autowired
    private SendLogService sendLogService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取发送日志列表", notes = "分页获取发送日志列表", response = SendLog.class)
    public ResponseResult<IPage<SendLog>> list(@RequestBody PageQuery query) {
        Page<SendLog> result = sendLogService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除发送日志", notes = "删除发送日志", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = sendLogService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

