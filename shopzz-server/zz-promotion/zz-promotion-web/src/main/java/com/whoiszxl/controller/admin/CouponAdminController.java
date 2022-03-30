package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.CouponSaveCommand;
import com.whoiszxl.cqrs.command.CouponUpdateCommand;
import com.whoiszxl.cqrs.query.CouponQuery;
import com.whoiszxl.cqrs.response.CouponResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/coupon")
@Api(tags = "优惠券相关接口")
public class CouponAdminController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取优惠券列表", notes = "分页获取优惠券列表", response = CouponResponse.class)
    public ResponseResult<IPage<CouponResponse>> list(@RequestBody CouponQuery query) {
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())) {
            wrapper.like(Coupon::getName, "%" + query.getName() + "%");
        }
        if(query.getType() != null) {
            wrapper.eq(Coupon::getType, query.getType());
        }
        IPage<Coupon> result = couponService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<CouponResponse> finalResult = result.convert(e -> dozerUtils.map(e, CouponResponse.class));
        return ResponseResult.buildSuccess(finalResult);

    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取优惠券", notes = "通过主键ID获取优惠券", response = CouponResponse.class)
    public ResponseResult<CouponResponse> getSupplierById(@PathVariable Long id) {
        Coupon banner = couponService.getById(id);
        return banner == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(banner, CouponResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增优惠券", notes = "新增优惠券", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody CouponSaveCommand bannerSaveCommand) {
        Coupon banner = dozerUtils.map(bannerSaveCommand, Coupon.class);
        boolean saveFlag = couponService.save(banner);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新优惠券", notes = "更新优惠券", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody CouponUpdateCommand bannerSaveCommand) {
        Coupon banner = dozerUtils.map(bannerSaveCommand, Coupon.class);
        boolean updateFlag = couponService.updateById(banner);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除优惠券", notes = "删除优惠券", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = couponService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

