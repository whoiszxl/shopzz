package com.whoiszxl.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.common.entity.PageResult;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.system.entity.Admin;
import com.whoiszxl.system.service.AdminService;
import com.whoiszxl.system.utils.JwtUtil;
import com.whoiszxl.system.vo.AdminSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-21
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/admin")
@Api(value = "ZMALL-管理员控制器", tags = "ZMALL-管理员控制器", description = "ZMALL-管理员控制器描述")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("查询全部管理员数据")
    @GetMapping
    public Result findAll(){
        List<Admin> adminList = adminService.list();
        return Result.success(adminList);
    }

    @ApiOperation("根据ID查询管理员")
    @ApiImplicitParam(value = "管理员ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    @ApiOperation("新增管理员数据")
    @ApiImplicitParam(name = "admin", value = "管理员对象", required = true, dataType = "Admin", paramType = "body")
    @PostMapping
    public Result add(@RequestBody Admin admin){
        String encryptPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(encryptPassword);
        boolean isSave = adminService.save(admin);
        return isSave ? Result.success() : Result.fail("add fail");
    }


    @ApiOperation("修改管理员数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin", value = "管理员对象", required = true, dataType = "Admin", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "管理员ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Admin admin, @PathVariable Integer id){
        admin.setId(id);
        boolean isUpdate = adminService.updateById(admin);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }


    @ApiOperation("根据ID删除管理员数据")
    @ApiImplicitParam(value = "管理员ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = adminService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }


    @ApiOperation("多条件搜索管理员数据")
    @ApiImplicitParam(value = "搜索条件",name = "adminSearchVo",dataType = "AdminSearchVo",paramType = "body")
    @PostMapping(value = "/search" )
    public Result findList(@RequestBody AdminSearchVo adminSearchVo){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(adminSearchVo.getLoginName())) {
            queryWrapper.like("login_name", "%" + adminSearchVo.getLoginName() + "%");
        }
        if(StringUtils.isNotBlank(adminSearchVo.getStatus())) {
            queryWrapper.like("status", adminSearchVo.getStatus());
        }
        if(adminSearchVo.getId() != null) {
            queryWrapper.like("id", adminSearchVo.getId());
        }
        List list = adminService.list(queryWrapper);
        return Result.success(list);
    }



    @ApiOperation("分页搜索实现")
    @ApiImplicitParam(value = "搜索条件",name = "adminSearchVo",dataType = "AdminSearchVo",paramType = "body")
    @PostMapping(value = "/searchByPage" )
    public Result<PageResult> findPage(@RequestBody AdminSearchVo adminSearchVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(adminSearchVo.getLoginName())) {
            queryWrapper.like("login_name", "%" + adminSearchVo.getLoginName() + "%");
        }
        if (StringUtils.isNotBlank(adminSearchVo.getStatus())) {
            queryWrapper.like("status", adminSearchVo.getStatus());
        }
        if (adminSearchVo.getId() != null) {
            queryWrapper.like("id", adminSearchVo.getId());
        }

        IPage<Admin> iPage = new Page<>(adminSearchVo.getPage(), adminSearchVo.getSize());
        IPage page1 = adminService.page(iPage, queryWrapper);
        PageResult pageResult = new PageResult(page1.getTotal(), page1.getRecords());
        return Result.success(pageResult);
    }

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        boolean result = adminService.login(admin);
        if (result){
            //密码是正确的
            //生成jwt令牌,返回到客户端
            Map<String,String> info = new HashMap<>();
            info.put("username",admin.getLoginName());
            //基于工具类生成jwt令牌
            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), admin.getLoginName(), null);
            info.put("token",jwt);
            return Result.success(info);
        }else{
            return Result.fail("登录失败");
        }
    }

}
