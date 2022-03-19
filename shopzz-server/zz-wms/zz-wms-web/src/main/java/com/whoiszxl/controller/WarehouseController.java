package com.whoiszxl.controller;

import com.whoiszxl.service.WarehouseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仓库相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@RestController
@RequestMapping("/warehouse")
@Api(tags = "仓库相关接口")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

}
