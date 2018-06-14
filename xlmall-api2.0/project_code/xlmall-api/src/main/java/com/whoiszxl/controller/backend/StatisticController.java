package com.whoiszxl.controller.backend;

import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.service.OrderService;
import com.whoiszxl.service.ProductService;
import com.whoiszxl.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "后台统计管理模块", description = "后台统计管理模块")
@RestController
@RequestMapping("/manage/statistic")
public class StatisticController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	
	@PostMapping("base_count")
	@ApiOperation(value = "用户总数，商品总数，订单总数统计")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<Map> base_count() {
		//查询用户总数
		int userCount = userService.selectUserCount();
		
		//查询商品总数
		int productCount = productService.selectProductCount();
		
		//查询订单总数
		int orderCount = orderService.selectOrderCount();
		
		Map<String, Integer> resultMap = Maps.newHashMap();
		resultMap.put("userCount", userCount);
		resultMap.put("productCount", productCount);
		resultMap.put("orderCount", orderCount);
		
		return ServerResponse.createBySuccess("用户总数，商品总数，订单总数统计结果", resultMap);
		
	}
}
