package com.whoiszxl.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.response.SeckillApiResponse;
import com.whoiszxl.cqrs.response.SeckillResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * C端:秒杀活动相关接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/api/seckill")
@Api(tags = "C端:秒杀相关接口")
public class SeckillApiController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private DozerUtils dozerUtils;

    @PostMapping("/list")
    @ApiOperation(value = "获取秒杀活动列表", notes = "获取秒杀活动列表", response = SeckillResponse.class)
    public ResponseResult<SeckillApiResponse> seckillList() {
        //TODO 获取秒杀活动列表
        return null;
    }



}

