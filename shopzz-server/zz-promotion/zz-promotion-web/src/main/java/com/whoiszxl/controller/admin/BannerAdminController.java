package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.BannerSaveCommand;
import com.whoiszxl.cqrs.command.BannerUpdateCommand;
import com.whoiszxl.cqrs.query.BannerQuery;
import com.whoiszxl.cqrs.response.BannerResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 轮播表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/banner")
@Api(tags = "轮播相关接口")
public class BannerAdminController {
    
    @Autowired
    private BannerService bannerService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取轮播图列表", notes = "分页获取轮播图列表", response = BannerResponse.class)
    public ResponseResult<IPage<BannerResponse>> list(@RequestBody BannerQuery query) {
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())) {
            wrapper.like(Banner::getName, "%" + query.getName() + "%");
        }
        if(query.getType() != null) {
            wrapper.eq(Banner::getType, query.getType());
        }
        if(query.getBizId() != null) {
            wrapper.eq(Banner::getBizId, query.getBizId());
        }
        if(query.getStatus() != null) {
            wrapper.eq(Banner::getStatus, query.getStatus());
        }
        IPage<Banner> result = bannerService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<BannerResponse> finalResult = result.convert(e -> dozerUtils.map(e, BannerResponse.class));
        return ResponseResult.buildSuccess(finalResult);
        
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取轮播图", notes = "通过主键ID获取轮播图", response = BannerResponse.class)
    public ResponseResult<BannerResponse> getSupplierById(@PathVariable Long id) {
        Banner banner = bannerService.getById(id);
        return banner == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(banner, BannerResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增轮播图", notes = "新增轮播图", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody BannerSaveCommand bannerSaveCommand) {
        Banner banner = dozerUtils.map(bannerSaveCommand, Banner.class);
        boolean saveFlag = bannerService.save(banner);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新轮播图", notes = "更新轮播图", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody BannerUpdateCommand bannerSaveCommand) {
        Banner banner = dozerUtils.map(bannerSaveCommand, Banner.class);
        boolean updateFlag = bannerService.updateById(banner);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除轮播图", notes = "删除轮播图", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = bannerService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

