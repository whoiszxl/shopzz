package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SeckillSaveCommand;
import com.whoiszxl.cqrs.command.SeckillUpdateCommand;
import com.whoiszxl.cqrs.query.SeckillQuery;
import com.whoiszxl.cqrs.response.SeckillResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 秒杀表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/seckill")
@Api(tags = "秒杀活动相关接口")
public class SeckillAdminController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取秒杀活动列表", notes = "分页获取秒杀活动列表", response = SeckillResponse.class)
    public ResponseResult<IPage<SeckillResponse>> list(@RequestBody SeckillQuery query) {
        LambdaQueryWrapper<Seckill> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())) {
            wrapper.like(Seckill::getName, "%" + query.getName() + "%");
        }
        if(query.getStatus() != null) {
            wrapper.eq(Seckill::getStatus, query.getStatus());
        }
        IPage<Seckill> result = seckillService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<SeckillResponse> finalResult = result.convert(e -> dozerUtils.map(e, SeckillResponse.class));
        return ResponseResult.buildSuccess(finalResult);

    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取秒杀活动", notes = "通过主键ID获取秒杀活动", response = SeckillResponse.class)
    public ResponseResult<SeckillResponse> getById(@PathVariable Long id) {
        Seckill seckill = seckillService.getById(id);
        return seckill == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(seckill, SeckillResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增秒杀活动", notes = "新增秒杀活动", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody SeckillSaveCommand bannerSaveCommand) {
        Seckill seckill = dozerUtils.map(bannerSaveCommand, Seckill.class);
        boolean saveFlag = seckillService.save(seckill);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新秒杀活动", notes = "更新秒杀活动", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody SeckillUpdateCommand bannerSaveCommand) {
        Seckill seckill = dozerUtils.map(bannerSaveCommand, Seckill.class);
        boolean updateFlag = seckillService.updateById(seckill);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除秒杀活动", notes = "删除秒杀活动", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = seckillService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

