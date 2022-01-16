package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.entity.Admin;
import com.whoiszxl.entity.query.LoginQuery;
import com.whoiszxl.logger.annotation.SSLog;
import com.whoiszxl.service.AdminService;
import com.whoiszxl.service.PrivilegeService;
import com.whoiszxl.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理后台登录相关接口")
public class AdminLoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/login")
    @SSLog("管理员账号密码登录")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", response = ResponseResult.class)
    public ResponseResult<String> adminLogin(@RequestBody LoginQuery loginQuery) {
        //1. 从数据库查询用户名和密码是否匹配
        Admin admin = adminService.getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, loginQuery.getUsername()));
        if (admin == null || !passwordEncoder.matches(loginQuery.getPassword(), admin.getPassword())) {
            return ResponseResult.buildError("账号或密码错误");
        }

        //2. 登录并获取token
        StpUtil.login(admin.getId());

        //3. 登录后将用户的权限查出来放到redis里去
        String redisKey = RedisKeyPrefixConstants.ADMIN_PRIVILEGE_PREFIX + admin.getId();
        redisUtils.delete(redisKey);
        List<String> privilegeList = privilegeService.getCurrentAdminAvailPrivilegeList(admin.getId());
        redisUtils.lLeftPushAll(redisKey, privilegeList);

        return ResponseResult.buildSuccess("登录成功", StpUtil.getTokenValue());
    }

    @SaCheckLogin
    @DeleteMapping("/logout")
    @SSLog("管理员登出")
    @ApiOperation(value = "管理员登出", notes = "管理员登出", response = ResponseResult.class)
    public ResponseResult<String> adminLogout() {
        StpUtil.logout();
        return ResponseResult.buildSuccess("登出成功");
    }
}