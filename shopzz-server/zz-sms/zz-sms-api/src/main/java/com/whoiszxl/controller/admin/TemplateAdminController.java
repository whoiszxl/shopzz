package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.TemplateSaveCommand;
import com.whoiszxl.cqrs.command.TemplateUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Template;
import com.whoiszxl.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
@Api(tags = "模板后台相关接口")
public class TemplateAdminController {
    
    @Autowired
    private TemplateService templateService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取模板列表", notes = "分页获取模板列表", response = Template.class)
    public ResponseResult<IPage<Template>> list(@RequestBody PageQuery query) {
        Page<Template> result = templateService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增模板", notes = "新增模板", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated TemplateSaveCommand templateSaveCommand) {
        boolean saveFlag = templateService.save(dozerUtils.map(templateSaveCommand, Template.class));
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新模板", notes = "更新模板", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody TemplateUpdateCommand templateUpdateCommand) {
        boolean updateFlag = templateService.updateById(dozerUtils.map(templateUpdateCommand, Template.class));
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除模板", notes = "删除模板", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = templateService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

