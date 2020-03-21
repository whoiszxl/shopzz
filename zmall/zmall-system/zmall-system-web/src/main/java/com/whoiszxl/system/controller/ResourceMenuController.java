package com.whoiszxl.system.controller;


import com.whoiszxl.system.service.ResourceMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-21
 */
@Slf4j
@Controller
@Api(value = "", tags = "", description="")
public class ResourceMenuController {

    @Autowired
    private ResourceMenuService resourceMenuService;
}
