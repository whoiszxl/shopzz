package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.FareTemplate;
import com.whoiszxl.entity.query.FareTemplateQuery;
import com.whoiszxl.service.FareTemplateService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 运费模板表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Slf4j
@RestController
@RequestMapping("/fare-template")
public class FareTemplateController {
    
    
    @Autowired
    private FareTemplateService fareTemplateService;


    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "分页获取运费模板列表", notes = "分页获取运费模板列表", response = FareTemplate.class)
    public ResponseResult<IPage<FareTemplate>> list(FareTemplateQuery query) {
        LambdaQueryWrapper<FareTemplate> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getTemplateName())) {
            wrapper.like(FareTemplate::getTemplateName, "%" + query.getTemplateName() + "%");
        }
        if(query.getTemplateType() != null) {
            wrapper.eq(FareTemplate::getTemplateType, query.getTemplateType());
        }
        IPage<FareTemplate> result = fareTemplateService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取运费模板", notes = "通过主键ID获取运费模板", response = FareTemplate.class)
    public ResponseResult<FareTemplate> getSupplierById(@PathVariable Long id) {
        FareTemplate fareTemplate = fareTemplateService.getById(id);
        return ResponseResult.buildSuccess(fareTemplate);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增运费模板", notes = "新增运费模板", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody FareTemplate fareTemplate) {
        boolean saveFlag = fareTemplateService.save(fareTemplate);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新运费模板", notes = "更新运费模板", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody FareTemplate fareTemplate) {
        boolean updateFlag = fareTemplateService.updateById(fareTemplate);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除运费模板", notes = "删除运费模板", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = fareTemplateService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }
    

}

