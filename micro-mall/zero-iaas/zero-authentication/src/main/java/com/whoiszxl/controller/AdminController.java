package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.SysUser;
import com.whoiszxl.entity.query.SysUserQuery;
import com.whoiszxl.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "管理员后台管理相关接口")
public class AdminController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SaCheckLogin
    @SaCheckRole("admin")
    @GetMapping("/admin/list")
    @ApiOperation(value = "管理员列表查询", notes = "管理员列表查询", response = SysUser.class)
    public ResponseResult<IPage<SysUser>> list(SysUserQuery query) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(query.getUsername())) {
            queryWrapper.eq("username", query.getUsername());
        }
        if(StringUtils.isNotBlank(query.getMobile())) {
            queryWrapper.or().eq("mobile", query.getMobile());
        }

        if(StringUtils.isNotBlank(query.getEmail())) {
            queryWrapper.or().eq("email", query.getEmail());
        }

        IPage<SysUser> pageResult = sysUserService.page(new Page<>(query.getPage(), query.getSize()), queryWrapper);
        return ResponseResult.buildSuccess(pageResult);
    }

    @SaCheckLogin
    @PostMapping("/admin/lock/switch/{username}")
    @ApiOperation(value = "禁用or解禁管理员", notes = "禁用or解禁管理员", response = ResponseResult.class)
    public ResponseResult<Boolean> lockSwitch(@PathVariable String username) {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));
        sysUser.setStatus(sysUser.getStatus() == 0 ? 1 : 0);
        boolean updateFlag = sysUserService.updateById(sysUser);
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @PostMapping("/admin/info")
    @ApiOperation(value = "获取管理员用户信息", notes = "获取管理员用户信息", response = SysUser.class)
    public ResponseResult<SysUser> adminInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("id", userId));
        return ResponseResult.buildSuccess(sysUser);
    }


}
