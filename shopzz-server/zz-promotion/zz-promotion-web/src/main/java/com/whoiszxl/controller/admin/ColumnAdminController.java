package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.ProductColumnSaveCommand;
import com.whoiszxl.cqrs.command.ProductColumnSpuSaveCommand;
import com.whoiszxl.cqrs.command.ProductColumnUpdateCommand;
import com.whoiszxl.cqrs.query.ProductColumnQuery;
import com.whoiszxl.cqrs.query.ProductColumnSkuQuery;
import com.whoiszxl.cqrs.response.ProductColumnResponse;
import com.whoiszxl.cqrs.response.ProductColumnSpuResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.ProductColumn;
import com.whoiszxl.entity.ProductColumnSpu;
import com.whoiszxl.service.ProductColumnService;
import com.whoiszxl.service.ProductColumnSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品专栏表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/column")
@Api(tags = "专栏相关接口")
public class ColumnAdminController {

    @Autowired
    private ProductColumnService columnService;

    @Autowired
    private ProductColumnSpuService productColumnSpuService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取专栏列表", notes = "分页获取专栏列表", response = ProductColumnResponse.class)
    public ResponseResult<IPage<ProductColumnResponse>> list(@RequestBody ProductColumnQuery query) {
        LambdaQueryWrapper<ProductColumn> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())) {
            wrapper.like(ProductColumn::getName, "%" + query.getName() + "%");
        }
        IPage<ProductColumn> result = columnService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<ProductColumnResponse> finalResult = result.convert(e -> dozerUtils.map(e, ProductColumnResponse.class));
        return ResponseResult.buildSuccess(finalResult);

    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取专栏", notes = "通过主键ID获取专栏", response = ProductColumnResponse.class)
    public ResponseResult<ProductColumnResponse> getSupplierById(@PathVariable Long id) {
        ProductColumn column = columnService.getById(id);
        return column == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(column, ProductColumnResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增专栏", notes = "新增专栏", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody ProductColumnSaveCommand columnSaveCommand) {
        ProductColumn column = dozerUtils.map(columnSaveCommand, ProductColumn.class);
        boolean saveFlag = columnService.save(column);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新专栏", notes = "更新专栏", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody ProductColumnUpdateCommand columnSaveCommand) {
        ProductColumn column = dozerUtils.map(columnSaveCommand, ProductColumn.class);
        boolean updateFlag = columnService.updateById(column);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除专栏", notes = "删除专栏", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = columnService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

    @SaCheckLogin
    @PostMapping("/spu/list")
    @ApiOperation(value = "分页获取专栏SPU列表", notes = "分页获取专栏SPU列表", response = ProductColumnResponse.class)
    public ResponseResult<IPage<ProductColumnSpuResponse>> spulist(@RequestBody ProductColumnSkuQuery query) {
        LambdaQueryWrapper<ProductColumnSpu> wrapper = new LambdaQueryWrapper<>();
        if(query.getColumnId() != null) {
            wrapper.like(ProductColumnSpu::getColumnId, query.getColumnId());
        }
        IPage<ProductColumnSpu> result = productColumnSpuService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<ProductColumnSpuResponse> finalResult = result.convert(e -> dozerUtils.map(e, ProductColumnSpuResponse.class));
        return ResponseResult.buildSuccess(finalResult);

    }

    @SaCheckLogin
    @PostMapping("/spu")
    @ApiOperation(value = "新增专栏SPU", notes = "新增专栏SPU", response = ResponseResult.class)
    public ResponseResult<Boolean> saveColumnSpu(@RequestBody ProductColumnSpuSaveCommand saveCommand) {
        ProductColumnSpu columnSpu = dozerUtils.map(saveCommand, ProductColumnSpu.class);
        boolean saveFlag = productColumnSpuService.save(columnSpu);
        return ResponseResult.buildByFlag(saveFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/spu/{id}")
    @ApiOperation(value = "删除专栏SPU", notes = "删除专栏SPU", response = ResponseResult.class)
    public ResponseResult<Boolean> deleteColumnSpu(@PathVariable Long id) {
        boolean removeFlag = productColumnSpuService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

