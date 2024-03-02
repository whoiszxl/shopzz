package com.whoiszxl.taowu.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.SkuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SpuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.SpuQuery;
import com.whoiszxl.taowu.entity.Spu;
import com.whoiszxl.taowu.service.SpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品基础信息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Slf4j
@RestController
@RequestMapping("/spu")
@Tag(name = "SPU后台相关接口")
@RequiredArgsConstructor
public class SpuAdminController extends BaseController<SpuService, Spu, Spu, SpuQuery, SpuSaveCommand, SpuUpdateCommand> {

    private final SpuService spuService;

    @SaCheckLogin
    @PostMapping("/add")
    @Operation(summary = "新增SPU", description = "新增SPU")
    public ResponseResult<Boolean> save(@RequestBody @Validated SpuSaveCommand spuSaveCommand) {
        spuService.save(spuSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PutMapping("/update")
    @Operation(summary = "更新SPU", description = "更新SPU")
    public ResponseResult<Boolean> update(@RequestBody SpuUpdateCommand spuUpdateCommand) {
        spuService.update(spuUpdateCommand);
        return ResponseResult.buildSuccess();
    }


    @SaCheckLogin
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除SPU", description = "删除SPU")
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        spuService.remove(id);
        return ResponseResult.buildSuccess();
    }


    @SaCheckLogin
    @PostMapping("/sku/save")
    @Operation(summary = "新增SKU", description = "新增SKU")
    public ResponseResult<Boolean> skuSave(@RequestBody @Validated SkuSaveCommand skuSaveCommand) {
        spuService.skuSave(skuSaveCommand);
        return ResponseResult.buildSuccess();
    }
}

