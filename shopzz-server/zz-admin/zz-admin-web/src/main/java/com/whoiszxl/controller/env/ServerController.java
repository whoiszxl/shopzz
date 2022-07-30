package com.whoiszxl.controller.env;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Server;
import com.whoiszxl.entity.common.ServerQuery;
import com.whoiszxl.service.ServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务器表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
@CrossOrigin
@RestController
@RequestMapping("/server")
@Api(tags = "服务器相关接口")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取配置列表", notes = "分页获取配置列表", response = Server.class)
    public ResponseResult<IPage<Server>> list(ServerQuery serverQuery) {
        LambdaQueryWrapper<Server> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(serverQuery.getServerName())) {
            wrapper.like(Server::getServerName, "%" + serverQuery.getServerName() + "%");
        }
        wrapper.orderByDesc(Server::getServerName);
        IPage<Server> pageResult = serverService.page(new Page<>(serverQuery.getPage(), serverQuery.getSize()), wrapper);
        return ResponseResult.buildSuccess(pageResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取服务器信息", notes = "通过主键ID获取服务器信息", response = Server.class)
    public ResponseResult<Server> get(@PathVariable Integer id) {
        Server server = serverService.getById(id);
        return ResponseResult.buildSuccess(server);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增服务器", notes = "新增服务器", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody Server server) {
        boolean saveFlag = serverService.save(server);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新服务器", notes = "更新服务器", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody Server server) {
        boolean updateFlag = serverService.updateById(server);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除服务器", notes = "删除服务器", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        boolean removeFlag = serverService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

