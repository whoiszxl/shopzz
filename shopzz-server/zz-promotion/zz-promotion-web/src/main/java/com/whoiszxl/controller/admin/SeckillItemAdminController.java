package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SeckillItemSaveCommand;
import com.whoiszxl.cqrs.command.SeckillItemUpdateCommand;
import com.whoiszxl.cqrs.query.SeckillItemQuery;
import com.whoiszxl.cqrs.response.SeckillItemResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.service.SeckillItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 秒杀item表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/seckill/item")
@Api(tags = "秒杀秒杀商品item相关接口")
public class SeckillItemAdminController {


    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取秒杀商品列表", notes = "分页获取秒杀商品列表", response = SeckillItemResponse.class)
    public ResponseResult<IPage<SeckillItemResponse>> list(@RequestBody SeckillItemQuery query) {
        LambdaQueryWrapper<SeckillItem> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getSkuName())) {
            wrapper.like(SeckillItem::getSkuName, "%" + query.getSkuName() + "%");
        }
        if(query.getSeckillId() != null) {
            wrapper.eq(SeckillItem::getSeckillId, query.getSeckillId());
        }
        if(query.getStatus() != null) {
            wrapper.eq(SeckillItem::getStatus, query.getStatus());
        }
        IPage<SeckillItem> result = seckillItemService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<SeckillItemResponse> finalResult = result.convert(e -> dozerUtils.map(e, SeckillItemResponse.class));
        return ResponseResult.buildSuccess(finalResult);

    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取秒杀商品", notes = "通过主键ID获取秒杀商品", response = SeckillItemResponse.class)
    public ResponseResult<SeckillItemResponse> getById(@PathVariable Long id) {
        SeckillItem seckillItem = seckillItemService.getById(id);
        return seckillItem == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(seckillItem, SeckillItemResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增秒杀商品", notes = "新增秒杀商品", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody SeckillItemSaveCommand bannerSaveCommand) {
        SeckillItem seckillItem = dozerUtils.map(bannerSaveCommand, SeckillItem.class);
        boolean saveFlag = seckillItemService.save(seckillItem);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新秒杀商品", notes = "更新秒杀商品", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody SeckillItemUpdateCommand bannerSaveCommand) {
        SeckillItem seckillItem = dozerUtils.map(bannerSaveCommand, SeckillItem.class);
        boolean updateFlag = seckillItemService.updateById(seckillItem);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除秒杀商品", notes = "删除秒杀商品", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = seckillItemService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }



}

