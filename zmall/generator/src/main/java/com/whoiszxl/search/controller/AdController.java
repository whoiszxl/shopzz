package com.whoiszxl.search.controller;


import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-22
 */
@Slf4j
@Controller
@Api(value = "", tags = "", description="")
public class AdController {

    @Autowired
    private AdService adService;
}
