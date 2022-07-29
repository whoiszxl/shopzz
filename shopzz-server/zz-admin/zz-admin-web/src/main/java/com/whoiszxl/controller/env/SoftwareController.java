package com.whoiszxl.controller.env;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Software;
import com.whoiszxl.service.SoftwareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 基础组件表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
@CrossOrigin
@RestController
@RequestMapping("/software")
@Api(tags = "组件相关接口")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "分页获取配置列表", notes = "分页获取配置列表", response = Software.class)
    public ResponseResult<IPage<Software>> list(PageQuery pageQuery) {
        LambdaQueryWrapper<Software> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Software::getCreatedAt);
        IPage<Software> pageResult = softwareService.page(new Page<>(pageQuery.getPage(), pageQuery.getSize()), wrapper);
        return ResponseResult.buildSuccess(pageResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取组件信息", notes = "通过主键ID获取组件信息", response = Software.class)
    public ResponseResult<Software> get(@PathVariable Integer id) {
        Software software = softwareService.getById(id);
        return ResponseResult.buildSuccess(software);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增组件", notes = "新增组件", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody Software server) {
        boolean saveFlag = softwareService.save(server);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新组件", notes = "更新组件", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody Software server) {
        boolean updateFlag = softwareService.updateById(server);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除组件", notes = "删除组件", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        boolean removeFlag = softwareService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }
    
}

